package com.moheqiongli.mybits.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author wanli.zhou
 * @description
 * @time 2020-01-11 20:43
 *
 *     id , name ,size ,second_category_id ,sanzhung ,show_able , code ,stock
 *     origin_price ,cost_price ,current_price ,supplier_id , sort ,profile_img
 *     sales_cnt,comment_cnt,
 *     lunbo_imgs ,detail_imgs ,zhuanqu_id ,zhuanqu_price , zhuanqu_endTime
 *     max_kanjia_person , created_time ,modified_time
 */
public class Product {

    private Integer id;
    private String name;
    private String size;
    private Integer secondCategoryId;
    private boolean sanzhung;
    private boolean showable;
    private String code;
    private int stock;
    private BigDecimal originPrice;
    private BigDecimal costPrice;
    private BigDecimal currentPrice;
    private Integer supplierId;
    private Integer sort;
    private String profileImg;
    private List<String> lunboImgs;
    private List<String> detailImgs;

    private int salesCnt;
    private int commentCnt;
    private Integer zhuanquId;
    private BigDecimal zhunquPrice;
    private long zhuanquEndTime;
    private int maxKanjiaPerson;
    private Timestamp createdTime;
    private Timestamp modifiedTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getSecondCategoryId() {
        return secondCategoryId;
    }

    public void setSecondCategoryId(Integer secondCategoryId) {
        this.secondCategoryId = secondCategoryId;
    }

    public boolean isSanzhung() {
        return sanzhung;
    }

    public void setSanzhung(boolean sanzhung) {
        this.sanzhung = sanzhung;
    }

    public boolean isShowable() {
        return showable;
    }

    public void setShowable(boolean showable) {
        this.showable = showable;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public List<String> getLunboImgs() {
        return lunboImgs;
    }

    public void setLunboImgs(List<String> lunboImgs) {
        this.lunboImgs = lunboImgs;
    }

    public List<String> getDetailImgs() {
        return detailImgs;
    }

    public void setDetailImgs(List<String> detailImgs) {
        this.detailImgs = detailImgs;
    }

    public Integer getZhuanquId() {
        return zhuanquId;
    }

    public void setZhuanquId(Integer zhuanquId) {
        this.zhuanquId = zhuanquId;
    }

    public BigDecimal getZhunquPrice() {
        return zhunquPrice;
    }

    public void setZhunquPrice(BigDecimal zhunquPrice) {
        this.zhunquPrice = zhunquPrice;
    }

    public long getZhuanquEndTime() {
        return zhuanquEndTime;
    }

    public void setZhuanquEndTime(long zhuanquEndTime) {
        this.zhuanquEndTime = zhuanquEndTime;
    }

    public int getMaxKanjiaPerson() {
        return maxKanjiaPerson;
    }

    public void setMaxKanjiaPerson(int maxKanjiaPerson) {
        this.maxKanjiaPerson = maxKanjiaPerson;
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

    public int getSalesCnt() {
        return salesCnt;
    }

    public void setSalesCnt(int salesCnt) {
        this.salesCnt = salesCnt;
    }

    public int getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(int commentCnt) {
        this.commentCnt = commentCnt;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", secondCategoryId=" + secondCategoryId +
                ", sanzhung=" + sanzhung +
                ", showable=" + showable +
                ", code='" + code + '\'' +
                ", stock=" + stock +
                ", originPrice=" + originPrice +
                ", costPrice=" + costPrice +
                ", currentPrice=" + currentPrice +
                ", supplierId=" + supplierId +
                ", sort=" + sort +
                ", profile_img='" + profileImg + '\'' +
                ", lunbo_imgs=" + lunboImgs +
                ", detail_imgs=" + detailImgs +
                ", salesCnt=" + salesCnt +
                ", commentCnt=" + commentCnt +
                ", zhuanquId=" + zhuanquId +
                ", zhunquPrice=" + zhunquPrice +
                ", zhuanquEndTime=" + zhuanquEndTime +
                ", maxKanjiaPerson=" + maxKanjiaPerson +
                ", createdTime=" + createdTime +
                ", modifiedTime=" + modifiedTime +
                '}';
    }
}