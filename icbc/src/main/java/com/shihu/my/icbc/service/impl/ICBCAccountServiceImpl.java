package com.shihu.my.icbc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.shihu.my.api.model.Account;
import com.shihu.my.api.model.TransferRecord;
import com.shihu.my.api.service.ICBCAccountService;
import com.shihu.my.api.service.ICBCTransferRecordService;
import com.shihu.my.icbc.dao.AccountDao;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 城市业务逻辑实现类
 *
 * Created by bysocket on 07/02/2017.
 */
@Service("icbcAccountService")
public class ICBCAccountServiceImpl implements ICBCAccountService {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ICBCTransferRecordService transferRecordService;
    @Autowired
    private TransactionMQProducer producer;


    @Override
    public Account get(long id) {
        return accountDao.get(id);
    }


    @Override
    public boolean payToABC(long id, long amount) {
        return payToOther(id,amount,"ICBCToABC");
    }

    @Override
    public boolean payToAlipay(long id, long amount) {
        return payToOther(id,amount,"ICBCToAlipay");
    }

    private boolean payToOther(long id, long amount,String tag){
        Map<String,Object> map=new HashMap<>();
        String transferRecordId=UUID.randomUUID().toString();
        System.out.println("transferRecordId="+transferRecordId);
        map.put("accountId",String.valueOf(id));
        map.put("amount",String.valueOf(amount));
        String msgStr=JSONObject.toJSONString(map);
        Message msg =
                new Message("TopicPayTest", tag, transferRecordId,
                        msgStr.getBytes());
        try {
            SendResult sendResult = producer.sendMessageInTransaction(msg, null);

            System.out.println(sendResult);
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        TransferRecord transferRecord=transferRecordService.get(transferRecordId);
        if(null!=transferRecord){
            return true;
        }else {
            return false;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void ince(long accountId,String transferRecordId, long amount) {
        TransferRecord transferRecord=transferRecordService.get(transferRecordId);
        if(null!=transferRecord){
            return ;
        }
        Account account=get(accountId);
        account.setOldBalance(account.getBalance());
        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        account.setBalance(account.getBalance()+amount);
        if(account.getBalance()<0){
            throw new RuntimeException("余额不足"+account.getOldBalance()+"----"+amount+"----"+account.getBalance());
        }
        long result=accountDao.update(account);
        if(result>0){
            transferRecord=new TransferRecord();
            transferRecord.setId(transferRecordId);
            transferRecord.setBalance(amount);
            transferRecordService.insert(transferRecord);
            if(new Random().nextInt(100)>80)throw new RuntimeException("事务中异常");
        }else {
            throw  new RuntimeException("更新时成功记录为0条");
        }
    }
}
