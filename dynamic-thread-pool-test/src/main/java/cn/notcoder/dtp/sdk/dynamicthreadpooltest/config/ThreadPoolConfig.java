package cn.notcoder.dtp.sdk.dynamicthreadpooltest.config;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.hook.ResizableCapacityLinkedBlockingQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import cn.notcoder.dtp.sdk.dynamicthreadpooltest.config.propertis.ThreadPoolConfigAutoProperties;

import java.util.concurrent.*;


@Slf4j
@EnableAsync
@Configuration
@EnableConfigurationProperties(ThreadPoolConfigAutoProperties.class)
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor01(ThreadPoolConfigAutoProperties properties) {
        return new ThreadPoolExecutor(
                properties.getCorePoolSize(),
                properties.getMaxPoolSize(),
                properties.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new ResizableCapacityLinkedBlockingQueue<>(properties.getBlockQueueSize()),
                Executors.defaultThreadFactory(),
                getRejectedExecutionHandler(properties.getPolicy())
        );
    }

    @Bean
    public ThreadPoolExecutor threadPoolExecutor02(ThreadPoolConfigAutoProperties properties) {
        return new ThreadPoolExecutor(
                properties.getCorePoolSize(),
                properties.getMaxPoolSize(),
                properties.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new ResizableCapacityLinkedBlockingQueue<>(properties.getBlockQueueSize()),
                Executors.defaultThreadFactory(),
                getRejectedExecutionHandler(properties.getPolicy())
        );
    }


    /**
     * 获取线程池拒绝策略
     * @param policy 策略名
     * @return 策略
     */
    private RejectedExecutionHandler getRejectedExecutionHandler(String policy) {
        switch (policy) {
            case "DiscardPolicy":;
                return new ThreadPoolExecutor.DiscardPolicy();
            case "DiscardOldestPolicy":
                return new ThreadPoolExecutor.DiscardOldestPolicy();
            case "CallerRunsPolicy":;
                return new ThreadPoolExecutor.CallerRunsPolicy();
            case "AbortPolicy":
            default:
                return new ThreadPoolExecutor.AbortPolicy();
        }
    }
}
