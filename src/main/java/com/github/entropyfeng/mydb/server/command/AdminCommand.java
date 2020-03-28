package com.github.entropyfeng.mydb.server.command;

import com.github.entropyfeng.mydb.server.command.IClientCommand;

/**
 * @author entropyfeng
 */
public class AdminCommand implements IClientCommand {

    private String operationName;
    @Override
    public String getOperationName() {
        return operationName;
    }

    public AdminCommand(String operationName) {
        this.operationName=operationName;
    }
}
