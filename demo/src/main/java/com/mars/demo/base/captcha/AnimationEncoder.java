package com.mars.demo.base.captcha;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;

/**
 * @author xzp
 * @description 动画编码器
 * @date 2021/1/5
 **/
public class AnimationEncoder {
    /**
     * 宽度
     */
    protected int width;
    /**
     * 高度
     */
    protected int height;
    /**
     * 透明色
     */
    protected Color transparent = null;
    /**
     * 颜色表透明指数
     */
    protected int transIndex;
    /**
     * 是否重复
     */
    protected int repeat = -1;
    /**
     * 帧延迟(数)
     */
    protected int delay = 0;
    /**
     * 输出帧
     */
    protected boolean started = false;
    /**
     * 输出流
     */
    protected OutputStream out;
    /**
     * 当前帧
     */
    protected BufferedImage image;
    /**
     * 帧的字节数组
     */
    protected byte[] pixels;
    /**
     * 转换帧索引到调色板
     */
    protected byte[] indexedPixels;
    /**
     * 钻平面数
     */
    protected int colorDepth;
    /**
     * 调色板
     */
    protected byte[] colorTab;
    /**
     * 调色板条目
     */
    protected boolean[] usedEntry = new boolean[256];
    /**
     * 颜色表
     */
    protected int palSize = 7;
    /**
     * 处理代码
     */
    protected int dispose = -1;
    /**
     * 完成时关闭流
     */
    protected boolean closeStream = false;
    /**
     * 第一帧
     */
    protected boolean firstFrame = true;
    /**
     * 如果为false，则从第一帧获取大小
     */
    protected boolean sizeSet = false;
    /**
     * 量化器的默认采样间隔
     */
    protected int sample = 10;

    /**
     * 设置或更改每帧之间的延迟时间
     * 用于后续帧(适用于添加的最后一帧)。
     * @param ms 延迟时间，以毫秒为单位
     */
    public void setDelay(int ms) {
        delay = Math.round(ms / 10.0f);
    }

    /**
     * 为最后添加的帧设置GIF帧处理代码
     * 和任何后续帧。如果不透明，默认值为0
     * 颜色已设置，否则为2。
     * @param code 代码
     */
    public void setDispose(int code) {
        if (code >= 0) {
            dispose = code;
        }
    }

    /**
     * 设置GIF帧集的次数
     * @param iter int number of iterations.
     */
    public void setRepeat(int iter) {
        if (iter >= 0) {
            repeat = iter;
        }
    }

    /**
     * 设置最后添加帧的透明颜色
     * 和任何后续帧。
     * 所有颜色均可修改
     * 在量化的过程中，颜色在最后
     * 每一帧最接近给定颜色的调色板
     * 成为该框架的透明颜色。
     * 可以设置为null，表示没有透明颜色。
     * @param c Color to be treated as transparent on display.
     */
    public void setTransparent(Color c) {
        transparent = c;
    }

    /**
     * Adds next GIF frame.  The frame is not written immediately, but is
     * actually deferred until the next frame is received so that timing
     * data can be inserted.  Invoking <code>finish()</code> flushes all
     * frames.  If <code>setSize</code> was not invoked, the size of the
     * first image is used for all subsequent frames.
     *
     * @param im BufferedImage containing frame to write.
     * @return true if successful.
     */
    public boolean addFrame(BufferedImage im) {
        if ((im == null) || !started) {
            return false;
        }
        boolean ok = true;
        try {
            if (!sizeSet) {
                // use first frame's size
                setSize(im.getWidth(), im.getHeight());
            }
            image = im;
            getImagePixels(); // convert to correct format if necessary
            analyzePixels(); // build color table & map pixels
            if (firstFrame) {
                writeLSD(); // logical screen descriptior
                writePalette(); // global color table
                if (repeat >= 0) {
                    // use NS app extension to indicate reps
                    writeNetscapeExt();
                }
            }
            writeGraphicCtrlExt(); // write graphic control extension
            writeImageDesc(); // image descriptor
            if (!firstFrame) {
                writePalette(); // local color table
            }
            writePixels(); // encode and write pixel data
            firstFrame = false;
        } catch (IOException e) {
            ok = false;
        }

        return ok;
    }

    //added by alvaro
    public boolean outFlush() {
        boolean ok = false;
        try {
            out.flush();
            ok = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ok;
    }

    public byte[] getFrameByteArray() {
        return ((ByteArrayOutputStream) out).toByteArray();
    }

    /**
     * Flushes any pending data and closes output file.
     * If writing to an OutputStream, the stream is not
     * closed.
     *
     * @return boolean
     */
    public boolean finish() {
        if (!started) {
            return false;
        }
        boolean ok = true;
        started = false;
        try {
            // gif trailer
            out.write(0x3b);
            out.flush();
            if (closeStream) {
                out.close();
            }
        } catch (IOException e) {
            ok = false;
        }

        return ok;
    }

    public void reset() {
        // reset for subsequent use
        transIndex = 0;
        out = null;
        image = null;
        pixels = null;
        indexedPixels = null;
        colorTab = null;
        closeStream = false;
        firstFrame = true;
    }

    /**
     * Sets frame rate in frames per second.  Equivalent to
     * <code>setDelay(1000/fps)</code>.
     *
     * @param fps float frame rate (frames per second)
     */
    public void setFrameRate(float fps) {
        if (fps != 0f) {
            delay = Math.round(100f / fps);
        }
    }

    /**
     * Sets quality of color quantization (conversion of images
     * to the maximum 256 colors allowed by the GIF specification).
     * Lower values (minimum = 1) produce better colors, but slow
     * processing significantly.  10 is the default, and produces
     * good color mapping at reasonable speeds.  Values greater
     * than 20 do not yield significant improvements in speed.
     *
     * @param quality int greater than 0.
     */
    public void setQuality(int quality) {
        if (quality < 1) {
            quality = 1;
        }
        sample = quality;
    }

    /**
     * Sets the GIF frame size.  The default size is the
     * size of the first frame added if this method is
     * not invoked.
     *
     * @param w int frame width.
     * @param h int frame width.
     */
    public void setSize(int w, int h) {
        if (started && !firstFrame) {
            return;
        }
        width = w;
        height = h;
        if (width < 1) {
            width = 320;
        }
        if (height < 1) {
            height = 240;
        }
        sizeSet = true;
    }

    /**
     * Initiates GIF file creation on the given stream.  The stream
     * is not closed automatically.
     *
     * @param os OutputStream on which GIF images are written.
     * @return false if initial write failed.
     */
    public boolean start(OutputStream os) {
        if (os == null) {
            return false;
        }
        boolean ok = true;
        closeStream = false;
        out = os;
        try {
            // header
            writeString("GIF89a");
        } catch (IOException e) {
            ok = false;
        }
        return started = ok;
    }

    /**
     * Initiates writing of a GIF file with the specified name.
     *
     * @param file String containing output file name.
     * @return false if open or initial write failed.
     */
    public boolean start(String file) {
        boolean ok;
        try {
            out = new BufferedOutputStream(new FileOutputStream(file));
            ok = start(out);
            closeStream = true;
        } catch (IOException e) {
            ok = false;
        }
        return started = ok;
    }

    /**
     * Analyzes image colors and creates color map.
     */
    protected void analyzePixels() {
        int len = pixels.length;
        int nPix = len / 3;
        indexedPixels = new byte[nPix];
        Quantizer nq = new Quantizer(pixels, len, sample);
        // initialize quantizer
        // create reduced palette
        colorTab = nq.process();
        // convert map from BGR to RGB
        for (int i = 0; i < colorTab.length; i += 3) {
            byte temp = colorTab[i];
            colorTab[i] = colorTab[i + 2];
            colorTab[i + 2] = temp;
            usedEntry[i / 3] = false;
        }
        // map image pixels to new palette
        int k = 0;
        for (int i = 0; i < nPix; i++) {
            int index =
                    nq.map(pixels[k++] & 0xff,
                            pixels[k++] & 0xff,
                            pixels[k++] & 0xff);
            usedEntry[index] = true;
            indexedPixels[i] = (byte) index;
        }
        pixels = null;
        colorDepth = 8;
        palSize = 7;
        // get closest match to transparent color if specified
        if (transparent != null) {
            transIndex = findClosest(transparent);
        }
    }

    /**
     * Returns index of palette color closest to c
     *
     * @param c color
     * @return int
     */
    protected int findClosest(Color c) {
        if (colorTab == null) return -1;
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int minpos = 0;
        int dmin = 256 * 256 * 256;
        int len = colorTab.length;
        for (int i = 0; i < len; ) {
            int dr = r - (colorTab[i++] & 0xff);
            int dg = g - (colorTab[i++] & 0xff);
            int db = b - (colorTab[i] & 0xff);
            int d = dr * dr + dg * dg + db * db;
            int index = i / 3;
            if (usedEntry[index] && (d < dmin)) {
                dmin = d;
                minpos = index;
            }
            i++;
        }
        return minpos;
    }

    /**
     * Extracts image pixels into byte array "pixels"
     */
    protected void getImagePixels() {
        int w = image.getWidth();
        int h = image.getHeight();
        int type = image.getType();
        if ((w != width)
                || (h != height)
                || (type != BufferedImage.TYPE_3BYTE_BGR)) {
            // create new image with right size/format
            BufferedImage temp =
                    new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D g = temp.createGraphics();
            g.drawImage(image, 0, 0, null);
            image = temp;
        }
        pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    }

    /**
     * Writes Graphic Control Extension
     *
     * @throws IOException IO异常
     */
    protected void writeGraphicCtrlExt() throws IOException {
        // extension introducer
        out.write(0x21);
        // GCE label
        out.write(0xf9);
        // data block size
        out.write(4);
        int transp, disp;
        if (transparent == null) {
            transp = 0;
            // dispose = no action
            disp = 0;
        } else {
            transp = 1;
            // force clear if using transparent color
            disp = 2;
        }
        if (dispose >= 0) {
            // user override
            disp = dispose & 7;
        }
        disp <<= 2;

        // packed fields
        // 8   transparency flag
        out.write((disp | transp));

        // delay x 1/100 sec
        writeShort(delay);
        // transparent color index
        out.write(transIndex);
        // block terminator
        out.write(0);
    }

    /**
     * Writes Image Descriptor
     *
     * @throws IOException IO异常
     */
    protected void writeImageDesc() throws IOException {
        // image separator
        out.write(0x2c);
        // image position x,y = 0,0
        writeShort(0);
        writeShort(0);
        // image size
        writeShort(width);
        writeShort(height);
        // packed fields
        if (firstFrame) {
            // no LCT  - GCT is used for first (or only) frame
            out.write(0);
        } else {
            // specify normal LCT
            // 6-8 size of color table
            out.write(0x80 | palSize);
        }
    }

    /**
     * Writes Logical Screen Descriptor
     *
     * @throws IOException IO异常
     */
    protected void writeLSD() throws IOException {
        // logical screen size
        writeShort(width);
        writeShort(height);
        // packed fields
        // 6-8 : gct size
        out.write((0x80 |0x70 | palSize));

        // background color index
        out.write(0);
        // pixel aspect ratio - assume 1:1
        out.write(0);
    }

    /**
     * Writes Netscape application extension to define
     * repeat count.
     *
     * @throws IOException IO异常
     */
    protected void writeNetscapeExt() throws IOException {
        // extension introducer
        out.write(0x21);
        // app extension label
        out.write(0xff);
        // block size
        out.write(11);
        // app id + auth code
        writeString("NETSCAPE2.0");
        // sub-block size
        out.write(3);
        // loop sub-block id
        out.write(1);
        // loop count (extra iterations, 0=repeat forever)
        writeShort(repeat);
        // block terminator
        out.write(0);
    }

    /**
     * Writes color table
     *
     * @throws IOException IO异常
     */
    protected void writePalette() throws IOException {
        out.write(colorTab, 0, colorTab.length);
        int n = (3 * 256) - colorTab.length;
        for (int i = 0; i < n; i++) {
            out.write(0);
        }
    }

    /**
     * Encodes and writes pixel data
     *
     * @throws IOException IO异常
     */
    protected void writePixels() throws IOException {
        CaptchaEncoder encoder = new CaptchaEncoder(width, height, indexedPixels, colorDepth);
        encoder.encode(out);
    }

    /**
     * Write 16-bit value to output stream, LSB first
     *
     * @param value int
     * @throws IOException IO异常
     */
    protected void writeShort(int value) throws IOException {
        out.write(value & 0xff);
        out.write((value >> 8) & 0xff);
    }

    /**
     * Writes string to output stream
     *
     * @param s string
     * @throws IOException IO异常
     */
    protected void writeString(String s) throws IOException {
        int length = s.length();
        for (int i = 0; i < length; i++) {
            out.write((byte) s.charAt(i));
        }
    }

}
