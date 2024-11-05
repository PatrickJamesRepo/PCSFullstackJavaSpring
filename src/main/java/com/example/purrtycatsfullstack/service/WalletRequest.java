package com.example.purrtycatsfullstack.service;

public class WalletRequest {
    private String walletType;

    public WalletRequest(String walletType) {
        this.walletType = walletType;
    }

    public String getWalletType() {
        return walletType;
    }

    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }
}
