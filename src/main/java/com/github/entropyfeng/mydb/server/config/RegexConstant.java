package com.github.entropyfeng.mydb.server.config;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author entropyfeng
 */
public final class RegexConstant {

    public static final Pattern VALUES_PATTERN = compile("-values\\.dump$");

    public static final Pattern SET_PATTERN = compile("-set\\.dump$");

    public static final Pattern ORDER_SET_PATTERN = compile("-orderSet\\.dump$");

    public static final Pattern LIST_PATTERN = compile("-list\\.dump$");

    public static final Pattern HASH_PATTERN = compile("-hash\\.dump$");

    public static final Pattern BACK_UP_PATTERN = compile("^[1-9]\\d*?(-hash\\.dump|-list\\.dump|-orderSet\\.dump|-set\\.dump|-values\\.dump)$");



}
