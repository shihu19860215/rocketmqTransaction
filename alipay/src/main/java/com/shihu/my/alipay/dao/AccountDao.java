package com.shihu.my.alipay.dao;


import com.shihu.my.api.model.Account;

public interface AccountDao {

    public Account get(Long id);
    public long update(Account account);
}
