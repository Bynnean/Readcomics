package com.alipay.sdk.pay;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * ${desc}
 *
 * @author bin2.he@gmail.com
 * @date 16-10-9-上午11:25
 */
public class PayEntity {

    @Expose
    public  int   payWay;  //  方式选择  1: 一本，2：任意5本;3,随便看
    @Expose
    public ArrayList<String> bookSel = new ArrayList<String>();

    public PayEntity(){
        super();
    }

    public int getPayWay() {
        return payWay;
    }

    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }

    public ArrayList<String> getBookSel() {
        return bookSel;
    }

    public void setBookSel(ArrayList<String> bookSel) {
        this.bookSel = bookSel;
    }
}
