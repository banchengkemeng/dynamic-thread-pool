package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 线程池注册中心 Redis 配置
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("dynamic-thread-pool.registry.redis")
public class DynamicThreadPoolRegistryRedisAutoProperties {

    /** Redis地址 */
    private String host;
    /** Redis端口，默认6379 */
    private Integer port = 6379;
    /** Redis数据库，默认是0 */
    private Integer database = 0;
    /** Redis 密码 */
    private String password;
    /** 设置连接池的大小，默认为64 */
    private int connectionPoolSize = 64;
    /** 设置连接池的最小空闲连接数，默认为10 */
    private int connectionMinimumIdleSize = 10;
    /** 设置连接的最大空闲时间（单位：毫秒），超过该时间的空闲连接将被关闭，默认为10000 */
    private int idleConnectionTimeout = 10000;
    /** 设置连接超时时间（单位：毫秒），默认为10000 */
    private int connectTimeout = 10000;
    /** 设置连接重试次数，默认为3 */
    private int retryAttempts = 3;
    /** 设置连接重试的间隔时间（单位：毫秒），默认为1000 */
    private int retryInterval = 1000;
    /** 设置定期检查连接是否可用的时间间隔（单位：毫秒），默认为0，表示不进行定期检查 */
    private int pingInterval = 0;
    /** 设置是否保持长连接，默认为true */
    private Boolean keepAlive = true;
}
