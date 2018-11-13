package com.shihu.my.alipay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:dubbo-provider.xml"})
@MapperScan("com.shihu.my.alipay.dao")
public class AlipayApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(AlipayApplication.class, args);
	}
}
