package com.shihu.my.icbc.service.impl;

import com.shihu.my.api.model.TransferRecord;
import com.shihu.my.api.service.ICBCTransferRecordService;
import com.shihu.my.icbc.dao.TransferRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ICBCTransferRecordServiceImpl implements ICBCTransferRecordService {
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
