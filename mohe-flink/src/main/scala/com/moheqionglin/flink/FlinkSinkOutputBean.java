package com.moheqionglin.flink;

import java.io.Serializable;

/**
 * @author zhangsx
 */
public class FlinkSinkOutputBean implements Serializable {
    public static final String MONTHLY_REPORT_DATE_FORMAT = "yyyy-MM";
    public static final String DAILY_REPORT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String SECOND_REPORT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String MINUTE_REPORT_DATE_FORMAT = "yyyy-MM-dd HH:mm";
    private Long bizTime;
    private Long createdTime;
    private Long modifiedTime;

    public static final String TYPE_GROUP = "group";

    private String type;
    private Long typeId;
    private String typeName;
    private String minute;
    private Long activityCount;
    private Long count;

    private String minuteStart;
    private String minuteEnd;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public Long getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(Long activityCount) {
        this.activityCount = activityCount;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getMinuteStart() {
        return minuteStart;
    }

    public void setMinuteStart(String minuteStart) {
        this.minuteStart = minuteStart;
    }

    public String getMinuteEnd() {
        return minuteEnd;
    }

    public void setMinuteEnd(String minuteEnd) {
        this.minuteEnd = minuteEnd;
    }
    public Long getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getModifiedTime() {
        return this.modifiedTime;
    }

    public void setModifiedTime(Long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Long getBizTime() {
        return this.bizTime;
    }

    public void setBizTime(Long bizTime) {
        this.bizTime = bizTime;
    }
}