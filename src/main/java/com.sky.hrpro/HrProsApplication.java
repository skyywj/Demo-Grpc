package com.sky.hrpro;


import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

/**
 * @ClassName:HrProsApplication
 * @Description:
 * HrProsApplication.java 是SpringBoot应用程序入口，或者叫主程序。
 * 注解@SpringBootApplication 标注他是一个SpringBoot应用，main方法使他成为一个主程序，将在应用启动时首先被执行。
 * 注解@RestController 标注这也是一个控制器。
 */

/**
 * @author CarryJey
 * @date 2018年9月27日 上午9:36:42
 */

@SpringBootApplication
@RestController
public class HrProsApplication{

    @RequestMapping("/")
    public String  hello(){
        return "hello git";
    }

    public static void main(String args[]){
        SpringApplication.run(HrProsApplication.class, args);
    }
}