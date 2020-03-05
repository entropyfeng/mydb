package log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * @author entropyfeng
 * @date 2020/3/5 12:35
 */
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        // 直接打印字符串
        logger.info("姓名:凤思高举,密码:******");

        String username = "凤思高举";
        String password = "******";

        // 拼接字符串写法
        logger.info("姓名:" + username + ",密码:" + password);

        // 使用MessageFormat的写法
        logger.info(MessageFormat.format("姓名:{0},密码:{1}", username, password));
    }
}

