package com.shihu.my.api.service;


import com.shihu.my.api.model.TransferRecord;

public interface ICBCTransferRecordService {

    public TransferRecord get(String id);
    public void insert(TransferRecord transferRecord);
}
