package com.moheqiongli.mybits.message;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-07-30 13:46
 */
public class UserInfo implements Serializable {
    private Long id;
    private String sex;
    private String password;
    private String nickName;
    private Date birthday;
    private Timestamp regTime;
    private String headPicture;
    private boolean disable;
    private String openCode;
    private BigDecimal amount;
    private BigDecimal yongjin;
    private String  yongjinCode;
    private String bindYongjinCode;
    private Timestamp createdTime;
    private Timestamp modifiedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Timestamp getRegTime() {
        return regTime;
    }

    public void setRegTime(Timestamp regTime) {
        this.regTime = regTime;
    }

    public String getHeadPicture() {
        return headPicture;
    }

    public void setHeadPicture(String headPicture) {
        this.headPicture = headPicture;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public String getOpenCode() {
        return openCode;
    }

    public void setOpenCode(String openCode) {
        this.openCode = openCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getYongjin() {
        return yongjin;
    }

    public void setYongjin(BigDecimal yongjin) {
        this.yongjin = yongjin;
    }

    public String getYongjinCode() {
        return yongjinCode;
    }

    public void setYongjinCode(String yongjinCode) {
        this.yongjinCode = yongjinCode;
    }

    public String getBindYongjinCode() {
        return bindYongjinCode;
    }

    public void setBindYongjinCode(String bindYongjinCode) {
        this.bindYongjinCode = bindYongjinCode;
    }

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
}