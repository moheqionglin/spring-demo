package com.moheqionglin.geomesa;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-11-14 15:01
 */
public class LbsBean {
    private String bizId;
    private String bizfield1;
    private long biztime;
    private double lon;
    private double lat;

    public LbsBean(String bizId, String bizfield1, long biztime, double lon, double lat) {
        this.bizId = bizId;
        this.bizfield1 = bizfield1;
        this.biztime = biztime;
        this.lon = lon;
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "LbsBean{" +
                "bizId='" + bizId + '\'' +
                ", bizfield1='" + bizfield1 + '\'' +
                ", biztime=" + biztime +
                ", lon=" + lon +
                ", lat=" + lat +
                '}';
    }

    public String getBizId() {
        return bizId;
    }

    public String getBizfield1() {
        return bizfield1;
    }

    public long getBiztime() {
        return biztime;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }
}