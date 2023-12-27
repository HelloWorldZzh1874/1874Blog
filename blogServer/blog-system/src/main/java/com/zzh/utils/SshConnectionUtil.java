package com.zzh.utils;


import ch.ethz.ssh2.Connection;
import com.zzh.common.exception.baseException.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author zzh
 * @version 1.0
 * @date 2020/1/26 14:43
 * @description ssh连接工具类
 */
@Component
public class SshConnectionUtil {
    private static final Logger log = LoggerFactory.getLogger(SshConnectionUtil.class);

    public Connection getConnection(String host, String username, String password) {
        // 默认22端口
        Connection connection = new Connection(host);
        try {
            connection.connect();
            boolean authenticated = connection.authenticateWithPassword(username, password);
            if(!authenticated){
                throw new MyException("连接失败，认证错误！");
            }
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
        return connection;
    }
}
