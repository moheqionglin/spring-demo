package com.moheqionglin.explicitjavacode.message;

import java.io.Serializable;
import java.math.BigDecimal;

public class Order implements Serializable {
    private Integer orderId;
    private Integer productId;
    private BigDecimal price;
    private String address;


    public Order(Integer orderId, Integer productId, BigDecimal price, String address) {
        this.orderId = orderId;
        this.productId = productId;
        this.price = price;
        this.address = address;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", price=" + price +
                ", address='" + address + '\'' +
                '}';
    }
}
