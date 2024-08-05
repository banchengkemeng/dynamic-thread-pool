package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "dynamic-thread-pool.registry")
public class DynamicThreadPoolRegistryAutoProperties {
    private String reportCron = "0/20 * * * * ?";
}
