package com.moheqionglin.remotecontroller;

/**
 * @ClassName : CommandRequest
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-17 15:23
 */
public class CommandRequest {
    String args[] = null;

    public CommandRequest(String[] args) {
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }
}