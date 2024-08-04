package cn.notcoder.dtp.sdk.dynamicthreadpooltest.config.propertis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 线程池配置
 */

@ConfigurationProperties(prefix = "thread.pool.executor.config", ignoreInvalidFields = true)
@Data
public class ThreadPoolConfigAutoProperties {
    private Integer corePoolSize;
    private Integer maxPoolSize;
    private Integer keepAliveTime;
    private Integer blockQueueSize;
    private String policy;
}
