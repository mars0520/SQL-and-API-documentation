package com.mars.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * @author xzp
 * @description
 * @date 2021/2/4
 **/
public interface WordService {

    /**
     * 生成数据库文档
     *
     * @param document
     */
    void sqlWord(XWPFDocument document);

    /**
     * 生成接口文档
     *
     * @param document
     */
    void apiWord(XWPFDocument document);

}
