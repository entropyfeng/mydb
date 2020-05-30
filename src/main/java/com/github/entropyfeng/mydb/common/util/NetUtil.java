package com.github.entropyfeng.mydb.common.util;


/**
 * 网络工具类
 * @author entropyfeng
 * @date 2020/2/23 10:34
 */
public class NetUtil {

    /**
     * 检查端口号是否合法
     * @param port 端口号
     * @return false->端口号合法
     * true->端口号合法
     */
    public static boolean isLegalPort(String port) {
        int tempPort;
        try {
            tempPort = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            return false;
        }

        return isLegalPort(tempPort);
    }

    /**
     * 检查端口号是否合法
     *
     * @param port 端口号
     * @return false->端口号合法
     * true->端口号合法
     */
    public static boolean isLegalPort(int port) {
        return port >= 0 && port <= 0xFFFF;
    }

    /**
     * 检查ip地址是否合法
     * @param host ip地址
     * @return true->合法 false->不合法
     */
    public static boolean isLegalHost(String host) {

        return host!=null&&(io.netty.util.NetUtil.isValidIpV4Address(host)||io.netty.util.NetUtil.isValidIpV6Address(host));

    }


}
