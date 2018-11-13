package com.shihu.my.icbc.mq;

import com.alibaba.fastjson.JSONObject;
import com.shihu.my.api.model.TransferRecord;
import com.shihu.my.api.service.ABCAccountService;
import com.shihu.my.api.service.ABCTransferRecordService;
import com.shihu.my.api.service.ICBCAccountService;
import com.shihu.my.api.service.ICBCTransferRecordService;
import com.shihu.my.api.util.FileUtil;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@Component
public class TransactionListenerImpl implements TransactionListener {
    @Autowired
    private ICBCTransferRecordService transferRecordService;
    @Autowired
    private ICBCAccountService accountService;

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            String tag=msg.getTags();
            String msgStr=new String(msg.getBody());
            Map<String,Object> map=JSONObject.parseObject(msgStr,Map.class);
            return ince(map,msg);
        }catch (Exception e){
            e.printStackTrace();
        }
        return LocalTransactionState.UNKNOW;
    }

    private LocalTransactionState ince(Map<String,Object> map,Message msg){
        long accountId=Long.valueOf((String)map.get("accountId"));
        long amount=Long.valueOf( (String)map.get("amount"));
        try {
            accountService.ince(accountId,msg.getKeys() ,amount * -1);
        }catch (Exception e){
            e.printStackTrace();
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        if(new Random().nextInt(100)>80)throw new RuntimeException("事务后异常");
        return LocalTransactionState.COMMIT_MESSAGE;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        String id=msg.getKeys();
        try {
            TransferRecord transferRecord=transferRecordService.get(id);
            if(new Random().nextInt(100)>80)throw new RuntimeException("事务后异常");
            if(null==transferRecord){
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }else {
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        }catch (Exception e){
            e.printStackTrace();
            return LocalTransactionState.UNKNOW;
        }
    }
}
