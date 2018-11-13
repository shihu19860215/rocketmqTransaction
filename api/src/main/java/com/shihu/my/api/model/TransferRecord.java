package com.shihu.my.api.model;

import java.io.Serializable;

public class TransferRecord implements Serializable {
    private String id;
    private long balance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }


    @Override
    public String toString() {
        return "TransferRecord{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                '}';
    }
}
