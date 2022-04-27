package com.moheqiongli.mybits.domain;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author wanli.zhou
 * @description
 * @time 2020-01-15 22:24
 */
public class Order {
    private final static long _15MIN = 15 * 60 * 1000;
    private Integer id;
    private String orderNum;
    private Integer userId;
    private Integer addressId;
    private String addressDetail;
    private String addressContract;
    private String yongjinCode;
    private String status;

    private BigDecimal totalPrice;
    private BigDecimal useYongjin;
    private BigDecimal useYue;
    private BigDecimal needPayMoney;
    private BigDecimal hadPayMoney;
    private String chajiaStatus;
    private BigDecimal chajiaPrice;
    private BigDecimal chajiaUseYongjin;
    private BigDecimal chajiaUseYue;
    private BigDecimal chajiaNeedPayMoney;
    private BigDecimal chajiaHadPayMoney;
    private String message;
    private Integer jianhuoyuanId;
    private String jianhuoyuanName;
    private String jianhuoStatus;
    private Boolean hasFahuo;
    private Timestamp createdTime;
    List<OrderItem> items;
    private BigDecimal deliveryFee;
    private boolean deleted;
    private Timestamp modifiedTime;

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Boolean getHasFahuo() {
        return hasFahuo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setHasFahuo(Boolean hasFahuo) {
        this.hasFahuo = hasFahuo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public BigDecimal getUseYue() {
        return useYue;
    }

    public void setUseYue(BigDecimal useYue) {
        this.useYue = useYue;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getAddressContract() {
        return addressContract;
    }

    public void setAddressContract(String addressContract) {
        this.addressContract = addressContract;
    }

    public String getYongjinCode() {
        return yongjinCode;
    }

    public void setYongjinCode(String yongjinCode) {
        this.yongjinCode = yongjinCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getUseYongjin() {
        return useYongjin;
    }

    public void setUseYongjin(BigDecimal useYongjin) {
        this.useYongjin = useYongjin;
    }

    public BigDecimal getNeedPayMoney() {
        return needPayMoney;
    }

    public void setNeedPayMoney(BigDecimal needPayMoney) {
        this.needPayMoney = needPayMoney;
    }

    public BigDecimal getHadPayMoney() {
        return hadPayMoney;
    }

    public void setHadPayMoney(BigDecimal hadPayMoney) {
        this.hadPayMoney = hadPayMoney;
    }

    public String getChajiaStatus() {
        return chajiaStatus;
    }

    public void setChajiaStatus(String chajiaStatus) {
        this.chajiaStatus = chajiaStatus;
    }

    public BigDecimal getChajiaPrice() {
        return chajiaPrice;
    }

    public void setChajiaPrice(BigDecimal chajiaPrice) {
        this.chajiaPrice = chajiaPrice;
    }

    public BigDecimal getChajiaUseYongjin() {
        return chajiaUseYongjin;
    }

    public void setChajiaUseYongjin(BigDecimal chajiaUseYongjin) {
        this.chajiaUseYongjin = chajiaUseYongjin;
    }

    public BigDecimal getChajiaUseYue() {
        return chajiaUseYue;
    }

    public void setChajiaUseYue(BigDecimal chajiaUseYue) {
        this.chajiaUseYue = chajiaUseYue;
    }

    public BigDecimal getChajiaNeedPayMoney() {
        return chajiaNeedPayMoney;
    }

    public void setChajiaNeedPayMoney(BigDecimal chajiaNeedPayMoney) {
        this.chajiaNeedPayMoney = chajiaNeedPayMoney;
    }

    public BigDecimal getChajiaHadPayMoney() {
        return chajiaHadPayMoney;
    }

    public void setChajiaHadPayMoney(BigDecimal chajiaHadPayMoney) {
        this.chajiaHadPayMoney = chajiaHadPayMoney;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getJianhuoyuanId() {
        return jianhuoyuanId;
    }

    public void setJianhuoyuanId(Integer jianhuoyuanId) {
        this.jianhuoyuanId = jianhuoyuanId;
    }

    public String getJianhuoyuanName() {
        return jianhuoyuanName;
    }

    public void setJianhuoyuanName(String jianhuoyuanName) {
        this.jianhuoyuanName = jianhuoyuanName;
    }

    public String getJianhuoStatus() {
        return jianhuoStatus;
    }

    public void setJianhuoStatus(String jianhuoStatus) {
        this.jianhuoStatus = jianhuoStatus;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}