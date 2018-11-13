package com.shihu.my.api.service;

import com.shihu.my.api.model.Account;

import java.util.Map;

public interface AlipayAccountService {
    public Account get(long id);
    public boolean payToICBC(long id,long amount);
    public boolean payToABC(long id,long amount);
    public void ince(long accountId,String transferRecordId, long amount);
}
