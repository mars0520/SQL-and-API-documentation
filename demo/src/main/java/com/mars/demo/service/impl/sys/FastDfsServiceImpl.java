package com.mars.demo.service.impl.sys;

import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.mars.demo.base.enums.FastGroupNameEnum;
import com.mars.demo.base.exception.ParamExceptionHandling;
import com.mars.demo.service.BaseService;
import com.mars.demo.service.FastDfsService;
import com.mars.demo.util.ParamCheckUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
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
@Service("FastDfsService")
public class FastDfsServiceImpl extends BaseService implements FastDfsService {

    @Autowired private FastFileStorageClient storageClient;

    /**
     * 查看文件的信息
     * @param groupName 组名
     * @param path 路径
     * @return 文件的基础信息
     */
    @Override
    public FileInfo queryFileInfo(String groupName, String path) {
        return storageClient.queryFileInfo(groupName, path);
    }

    /**
     * 获取文件元信息
     * @param groupName 组名
     * @param path 路径
     * @return 文件元数据
     */
    @Override
    public Set<MetaData> getMetadata(String groupName, String path) {
        return storageClient.getMetadata(groupName, path);
    }

    /**
     * 上传一般文件
     * @param file
     * @return 路径
     * @throws IOException
     */
    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return storePath.getFullPath();
    }

    /**
     * 上传文件(文件不可修改)
     * 文件上传后不可以修改，如果要修改则删除以后重新上传
     * @param groupName 组名
     * @param file
     * @return 路径
     * @throws IOException
     */
    @Override
    public String uploadFile(String groupName, MultipartFile file) throws IOException {
        validationGroupName(groupName);
        StorePath storePath = storageClient.uploadFile(groupName, file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()));
        return storePath.getFullPath();
    }

    /**
     * 上传图片并且生成缩略图
     * 支持的图片格式包括"JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"
     * 缩略图为上传文件名 + 缩略图后缀 _150x150
     * 如 xxx.jpg, 缩略图为 xxx_150x150.jpg
     * @param file
     * @return 路径
     * @throws IOException
     */
    @Override
    public String uploadImageAndCrtThumbImage(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return storePath.getFullPath();
    }

    /**
     * 下载整个文件
     * @param groupName 组名
     * @param path 路径
     * @param response
     * @throws IOException
     */
    @Override
    public void downloadFile(HttpServletResponse response, String groupName, String path) throws IOException {
        validationGroupName(groupName);
        InputStream inputStream = null;
        try{
            inputStream = storageClient.downloadFile(groupName, path, ins1 -> ins1);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null != inputStream){
                inputStream.close();
            }
        }
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    /**
     * 下载文件片段
     * @param groupName 组名
     * @param path 路径
     * @param response
     * @param fileOffset 开始位置 
     * @param fileSize 读取文件长度
     * @throws IOException
     */
    @Override
    public void downloadFile(HttpServletResponse response, String groupName, String path, long fileOffset, long fileSize) throws IOException {
        validationGroupName(groupName);
        InputStream inputStream = null;
        try{
            inputStream = storageClient.downloadFile(groupName, path, fileOffset, fileSize, ins1 -> ins1);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null != inputStream){
                inputStream.close();
            }
        }
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    /**
     * 删除文件
     * @param filePath 文件路径(groupName/path)
     */
    @Override
    public void deleteFile(String filePath) {
        ParamCheckUtil.notNull(filePath, "文件路径(groupName/path)");
        validationGroupName(filePath.substring(0, filePath.indexOf("/")));
        storageClient.deleteFile(filePath);
    }

    /**
     * 删除文件
     * @param groupName 组名
     * @param path 路径
     */
    @Override
    public void deleteFile(String groupName, String path){
        validationGroupName(groupName);
        storageClient.deleteFile(groupName, path);
    }

    /**
     * 验证表名是否存在
     * @param groupName
     */
    public void validationGroupName(String groupName){
        ParamCheckUtil.notNull(groupName, "组名");
        String name = FastGroupNameEnum.getMaps().get(groupName);
        if(null == name || !name.equals(groupName)){
            throw new ParamExceptionHandling("fast组名不存在");
        }
    }

}
