package com.shihu.my.icbc.dao;

import com.shihu.my.api.model.TransferRecord;

public interface TransferRecordDao {
    public TransferRecord get(String id);
    public void insert(TransferRecord transferRecord);
}
