package com.github.entropyfeng.mydb.server.config;


import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author entropyfeng
 * @date 2020/2/23 10:11
 */
public final class Constant {
    public static final String USER_NAME="username";
    public static final String PASSWORD="password";
    public static final String HOST="host";
    public static final String PORT="port";
    public static final String CONFIG_FILE_NAME="config.properties";
    public static final String BACK_UP_PATH_NAME="backupPath";
    public static final String DB_NAME="Turtle";
    public static final String SYSTEM_CLOCK_REFRESH="systemClockRefresh";
    public static final String SYSTEM_DUMP_CIRCLE="systemDumpCircle";

    public static final String VALUES_SUFFIX="-values.dump";
    public static final String HASH_SUFFIX="-hash.dump";
    public static final String SET_SUFFIX="-set.dump";
    public static final String LIST_SUFFIX="-list.dump";
    public static final String ORDER_SET_SUFFIX="-orderSet.dump";

    public static final Integer CONSUMER_THREAD_NUMBER =5;
    /**
     * magic number
     */
    public static final byte[] MAGIC_NUMBER={'t','u','r','t','l','e'};

    /**
     * 1M
     * and all dump files at least support 1P memory
     */
    public static final Integer FILE_CHUCK_SIZE=1024*1024;

}
