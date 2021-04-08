package com.zty.kdd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.zty.bo.dao"})
@ComponentScan(basePackages = {"com.zty"})
public class KddAllInOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(KddAllInOneApplication.class, args);
    }

}
