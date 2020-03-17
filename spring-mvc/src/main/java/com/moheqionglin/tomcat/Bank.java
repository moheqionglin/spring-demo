package com.moheqionglin.tomcat;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 16:26
 */
public class Bank {
    private String bankName;
    private String cardNO;

    public Bank(String bankName, String cardNO) {
        this.bankName = bankName;
        this.cardNO = cardNO;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNO() {
        return cardNO;
    }

    public void setCardNO(String cardNO) {
        this.cardNO = cardNO;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "bankName='" + bankName + '\'' +
                ", cardNO='" + cardNO + '\'' +
                '}';
    }
}