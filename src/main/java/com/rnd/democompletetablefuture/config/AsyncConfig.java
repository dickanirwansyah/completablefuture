package com.rnd.democompletetablefuture.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
@EnableAsync
public class AsyncConfig {

    @Value("${thread.core.poolsize}")
    private int threadCorePoolSize;
    @Value("${thread.max.poolsize}")
    private int threadMaxPoolSize;
    @Value("${thread.queue.capacity}")
    private int threadQueueCapacity;

    @Bean(name = "taxkExecutor")
    public Executor taskExecutor(){
        log.info("initialize bean task executor..");
        log.info("thread core pool size={}",threadCorePoolSize);
        log.info("thread max pool size={}",threadMaxPoolSize);
        log.info("thread queue capacity={}",threadQueueCapacity);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadCorePoolSize);
        executor.setMaxPoolSize(threadMaxPoolSize);
        executor.setQueueCapacity(threadQueueCapacity);
        executor.setThreadNamePrefix("userThread-");
        executor.initialize();
        return executor;
    }
}
