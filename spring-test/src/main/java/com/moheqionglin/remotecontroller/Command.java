package com.moheqionglin.remotecontroller;

import java.util.Objects;

/**
 * @ClassName : Command
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-17 16:16
 */
public abstract class Command {
    public String command;
    public String helpInfo;

    public Command(String command, String helpInfo) {
        this.command = command;
        this.helpInfo = helpInfo;
    }

    public boolean accept(String cmd){
        return this.command.equals(cmd);
    }

    protected abstract CommandResponse process(CommandRequest request);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command1 = (Command) o;
        return Objects.equals(command, command1.command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command);
    }


}