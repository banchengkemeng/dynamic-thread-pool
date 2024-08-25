package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "dynamic-thread-pool.alarm")
public class DynamicThreadPoolAlarmAutoProperties {
    private Boolean enabled = false;
    private List<String > usePlatform;
    private AccessToken accessToken;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccessToken {
        private String dingDing;
    }
}
