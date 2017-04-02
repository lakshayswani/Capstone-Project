package com.lakshayswani.virtuastock.model;

import java.util.List;

/**
 * Created by lenovo on 31-03-2017.
 */

public class User {

    private String uid;

    private String username;

    private String emailId;

    private String phoneNo;

    private String balance;

    List<Stocks> stocks;

    public User() {
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public List<Stocks> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stocks> stocks) {
        this.stocks = stocks;
    }

    public boolean checkSell(String stockName, int sellQuantity) {
        int bought = 0;
        int sold = 0;
        for (Stocks stock : stocks
                ) {
            if (stock.getStockName().equalsIgnoreCase(stockName)) {
                if (stock.getType().equalsIgnoreCase("buy"))
                    bought = bought + Integer.parseInt(stock.getQuantity());
                else if (stock.getType().equalsIgnoreCase("sell"))
                    sold = sold + Integer.parseInt(stock.getQuantity());
            }
        }
        if (sellQuantity <= (bought - sold))
            return true;
        else
            return false;
    }

    public void balanceUpdate(String tradeType, int bidPrice, int quantity) {
        int currentBalance = Integer.parseInt(balance);
        if(tradeType.equalsIgnoreCase("buy"))
        {
            currentBalance = currentBalance - (bidPrice*quantity);
        }
        else if(tradeType.equalsIgnoreCase("sell"))
        {
            currentBalance = currentBalance + (bidPrice*quantity);
        }
        balance = ""+ currentBalance;
    }
}
