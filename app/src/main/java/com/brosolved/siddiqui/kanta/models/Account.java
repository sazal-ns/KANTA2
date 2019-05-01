package com.brosolved.siddiqui.kanta.models;

/*
 * com.brosolved.siddiqui.kanta.models is created by Noor Nabiul Alam Siddiqui on 5/1/2019
 *
 * BroSolved (c) 2019.
 */
public class Account {
  private   String month;
  private   int amount;
  private   int product;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }
}
