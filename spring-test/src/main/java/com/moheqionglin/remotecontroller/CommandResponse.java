package com.moheqionglin.remotecontroller;

import java.util.HashMap;

/**
 * @ClassName : CommandRst
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-17 15:23
 */
public class CommandResponse {
    // 组件名字
    private String commandComponentName;

    private String result;

    private HashMap<String, String> attacheData = new HashMap<>();

    public CommandResponse() {
    }

    public CommandResponse(String result) {
        this.result = result;
    }

    public String getCommandComponentName() {
        return commandComponentName;
    }

    public void setCommandComponentName(String commandComponentName) {
        this.commandComponentName = commandComponentName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public HashMap<String, String> getAttacheData() {
        return attacheData;
    }

    public void setAttacheData(HashMap<String, String> attacheData) {
        this.attacheData = attacheData;
    }
}