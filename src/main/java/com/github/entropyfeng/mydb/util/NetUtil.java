package com.github.entropyfeng.mydb.util;

import io.netty.util.internal.StringUtil;

/**
 * 网络工具类
 *
 * @author entropyfeng
 * @date 2020/2/23 10:34
 */
public class NetUtil {

    /**
     * 检查端口号是否合法
     *
     * @param port 端口号
     * @return false->端口号合法
     * true->端口号合法
     */
    public static boolean checkPort(String port) {
        int tempPort;
        try {
            tempPort = Integer.valueOf(port);
        } catch (NumberFormatException e) {
            return false;
        }

        return checkPort(tempPort);
    }

    /**
     * 检查端口号是否合法
     *
     * @param port 端口号
     * @return false->端口号合法
     * true->端口号合法
     */
    public static boolean checkPort(int port) {
        return port >= 0 && port <= 0xFFFF;
    }

    /**
     * 检查ip地址是否合法
     * @param host ip地址
     * @return true->合法 false->不合法
     */
    public static boolean checkHost(String host) {
        boolean res = false;
        if (!StringUtil.isNullOrEmpty(host)) {
            // 定义正则表达式
            String regex = "^([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}$";
            // 判断ip地址是否与正则表达式匹配
            if (host.matches(regex)) {
                res = true;
            }
        }
        return res;

    }

}
