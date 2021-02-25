package com.mars.demo.util;

import com.alibaba.fastjson.JSON;
import com.mars.demo.base.config.CommonConfigProperties;
import com.mars.demo.base.constant.GlobalUserInfo;
import com.mars.demo.bean.dto.FtpDTO;
import com.mars.demo.bean.vo.DownloadFileVO;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * @author xzp
 * @description ftp工具类
 * @date 2021/1/21
 **/
@Component
public class FtpUtil {

    private final static Logger log = LoggerFactory.getLogger(FtpUtil.class);

    /**
     * 返回下载连接
     * @param path 路径
     * @return
     */
    public static String getServerPath(String path) {
        return getServerPath(path, 600L);
    }

    /**
     * 返回下载连接
     * @param path 路径
     * @param expire 有效时间 分
     * @return
     */
    public static String getServerPath(String path, Long expire) {
        return String.format("%s/%s%s",
                getServerAddress(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()),
                "ftp/downloadFile?param=",
                JSON.toJSONString(new DownloadFileVO(path, new Date(), expire)));
    }

    /**
     * 获取服务器地址ip
     * @param request
     * @return
     */
    private static String getServerAddress(HttpServletRequest request){
        return String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());
    }

    /**
     * ftp方法调用说明
     *
     * 文件上传 logo(首页...)
     * public boolean uploadLogo(FtpDTO ftp)
     * @param ftp inputStream文件流, stcd, fileName文件名(带后缀)
     *
     * 文件下载
     * public void downloadFile(HttpServletRequest request, HttpServletResponse response, String param)
     * @param param 参数 path 文件路径 time 时间 格式(秘文) : {"path":"prefix/STCD/yyyy/MM/dd/md5.png", "time":"2020-01-01"}
     *
     * 删除FTP服务器上的一个目录(如果为空)
     * public boolean removeDirectory(String directory) throws IOException
     * @param directory 文件目录
     *
     * 检查路径是否存在
     * public boolean existDirectory(String path) throws IOException
     * @param path web/STCD/yyyy/MM/dd/md5.png
     *
     * 检查路径是否存在
     * public FTPFile[] getListFiles(String path) throws IOException
     * @param path web/STCD/yyyy/MM/dd/md5.png
     *
     * 创建FTP文件夹目录
     * public boolean createDirectory(String directory)
     * @param directory 文件目录
     */

    /**
     * 配置文件
     */
    private static CommonConfigProperties commonConfigProperties;

    public FtpUtil(CommonConfigProperties properties) {
        commonConfigProperties = properties;
    }

    /**
     * 是否开启下载权限认证
     */
    private static boolean enableDownload = false;

    /**
     * 文件上传 logo(首页...)
     *
     * @param ftp
     */
    public static boolean uploadLogo(FtpDTO ftp) {
        return uploadFile(ftp.getInputStream(), "logo", ftp.getId(), ftp.getFileName());
    }

    /**
     * 文件上传
     *
     * @param inputStream
     * @param groupName   组名 FtpPrefixEnum枚举值
     * @param id
     * @param fileName    文件名(带后缀)
     * @return
     */
    private static boolean uploadFile(InputStream inputStream, String groupName, String id, String fileName) {
        // groupName/STCD/yyyy/MM/dd/md5.png
        StringBuffer str = new StringBuffer().append(groupName).append("/").append(id);
        if (!groupName.equals("logo")) {
            str.append("/").append(getCurrentDate());
        }
        String path = str.toString(), name = new StringBuffer()
                .append(fileName)
                .append(".").append(FilenameUtils.getExtension(fileName)).toString();
        // 文件入库 stcd 文件名fileName 路径path
        return uploadFile(inputStream, path, name);
    }

    /**
     * 文件上传
     *
     * @param inputStream 上传文件流
     * @param filepath    保存文件路径
     * @param fileName    文件名称
     * @return
     */
    private static boolean uploadFile(InputStream inputStream, String filepath, String fileName) {
        boolean success = false;
        FTPClient ftpClient = null;
        try {
            ftpClient = connectFTPClient();
            if (!ftpClient.changeWorkingDirectory(commonConfigProperties.getFilepath()) && ftpClient.makeDirectory(commonConfigProperties.getFilepath())) {
                ftpClient.changeWorkingDirectory(commonConfigProperties.getFilepath());
            }
            String[] array = filepath.split("/");
            for (String s : array) {
                ftpClient.makeDirectory(s);
                ftpClient.changeWorkingDirectory(s);
            }
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            success = ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
        } catch (FTPConnectionClosedException e) {
            log.error("ftp连接被关闭", e);
            e.printStackTrace();
        } catch (Exception e) {
            log.error("ERR : upload file  to ftp : failed! ", e);
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                log.error("ftp关闭输入流时失败", e);
            }
            if (ftpClient.isConnected()) {
                try {
                    if (ftpClient != null) {
                        ftpClient.logout();
                        ftpClient.disconnect();
                    }
                } catch (Exception e) {
                    log.error("ftp连接关闭失败", e);
                }
            }
        }
        return success;
    }

    /**
     * 文件下载
     *
     * @param response
     * @param param    参数 path 文件路径, time 时间, expire 有效时间
     *                 格式(秘文) : {"path":"prefix/id/yyyy/MM/dd/md5.png", "time":"yyyy-MM-dd hh:mm:ss", "expire":600}
     * @return
     * @throws IOException
     */
    public static void downloadFile(HttpServletResponse response, String param) {
        notNull(param, "下载参数");
        try {
            //param = xxx(param);
            DownloadFileVO downloadVO = JSON.parseObject(param, DownloadFileVO.class);
            String path = downloadVO.getPath();
            validationDownload(path, downloadVO.getTime().getTime(), downloadVO.getExpire());
            FTPClient ftpClient = connectFTPClient(true);
            InputStream inputStream = ftpClient.retrieveFileStream(path);
            if (null == inputStream) {
                throw new RuntimeException("文件不存在");
            }
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } catch (FTPConnectionClosedException e) {
            log.error("ftp连接被关闭", e);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查路径是否存在
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static boolean existDirectory(String path) throws IOException {
        notNull(path, "文件路径");
        boolean flag = false;
        FTPFile[] ftpFileArr = getListFiles(path);
        for (FTPFile ftpFile : ftpFileArr) {
            log.info("文件名：" + ftpFile.getName());
            if (ftpFile.isDirectory() && ftpFile.getName().equalsIgnoreCase(path)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 获取文件列表
     *
     * @param path 目录
     * @return
     * @throws IOException
     */
    public static FTPFile[] getListFiles(String path) throws IOException {
        notNull(path, "文件路径");
        FTPClient ftpClient = connectFTPClient();
        return ftpClient.listFiles(path);
    }

    /**
     * 创建FTP文件夹目录
     *
     * @param directory 文件目录
     * @return
     * @throws IOException
     */
    public static boolean createDirectory(String directory) throws IOException {
        notNull(directory, "文件目录");
        FTPClient ftpClient = connectFTPClient(true);
        return ftpClient.makeDirectory(directory);
    }

    /**
     * 删除FTP服务器上的一个目录(如果为空)
     *
     * @param directory 文件目录
     * @return
     */
    public static boolean removeDirectory(String directory) throws IOException {
        notNull(directory, "文件目录");
        FTPClient ftpClient = connectFTPClient(true);
        return ftpClient.removeDirectory(directory);
    }

    /**
     * 连接FTPClient
     *
     * @return
     */
    private static FTPClient connectFTPClient() {
        return connectFTPClient(false);
    }

    private static FTPClient connectFTPClient(boolean is) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(commonConfigProperties.getIp(), commonConfigProperties.getPort());
            ftpClient.login(commonConfigProperties.getUserName(), commonConfigProperties.getPassword());
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                ftpClient.disconnect();
                log.info("connectToServer FTP server refused connection.");
            }
            if (is) {
                ftpClient.changeWorkingDirectory(commonConfigProperties.getFilepath());
            }
        } catch (FTPConnectionClosedException e) {
            log.error("ftp连接被关闭", e);
            e.printStackTrace();
        } catch (Exception e) {
            log.error("ERR : upload file  to ftp : failed! ", e);
            e.printStackTrace();
        }
        return ftpClient;
    }

    /**
     * 获取当前时间
     *
     * @return yyyy/MM/dd
     */
    private static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.format(new Date());
    }

    /**
     * 验证下载权限
     *
     * @param path    路径
     * @param time    时间毫秒
     * @param expire  有效时间(m)
     */
    private static void validationDownload(String path, Long time, Long expire) {
        if(null == expire || expire <= 0){
            expire = 600L;
        }
        if ((System.currentTimeMillis() - time) / 60000 > expire) {
            throw new RuntimeException("下载地址已经失效");
        }
        if (enableDownload) {
            if (!path.contains("/")) {
                throw new RuntimeException("路径不符合");
            }
            Set<String> list = GlobalUserInfo.getFtpDownloadList();
            if (null == list || list.isEmpty()) {
                String userId = GlobalUserInfo.getUserUUId();
                //查询数据库赋值
                list = null;
                SecurityUtils.getSubject().getSession().setAttribute("FtpDownloadList", list);
            }
            int length = path.indexOf("/");
            String name = path.substring(length + 1), id = name.substring(0, name.indexOf("/"));
            if (null == list || list.isEmpty() || list.contains(path)) {
                log.error("无权限下载,id:" + id + ",list:" + list);
                throw new RuntimeException("无权限下载");
            }
        }
    }

    /**
     * 非空校验
     *
     * @param value 参数
     * @param note  说明
     */
    public static void notNull(String value, String note) {
        if (StringUtils.isBlank(value)) {
            throw new RuntimeException(note + "为空!");
        }
    }

}
