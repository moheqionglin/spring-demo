package com.moheqionglin.remotecontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public interface CommandHandler {
    Set<Command> defaultCommands = new HashSet<>();
    Logger log = LoggerFactory.getLogger(CommandHandler.class);

    default CommandResponse process(String cmd, CommandRequest request){
        for(Command command : defaultCommands()){
            if(command.accept(cmd)){
                return command.process(request);
            }
        }

        for(Command command : commands()){
            if(command.accept(cmd)){
                return command.process(request);
            }
        }
        return new CommandResponse("Usage: fields className fieldName value");
    }

    Set<Command> commands();

    default String name(){
        return this.getClass().getSimpleName();
    }

    default Set<Command> defaultCommands(){
        if(!defaultCommands.isEmpty()){
            return defaultCommands;
        }
        Command helpCmd = new Command("help", "show help info") {
            @Override
            protected CommandResponse process(CommandRequest request) {
                CommandResponse rsp = new CommandResponse();

                for(Command cmd : defaultCommands()){
                    rsp.getAttacheData().put(cmd.command, cmd.helpInfo);
                }

                for(Command cmd : commands()){
                    rsp.getAttacheData().put(cmd.command, cmd.helpInfo);
                }
                return rsp;
            }
        };
        Command statusCmd = new Command("status", "show system status info") {
            @Override
            protected CommandResponse process(CommandRequest request) {
                return doStatus(request);
            }
        };
        Command quitCmd = new Command("quit", "quit telnet client") {
            @Override
            protected CommandResponse process(CommandRequest request) {
                return new CommandResponse("Bye Bye!");
            }

        };
        Command healthyCmd = new Command("healthy", "check system healthy") {
            @Override
            protected CommandResponse process(CommandRequest request) {
                return doHealthy(request);
                
            }
        };

        defaultCommands.add(helpCmd);
        defaultCommands.add(statusCmd);
        defaultCommands.add(quitCmd);
        defaultCommands.add(healthyCmd);
        return defaultCommands;
    }

    CommandResponse doHealthy(CommandRequest request);

    CommandResponse doStatus(CommandRequest request);
    


    default boolean accept(String cmd){
        for(Command command : defaultCommands()){
            if(command.accept(cmd)){
                return true;
            }
        }
        for(Command command : commands()){
            if(command.accept(cmd)){
                return true;
            }
        }
        return false;
    }
}
