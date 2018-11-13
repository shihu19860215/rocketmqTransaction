package com.shihu.my.mq;

import com.alibaba.fastjson.JSONObject;
import com.shihu.my.api.service.*;
import com.shihu.my.api.util.FileUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class Consumer implements MessageListenerConcurrently {
    @Autowired
    private ABCAccountService abcAccountService;
    @Autowired
    private ICBCAccountService icbcAccountService;
    @Autowired
    private AlipayAccountService alipayAccountService;

    public void init() throws MQClientException {
        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("abc_icbc_alipay_consumer_group");

        // Specify name server addresses.
        consumer.setNamesrvAddr("192.168.7.160:9876");

        // Subscribe one more more topics to consume.
        consumer.subscribe("TopicPayTest", "*");
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(this);
        //Launch the consumer instance.
        consumer.start();

        System.out.printf("Consumer Started.%n");

    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            for(MessageExt messageExt:list){
                String msgStr=new String(messageExt.getBody());
                if(null!=msgStr){
                    Map<String,Object> map=JSONObject.parseObject(msgStr,Map.class);
                    String accountIdStr=(String) map.get("accountId");
                    String amountStr= (String) map.get("amount");
                    if(null!=accountIdStr&&null!=amountStr){
                        System.out.println(msgStr+"===="+messageExt.getKeys()+"===="+messageExt.getTags()+"===="+messageExt);
                        Long accountId=Long.valueOf(accountIdStr);
                        Long amount=Long.valueOf(amountStr);
                        switch (messageExt.getTags()){
                            case "ABCToICBC":{
                                icbcAccountService.ince(accountId,messageExt.getKeys(),amount);
                                break;
                            }
                            case "alipayToICBC":{
                                icbcAccountService.ince(accountId,messageExt.getKeys(),amount);
                                break;
                            }
                            case "ABCToAlipay":{
                                alipayAccountService.ince(accountId,messageExt.getKeys(),amount);
                                break;
                            }
                            case "ICBCToAlipay":{
                                alipayAccountService.ince(accountId,messageExt.getKeys(),amount);
                                break;
                            }
                            case "alipayToABC":{
                                abcAccountService.ince(accountId,messageExt.getKeys(),amount);
                                break;
                            }
                            case "ICBCToABC":{
                                abcAccountService.ince(accountId,messageExt.getKeys(),amount);
                                break;
                            }
                        }
                        try {
                            FileUtil.writeToFileAppend("d:/temp/okmsg.txt",messageExt.getKeys()+"===="+messageExt);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
//                        if(new Random().nextInt(100)>80)throw new RuntimeException("运行时异常");
                    }
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }

}
