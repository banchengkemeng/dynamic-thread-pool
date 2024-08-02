package site.notcoder.dtp.sdk.dynamicthreadpooltest.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;

@Slf4j
@EnableAsync
@Configuration
@EnableConfigurationProperties(ThreadPoolConfigProperties.class)
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor01(ThreadPoolConfigProperties properties) {
        return new ThreadPoolExecutor(
                properties.getCorePoolSize(),
                properties.getMaxPoolSize(),
                properties.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(properties.getBlockQueueSize()),
                Executors.defaultThreadFactory(),
                getRejectedExecutionHandler(properties.getPolicy())
        );
    }

    @Bean
    public ThreadPoolExecutor threadPoolExecutor02(ThreadPoolConfigProperties properties) {
        return new ThreadPoolExecutor(
                properties.getCorePoolSize(),
                properties.getMaxPoolSize(),
                properties.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(properties.getBlockQueueSize()),
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
