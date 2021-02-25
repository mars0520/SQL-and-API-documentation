package com.mars.demo;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
public class DemoApplicationTests {

    @Autowired
    private StringEncryptor encryptor;

    @Test
    public void contextLoads() {
        String s2 = encryptor.encrypt("root");
        String s3 = encryptor.encrypt("xiaobumac0520");
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(encryptor.decrypt(s2));
        System.out.println(encryptor.decrypt(s3));
    }

}
