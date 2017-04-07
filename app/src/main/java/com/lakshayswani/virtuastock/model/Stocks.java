package com.lakshayswani.virtuastock.model;

/**
 * Created by lenovo on 31-03-2017.
 */
public class Stocks {

    private String stockName;

    private String bidPrice;

    private String quantity;

    private String date;

    private String type;

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets stock name.
     *
     * @return the stock name
     */
    public String getStockName() {
        return stockName;
    }

    /**
     * Sets stock name.
     *
     * @param stockName the stock name
     */
    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    /**
     * Gets bid price.
     *
     * @return the bid price
     */
    public String getBidPrice() {
        return bidPrice;
    }

    /**
     * Sets bid price.
     *
     * @param bidPrice the bid price
     */
    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }
}
