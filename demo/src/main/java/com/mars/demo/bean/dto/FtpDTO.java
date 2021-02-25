package com.mars.demo.bean.dto;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;

/**
 * @author xzp
 * @description 文件上传对象
 * @date 2021/1/22
 **/
public class FtpDTO {

    /**
     * 文件流
     */
    private InputStream inputStream;
    /**
     * id
     */
    private String id;
    /**
     * 文件名(带后缀)
     */
    private String fileName;

    public FtpDTO(InputStream inputStream, String id, String fileName) {
        if (null == inputStream || StringUtils.isBlank(id) || StringUtils.isBlank(fileName)) {
            throw new RuntimeException("FtpDTO参数为空");
        }
        this.inputStream = inputStream;
        this.id = id;
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "FtpDTO{" +
                "inputStream=" + inputStream +
                ", id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
