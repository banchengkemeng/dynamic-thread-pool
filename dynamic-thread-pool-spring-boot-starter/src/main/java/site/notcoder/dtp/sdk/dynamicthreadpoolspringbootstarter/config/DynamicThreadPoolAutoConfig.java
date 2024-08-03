package site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config.properties.DynamicThreadPoolAutoProperties;
import site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config.properties.DynamicThreadPoolRegistryRedisAutoProperties;
import site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.registry.IRegistry;
import site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.registry.redis.RedisRegistry;
import site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.service.impl.DynamicThreadPoolService;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自动配置入口
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({
        DynamicThreadPoolAutoProperties.class,
        DynamicThreadPoolRegistryRedisAutoProperties.class,
})
public class DynamicThreadPoolAutoConfig {

    @Bean
    public RedissonClient redissonClient(DynamicThreadPoolRegistryRedisAutoProperties properties) {
        Config config = new Config();
        config.setCodec(JsonJacksonCodec.INSTANCE);
        config.useSingleServer()
                .setAddress(String.format("redis://%s:%d", properties.getHost(), properties.getPort()))
                .setPassword(properties.getPassword())
                .setDatabase(properties.getDatabase())
                .setConnectionPoolSize(properties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(properties.getConnectionMinimumIdleSize())
                .setIdleConnectionTimeout(properties.getIdleConnectionTimeout())
                .setConnectTimeout(properties.getConnectTimeout())
                .setRetryAttempts(properties.getRetryAttempts())
                .setRetryInterval(properties.getRetryInterval())
                .setKeepAlive(properties.getKeepAlive());
        return Redisson.create(config);
    }


    @Bean
    public IRegistry redisRegistry(RedissonClient redissonClient) {
        return new RedisRegistry(redissonClient);
    }

    @Bean
    public DynamicThreadPoolService dynamicThreadPoolService(
            ApplicationContext applicationContext,
            Map<String, ThreadPoolExecutor> threadPoolExecutorMap,
            RedissonClient redissonClient
    ) {
        String applicationName = applicationContext.getEnvironment().getProperty("spring.application.name");
        if (StringUtils.isBlank(applicationName)) {
            log.warn("动态线程池启动提示。SpringBoot 应用未配置应用名(spring.application.name)");
        }

        return new DynamicThreadPoolService(applicationName, threadPoolExecutorMap);
    }
}
