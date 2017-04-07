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

    /**
     * The Stocks.
     */
    List<Stocks> stocks;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Gets balance.
     *
     * @return the balance
     */
    public String getBalance() {
        return balance;
    }

    /**
     * Sets balance.
     *
     * @param balance the balance
     */
    public void setBalance(String balance) {
        this.balance = balance;
    }

    /**
     * Gets uid.
     *
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets uid.
     *
     * @param uid the uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets email id.
     *
     * @return the email id
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * Sets email id.
     *
     * @param emailId the email id
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * Gets phone no.
     *
     * @return the phone no
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * Sets phone no.
     *
     * @param phoneNo the phone no
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * Gets stocks.
     *
     * @return the stocks
     */
    public List<Stocks> getStocks() {
        return stocks;
    }

    /**
     * Sets stocks.
     *
     * @param stocks the stocks
     */
    public void setStocks(List<Stocks> stocks) {
        this.stocks = stocks;
    }

    /**
     * Check sell boolean.
     *
     * @param stockName    the stock name
     * @param sellQuantity the sell quantity
     * @return the boolean
     */
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

    /**
     * Balance update.
     *
     * @param tradeType the trade type
     * @param bidPrice  the bid price
     * @param quantity  the quantity
     */
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
