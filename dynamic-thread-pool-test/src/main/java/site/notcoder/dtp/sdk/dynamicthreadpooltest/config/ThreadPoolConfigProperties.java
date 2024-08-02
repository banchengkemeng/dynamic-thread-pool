package site.notcoder.dtp.sdk.dynamicthreadpooltest.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 线程池配置
 */

@ConfigurationProperties(prefix = "thread.pool.executor.config")
@Data
public class ThreadPoolConfigProperties {
    private Integer corePoolSize;
    private Integer maxPoolSize;
    private Integer keepAliveTime;
    private Integer blockQueueSize;
    private String policy;
}
