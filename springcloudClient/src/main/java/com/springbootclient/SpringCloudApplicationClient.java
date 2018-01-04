package com.springbootclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudApplicationClient {

	private static final Logger logger = LoggerFactory.getLogger(SpringCloudApplicationClient .class);

	/***
	 * @EnableDiscoveryClient 标志该应用作为 Eureka Client ，并会自动化读取 Eureka 相关配置。还有向服务注册中心发现服务并进行调用。
	 @LoadBalanced 标志着 RestTemplate 是通过 Ribbon 客户端负载均衡去调用服务提供者集群的。即可以在获取的服务提供者实例列表中，通过 Ribbon 进行选择某实例，然后调用该服务实例。
	 */

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudApplicationClient.class, args);
		logger.info("client启动完成请开始你的表演。		+_+");
	}
}
