package com.springboot.caoqing;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by 曹清 on 2017/12/20.
 */

@SpringBootApplication
@MapperScan("com.springboot.caoqing.mapper")//将项目中对应的mapper类的路径加进来就可以了
@EnableEurekaServer
public class CaoqingApplication {

	private static final Logger logger = LoggerFactory.getLogger(CaoqingApplication .class);

	public static void main(String[] args) {

		SpringApplication.run(CaoqingApplication.class, args);
		logger.info("server启动完成请开始你的表演。		*_*");
	}
	//启动报错注册服务错误   →  原因发现服务超时，隔30秒刷新一次心跳，    →  等项目启动成功后  心跳正常可以注册   则错误消失
}
