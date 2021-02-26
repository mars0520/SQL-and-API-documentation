package com.mars.demo.controller;

import com.alibaba.fastjson.JSON;
import com.mars.demo.base.bean.ResponseResult;
import com.mars.demo.base.enums.ReturnMessageType;
import com.mars.demo.bean.dto.FtpDTO;
import com.mars.demo.service.impl.sys.WordServiceImpl;
import com.mars.demo.util.FtpUtil;
import com.mars.demo.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Random;

/**
 * @author xzp
 * @description
 * @date 2021/1/21
 **/
@Api(tags = "生成word文档")
@RestController
@RequestMapping("/word")
@CrossOrigin(originPatterns = "*", maxAge = 3600)
public class WordController extends BaseController {

    /**
     * 枚举
     */
    private enum WordEnum{
        SQL,API
    }

    @GetMapping("/sql")
    @ApiOperation(value = "生成数据库文档", notes = "生成数据库文档")
    public void writeSqlDocx(HttpServletResponse response) throws Exception{
        writeDocx(WordEnum.SQL, response);
    }

    @GetMapping("/api")
    @ApiOperation(value = "生成接口文档", notes = "生成接口文档")
    public void writeApiDocx(HttpServletResponse response) throws Exception{
        writeDocx(WordEnum.API, response);
    }

    /**
     * 生成文档
     * @param wordEnum
     * @throws Exception
     */
    private void writeDocx(WordEnum wordEnum, HttpServletResponse response) throws Exception{
        InputStream in = null;
        XWPFDocument document = null;
        FileOutputStream out = null;
        try{
            if(WordEnum.SQL.equals(wordEnum)) {
                in = new FileInputStream(new File("../" + wordEnum +".docx"));
                document = new XWPFDocument(in);
                wordService.sqlWord(document);
            } else {
                document = new XWPFDocument();
                wordService.apiWord(document);
            }
            String fileName = wordEnum + "-" + new Random().nextInt(9999);
            out = new FileOutputStream(new File("../" + fileName + ".docx"));
            document.write(out);
            String jsonObjectStr = JSON.toJSONString(ResponseResult.responseOk("文件名：" + fileName));
            JsonUtil.returnJson(response, jsonObjectStr);
        }catch (Exception e){
            e.printStackTrace();
            String jsonObjectStr = JSON.toJSONString(ResponseResult.responseError(ReturnMessageType.MSG_FAIL_50017));
            JsonUtil.returnJson(response, jsonObjectStr);
        }finally {
            if(null != in){
                in.close();
            }
            if(null != document){
                document.close();
            }
            if(null != out){
                out.close();
            }
        }
    }

}
