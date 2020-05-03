package com.github.entropyfeng.mydb.front;

import com.beust.jcommander.Parameter;

/**
 * @author entropyfeng
 */
public class Args {


    @Parameter(names = { "--name", "-n" }, required = true,description = "the operation name of the command.")
    public String operationName;
    @Parameter(names = { "--model", "-m" }, required = true,description = "the operation model of the command.")
    public String model;

    @Parameter(names = {"--host","-h"},description = "the host to destination ")
    public String host;

    @Parameter(names = {"--port","-p"},description = "the port to destination ")
    public Integer port;
}
