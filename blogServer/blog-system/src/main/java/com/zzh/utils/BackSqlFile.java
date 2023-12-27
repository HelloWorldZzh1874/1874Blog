package com.zzh.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.zzh.common.exception.QuartzJobException;
import com.zzh.vo.BackSqlVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 37436
 */
@Component
public class BackSqlFile {
    static final Logger log = LoggerFactory.getLogger(BackSqlFile.class);

    @Autowired
    private SshConnectionUtil sshConnectionUtil;

    // 锁，防止并发备份
    private final Lock lock = new ReentrantLock();

    // 日期格式化
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HHmmss");

    /**
     * 备份数据库生成file
     */
    public void backupSqlFile(BackSqlVO property) {
        // 获取ssh主机
        Connection connection = sshConnectionUtil.getConnection(property.getHost(), property.getHostUsername(), property.getHostPwd());
        try {
            Session openSession = connection.openSession();
            // 上锁，如果拿不到锁抛出异常
            if (!lock.tryLock()) {
                throw new QuartzJobException("备份任务正在执行!");
            }
            Path dirPath = Paths.get(property.getBackupDir());
            String database = property.getDatabases();
            String fileName = formatter.format(LocalDateTime.now()) + "-" + database + ".sql";
            Path backupFile = dirPath.resolve(fileName);
            int count = 0;
            while (Files.exists(backupFile)) {
                backupFile = dirPath.resolve(count + fileName + count);
                count++;
            }
            // 初始化目录
            if (!Files.isDirectory(backupFile.getParent())) {
                Files.createDirectories(backupFile.getParent());
            }
            try (
                    InputStream streamGobbler = new StreamGobbler(openSession.getStdout());
                    OutputStream out = new BufferedOutputStream(Files.newOutputStream(backupFile, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING));
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(streamGobbler);
            ) {
                log.info("=================== " + database + "开始备份  ===============================");
                // 创建备份文件文件
                Files.createDirectories(backupFile.getParent());
                String commend = "docker exec mysql mysqldump -u" + property.getDataUsername() + " -p" + property.getDataPwd() + " " + database;
                openSession.execCommand(commend);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = (bufferedInputStream.read(buffer))) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            } finally {
                lock.unlock();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
