package com.shihu.my.work.test;

import com.shihu.my.api.service.ABCAccountService;
import com.shihu.my.api.service.AlipayAccountService;
import com.shihu.my.api.service.ICBCAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {
    @Autowired
    private ICBCAccountService icbcAccountService;
    @Autowired
    private ABCAccountService abcAccountService;
    @Autowired
    private AlipayAccountService alipayAccountService;
    private static final long id=3L;

    @Test
    public void test111(){
        AtomicInteger atomicInteger=new AtomicInteger();
        Runnable runnable1=new Runnable() {
            @Override
            public void run() {
                boolean flag=false;
                while(!flag){
                    try {
                        int n=new Random().nextInt(1000);
                        switch (n%6){
                            case 0:{
                                if(abcAccountService.payToICBC(id,new Random().nextInt(10000)%100)){
                                    flag=true;
                                    atomicInteger.incrementAndGet();
                                }
                                break;
                            }
                            case 1:{
                                if(abcAccountService.payToAlipay(id,new Random().nextInt(10000)%100)){
                                    flag=true;
                                    atomicInteger.incrementAndGet();
                                }
                                break;
                            }
                            case 2:{
                                if(alipayAccountService.payToICBC(id,new Random().nextInt(10000)%100)){
                                    flag=true;
                                    atomicInteger.incrementAndGet();
                                }
                                break;
                            }
                            case 3:{
                                if(alipayAccountService.payToABC(id,new Random().nextInt(10000)%100)){
                                    flag=true;
                                    atomicInteger.incrementAndGet();
                                }
                                break;
                            }
                            case 4:{
                                if(icbcAccountService.payToAlipay(id,new Random().nextInt(10000)%100)){
                                    flag=true;
                                    atomicInteger.incrementAndGet();
                                }
                                break;
                            }
                            case 5:{
                                if(icbcAccountService.payToABC(id,new Random().nextInt(10000)%100)){
                                    flag=true;
                                    atomicInteger.incrementAndGet();
                                }
                                break;
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };
        for(int i=0;i<100;i++){
            new Thread(runnable1).start();
        }
        while(true){
            System.out.println(atomicInteger.get());
            System.out.println("--------------等待------------");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void test222(){
        icbcAccountService.ince(1L,UUID.randomUUID().toString(), -1L);
        abcAccountService.ince(1L,UUID.randomUUID().toString(), -1L);
        alipayAccountService.ince(1L,UUID.randomUUID().toString(), -1L);
    }
}
