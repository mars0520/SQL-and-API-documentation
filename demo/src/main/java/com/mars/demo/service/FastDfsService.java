package com.mars.demo.service;

import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * @author xzp
 * @description fastDfs
 * @date 2021/1/20
 **/
public interface FastDfsService {

    /**
     * 查看文件的信息
     * @param groupName 组名
     * @param path 路径
     * @return 文件的基础信息
     */
    FileInfo queryFileInfo(String groupName, String path);

    /**
     * 获取文件元信息
     * @param groupName 组名
     * @param path 路径
     * @return 文件元数据
     */
    Set<MetaData> getMetadata(String groupName, String path);

    /**
     * 上传一般文件
     * @param file
     * @return 路径
     * @throws IOException
     */
    String uploadFile(MultipartFile file) throws IOException;

    /**
     * 上传文件(文件不可修改)
     * 文件上传后不可以修改，如果要修改则删除以后重新上传
     * @param groupName 组名
     * @param file
     * @return 路径
     * @throws IOException
     */
    String uploadFile(String groupName, MultipartFile file) throws IOException;

    /**
     * 上传图片并且生成缩略图
     * 支持的图片格式包括"JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"
     * 缩略图为上传文件名 + 缩略图后缀 _150x150
     * 如 xxx.jpg, 缩略图为 xxx_150x150.jpg
     * @param file
     * @return 路径
     * @throws IOException
     */
    String uploadImageAndCrtThumbImage(MultipartFile file) throws IOException;

    /**
     * 下载整个文件
     * @param groupName 组名
     * @param path 路径
     * @param response
     * @throws IOException
     */
    void downloadFile(HttpServletResponse response, String groupName, String path) throws IOException;

    /**
     * 下载文件片段
     * @param groupName 组名
     * @param path 路径
     * @param response
     * @param fileOffset 开始位置 
     * @param fileSize 读取文件长度
     * @throws IOException
     */
    void downloadFile(HttpServletResponse response, String groupName, String path, long fileOffset, long fileSize) throws IOException;

    /**
     * 删除文件
     * @param filePath 文件路径(groupName/path)
     */
    void deleteFile(String filePath);

    /**
     * 删除文件
     * @param groupName 组名
     * @param path 路径
     */
    void deleteFile(String groupName, String path);

}
