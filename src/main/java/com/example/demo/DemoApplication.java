package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration   //标注一个类是配置类，spring boot在扫到这个注解时自动加载这个类相关的功能，比如前面的文章中介绍的配置AOP和拦截器时加在类上的Configuration
@MapperScan("mapper") //扫描的mapper
@ComponentScan(basePackages = {"controller","util","error","config","dao","mapper","service"})
@SpringBootApplication
public class DemoApplication {
    /* *
     * @param args
     * @return void
     * @author 王正龙
     * @creed: 这是一个springboot启动类
     * @date 2019/12/12 16:20
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
