package com.wst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms    //启动消息队列
public class MyApplicationOne {

    public static void main(String[] args) {
        SpringApplication.run(MyApplicationOne.class, args);
    }

}
