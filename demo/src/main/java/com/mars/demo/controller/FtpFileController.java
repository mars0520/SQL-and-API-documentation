package com.mars.demo.controller;

import com.mars.demo.bean.dto.FtpDTO;
import com.mars.demo.util.FtpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xzp
 * @description
 * @date 2021/1/21
 **/
@Api(tags = "ftp文件接口")
@RestController
@RequestMapping("/ftp")
@CrossOrigin(originPatterns = "*", maxAge = 3600)
public class FtpFileController extends BaseController {

    @PostMapping("/uploadLogo")
    @ApiOperation(value = "文件上传 logo(首页...)")
    public boolean uploadLogo(@RequestParam("file") MultipartFile file, String id) throws IOException {
        return FtpUtil.uploadLogo(new FtpDTO(file.getInputStream(), id, file.getOriginalFilename()));
    }

    @GetMapping("/downloadFile")
    @ApiOperation(value = "文件下载", notes = "请求参数 param:{\"path\":\"web/id/yyyy/MM/dd/md5.png\", \"time\":\"yyyy-MM-dd hh:mm:ss\", \"expire\":600}")
    public void downloadFile(HttpServletResponse response, String param) {
        FtpUtil.downloadFile(response, param);
    }

    @GetMapping("/createDirectory")
    @ApiOperation(value = "创建FTP文件夹目录")
    public boolean createDirectory(String directory) throws IOException {
        return FtpUtil.createDirectory(directory);
    }

    @GetMapping("/removeDirectory")
    @ApiOperation(value = "删除FTP服务器上的一个目录(如果为空)")
    public boolean removeDirectory(String directory) throws IOException {
        return FtpUtil.removeDirectory(directory);
    }

    @GetMapping("/existDirectory")
    @ApiOperation(value = "检查路径是否存在")
    public boolean existDirectory(String path) throws IOException {
        return FtpUtil.existDirectory(path);
    }



}
