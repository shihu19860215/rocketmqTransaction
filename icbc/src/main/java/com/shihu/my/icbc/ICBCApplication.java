package com.shihu.my.icbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:dubbo-provider.xml"})
@MapperScan("com.shihu.my.icbc.dao")
public class ICBCApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ICBCApplication.class, args);
	}
}
