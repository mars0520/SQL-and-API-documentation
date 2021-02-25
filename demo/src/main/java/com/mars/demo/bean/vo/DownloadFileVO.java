package com.mars.demo.bean.vo;

import java.util.Date;

/**
 * @author xzp
 * @description
 * @date 2021/1/22
 **/
public class DownloadFileVO {

    /**
     * 路径 （prefix/id/yyyy/MM/dd/md5.png）
     */
    private String path;

    /**
     * 时间 （yyyy-MM-dd hh:mm:ss）
     */
    private Date time;

    /**
     * 有效分钟
     */
    private Long expire;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public DownloadFileVO() {
    }

    public DownloadFileVO(String path, Date time, Long expire) {
        this.path = path;
        this.time = time;
        this.expire = expire;
    }

    @Override
    public String toString() {
        return "DownloadFileVO{" +
                "path='" + path + '\'' +
                ", time=" + time +
                ", expire=" + expire +
                '}';
    }
}
