package com.moheqionglin.flink;

import java.io.Serializable;

/**
 * @ClassName : FlinkInputBean
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-29 22:49
 */
public class FlinkInputBean implements Serializable {
    private Long gnssTime;
    private Long serverTime;
    private String product;
    private Long groupId;
    private String entityName;
    private Long entityId;

    public Long getGnssTime() {
        return gnssTime;
    }

    public void setGnssTime(Long gnssTime) {
        this.gnssTime = gnssTime;
    }

    public Long getServerTime() {
        return serverTime;
    }

    public void setServerTime(Long serverTime) {
        this.serverTime = serverTime;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

}