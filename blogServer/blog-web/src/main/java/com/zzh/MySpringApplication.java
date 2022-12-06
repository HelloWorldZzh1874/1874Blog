package com.zzh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @Author zzh
 * @Date 2022/1/17 16:28
 * @Version 0.1
 * @Description TODO
 **/

@SpringBootApplication
@ServletComponentScan
@EnableAsync
@EnableCaching
@EnableScheduling
@MapperScan("com.zzh.mapper")
public class MySpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(MySpringApplication.class,args);
    }
}
