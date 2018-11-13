package com.shihu.my.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@ImportResource({"classpath:dubbo-consumer.xml"})
public class MqApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MqApplication.class, args).getBean(Consumer.class).init();
        while (true){
            TimeUnit.SECONDS.sleep(5);
        }
    }
}
