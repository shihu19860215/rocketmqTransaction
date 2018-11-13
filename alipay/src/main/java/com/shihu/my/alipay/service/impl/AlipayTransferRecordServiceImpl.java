package com.shihu.my.alipay.service.impl;

import com.shihu.my.alipay.dao.TransferRecordDao;
import com.shihu.my.api.model.TransferRecord;
import com.shihu.my.api.service.AlipayTransferRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlipayTransferRecordServiceImpl implements AlipayTransferRecordService {
    @Autowired
    private TransferRecordDao transferRecordDao;
    @Override
    public TransferRecord get(String id) {
        return transferRecordDao.get(id);
    }

    @Override
    public void insert(TransferRecord transferRecord) {
        transferRecordDao.insert(transferRecord);
    }
}
