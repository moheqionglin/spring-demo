package com.moheqionglin.remotecontroller.test;

import com.moheqionglin.remotecontroller.Command;
import com.moheqionglin.remotecontroller.CommandHandler;
import com.moheqionglin.remotecontroller.CommandRequest;
import com.moheqionglin.remotecontroller.CommandResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName : RemoteControllerHandler
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-17 17:18
 */
@Component
public class RemoteControllerHandler implements CommandHandler {
    @Autowired
    private Service service;
    @Autowired
    private Dao dao;


    @Override
    public Set<Command> commands() {
        Command changeDb = new Command("change.db.url", "change database url, <change.db.url URL>") {
            @Override
            protected CommandResponse process(CommandRequest request) {
                CommandResponse rsp = new CommandResponse();
                String[] args = request.getArgs();
                if(args == null || args.length != 1){
                    rsp.setResult("invalid params, you can type help");
                }else{
                    String origin = dao.getDatabase();
                    dao.setDatabase(args[0]);
                    rsp.setResult("change url from " + origin + " to " + dao.getDatabase());
                }
                return rsp;
            }
        };
        Command showdb = new Command("show.db.url", "show database url, <show.db.url>") {
            @Override
            protected CommandResponse process(CommandRequest request) {
                return new CommandResponse(dao.getDatabase());
            }
        };

        Command showService = new Command("list.service.cache", "list service cache, <list.service.cache>") {
            @Override
            protected CommandResponse process(CommandRequest request) {
                CommandResponse rsp = new CommandResponse();
                rsp.setResult("list cache");
                rsp.setAttacheData(service.getCache());
                return rsp;
            }
        };
        Command addService = new Command("add.service.cache", "add service cache, <add.service.cache key value>") {
            @Override
            protected CommandResponse process(CommandRequest request) {
                CommandResponse rsp = new CommandResponse();
                String[] args = request.getArgs();
                if(args == null || args.length != 2){
                    rsp.setResult("invalid params, you can type help");
                }else{
                    service.getCache().put(args[0], args[2]);
                    rsp.setResult("add cache " + args[0] + " -> " + args[1] + " success!");
                }
                return rsp;
            }
        };
        Command deleteService = new Command("delete.service.cache", "delete service cache, <delete.service.cache key>") {
            @Override
            protected CommandResponse process(CommandRequest request) {
                CommandResponse rsp = new CommandResponse();
                String[] args = request.getArgs();
                if(args == null || args.length != 1){
                    rsp.setResult("invalid params, you can type help");
                }else{
                    service.getCache().remove(args[1]);
                    rsp.setResult("remove cache " + args[1] + " success!");
                }
                return rsp;
            }
        };
        Set<Command> defaultCommands = new HashSet<>();
        defaultCommands.add(changeDb);
        defaultCommands.add(showdb);
        defaultCommands.add(showService);
        defaultCommands.add(addService);
        defaultCommands.add(deleteService);
        return defaultCommands;
    }

    @Override
    public CommandResponse doHealthy(CommandRequest request) {
        return new CommandResponse("is OK!");
    }

    @Override
    public CommandResponse doStatus(CommandRequest request) {
        return new CommandResponse("status is ....");
    }

}