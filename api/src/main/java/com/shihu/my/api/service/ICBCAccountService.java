package com.shihu.my.api.service;


import com.shihu.my.api.model.Account;

import java.util.Map;

public interface ICBCAccountService {

    public Account get(long id);
    public boolean payToABC(long id,long amount);
    public boolean payToAlipay(long id,long amount);
    public void ince(long accountId,String transferRecordId, long amount);
}
