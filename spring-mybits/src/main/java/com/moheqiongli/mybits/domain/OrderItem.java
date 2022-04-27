package com.moheqiongli.mybits.domain;


import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author wanli.zhou
 * @description
 * @time 2020-01-15 22:24
 */
public class OrderItem {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private String productName;
    private String productProfileImg;
    private String productSize;
    private Integer productCnt;
    private BigDecimal productTotalPrice;
    private BigDecimal productUnitPrice;
    private boolean productSanzhuang;
    private String chajiaTotalWeight;
    private BigDecimal chajiaTotalPrice;
    private Boolean jianhuoSuccess;
    private Timestamp jianhuoTime;
    private boolean hasDrawback = false;

    private Timestamp createdTime;
    private Timestamp modifiedTime;

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public boolean isHasDrawback() {
        return hasDrawback;
    }

    public void setHasDrawback(boolean hasDrawback) {
        this.hasDrawback = hasDrawback;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductProfileImg() {
        return productProfileImg;
    }

    public void setProductProfileImg(String productProfileImg) {
        this.productProfileImg = productProfileImg;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public Integer getProductCnt() {
        return productCnt;
    }

    public void setProductCnt(Integer productCnt) {
        this.productCnt = productCnt;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public boolean isProductSanzhuang() {
        return productSanzhuang;
    }

    public void setProductSanzhuang(boolean productSanzhuang) {
        this.productSanzhuang = productSanzhuang;
    }

    public String getChajiaTotalWeight() {
        return chajiaTotalWeight;
    }

    public void setChajiaTotalWeight(String chajiaTotalWeight) {
        this.chajiaTotalWeight = chajiaTotalWeight;
    }

    public BigDecimal getChajiaTotalPrice() {
        return chajiaTotalPrice;
    }

    public void setChajiaTotalPrice(BigDecimal chajiaTotalPrice) {
        this.chajiaTotalPrice = chajiaTotalPrice;
    }

    public Boolean getJianhuoSuccess() {
        return jianhuoSuccess;
    }

    public void setJianhuoSuccess(Boolean jianhuoSuccess) {
        this.jianhuoSuccess = jianhuoSuccess;
    }

    public Timestamp getJianhuoTime() {
        return jianhuoTime;
    }

    public BigDecimal getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(BigDecimal productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public void setJianhuoTime(Timestamp jianhuoTime) {
        this.jianhuoTime = jianhuoTime;
    }
}