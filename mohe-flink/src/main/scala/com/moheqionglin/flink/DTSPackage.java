package com.moheqionglin.flink;

import com.alibaba.fastjson.JSONObject;


public class DTSPackage {
    public static int TYPE_POINT = 1;
    public static int TYPE_SIGAL_READER_FINISH = 2;

    private Long ruleId;
    private String ruleName;
    private JSONObject data;
    private int type;

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static boolean isReaderFinishPackage(DTSPackage p){
        return p.getType() == TYPE_SIGAL_READER_FINISH;
    }

    @Override
    public String toString() {
        return "DTSPackage{" +
                "ruleId=" + ruleId +
                ", ruleName='" + ruleName + '\'' +
                ", data=" + data +
                ", type=" + type +
                '}';
    }
}
