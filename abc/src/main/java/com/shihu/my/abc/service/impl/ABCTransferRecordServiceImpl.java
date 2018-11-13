package com.shihu.my.abc.service.impl;

import com.shihu.my.abc.dao.TransferRecordDao;
import com.shihu.my.api.model.TransferRecord;
import com.shihu.my.api.service.ABCTransferRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ABCTransferRecordServiceImpl implements ABCTransferRecordService {
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
