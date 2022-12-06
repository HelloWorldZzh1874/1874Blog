package com.zzh.aysnc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zzh
 * @description 自定义线程池配置
 * @date 2022/2/7 14:49
 */
@Configuration
public class TaskThreadPoolConfig {
    /**
     * 核心线程池大小
     */
    private final int corePoolSize = 5;

    /**
     * 最大可创建的线程数
     */
    private final int maxPoolSize = 20;

    /**
     * 队列最大长度
     */
    private final int queueCapacity = 50;

    /**
     * 线程池维护线程所允许的空闲时间(秒)
     */
    private final int keepAliveSeconds = 300;


    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(corePoolSize);
        //最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //队列容量
        executor.setQueueCapacity(queueCapacity);
        //活跃时间
        executor.setKeepAliveSeconds(keepAliveSeconds);
        //线程名字前缀
        executor.setThreadNamePrefix("TaskExecutePool-");

        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务 --- 拒绝策略
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }
}
