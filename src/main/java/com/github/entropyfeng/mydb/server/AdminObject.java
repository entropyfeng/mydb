package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.config.CommonConfig;
import com.github.entropyfeng.mydb.config.Constant;
import com.github.entropyfeng.mydb.core.domain.*;
import com.github.entropyfeng.mydb.server.persistence.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.util.concurrent.*;
import java.util.regex.Pattern;

import static com.github.entropyfeng.mydb.config.Constant.BACK_UP_PATH_NAME;
import static java.util.regex.Pattern.compile;

/**
 * @author entropyfeng
 */
public class AdminObject {
    private static final Logger logger = LoggerFactory.getLogger(AdminObject.class);

    private ServerDomain serverDomain;

    public AdminObject(ServerDomain serverDomain) {
        this.serverDomain = serverDomain;
    }



}
