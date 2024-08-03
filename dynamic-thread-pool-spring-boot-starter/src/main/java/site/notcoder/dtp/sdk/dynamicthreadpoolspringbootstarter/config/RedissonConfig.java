//package site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config;
//
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.codec.JsonJacksonCodec;
//import org.redisson.config.Config;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config.properties.DynamicThreadPoolRegistryRedisAutoProperties;
//import site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.registry.IRegistry;
//import site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.registry.redis.RedisRegistry;
//
////@Component
////@Configuration
////@EnableConfigurationProperties(DynamicThreadPoolRegistryRedisAutoProperties.class)
//public class RedissonConfig {
//
////    @Bean
////    public RedissonClient redissonClient(DynamicThreadPoolRegistryRedisAutoProperties properties) {
////        Config config = new Config();
////
////        config.setCodec(JsonJacksonCodec.INSTANCE);
////
////        config.useSingleServer()
////                .setAddress(String.format("redis://%s:%d", properties.getHost(), properties.getPort()))
////                .setPassword(properties.getPassword())
////                .setDatabase(properties.getDatabase())
////                .setConnectionPoolSize(properties.getConnectionPoolSize())
////                .setConnectionMinimumIdleSize(properties.getConnectionMinimumIdleSize())
////                .setIdleConnectionTimeout(properties.getIdleConnectionTimeout())
////                .setConnectTimeout(properties.getConnectTimeout())
////                .setRetryAttempts(properties.getRetryAttempts())
////                .setRetryInterval(properties.getRetryInterval())
////                .setKeepAlive(properties.getKeepAlive());
////        return Redisson.create(config);
////    }
////
////    @Bean
////    public IRegistry redisRegistry(RedissonClient redissonClient) {
////        return new RedisRegistry(redissonClient);
////    }
//}
