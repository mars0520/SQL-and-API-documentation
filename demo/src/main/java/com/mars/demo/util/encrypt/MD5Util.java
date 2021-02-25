package com.mars.demo.util.encrypt;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description MD5加密工具类
 * @author xzp
 * @date 2019/10/9 10:35 上午
 **/
public class MD5Util {

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f"};

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            buffer.append(byteToHexString(b[i]));
        }

        return buffer.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        } catch (Exception exception) {
        }
        return resultString;
    }

    public static void main(String[] args) {
        System.out.println(MD5EncodeParam("account"));
        System.out.println(MD5EncodeParam("password"));
    }

    public static String MD5EncodeParam(String param) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        param = param.concat(dateFormat.format(date));
        String newMd5 = MD5Encode(param, "UTF-8");
        return MD5Encode(newMd5, "UTF-8");
    }

}
