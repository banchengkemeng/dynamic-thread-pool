package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("dynamic-thread-pool.web")
public class DynamicThreadPoolWebAutoProperties {

    /**
     * 是否开启Web管理界面, 默认关闭
     */
    private Boolean enabled = false;
    /**
     * WEB根路径
     */
    private String contextPath = "/dtp";
    /**
     * 权限认证
     */
    private Auth auth = new Auth();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Auth {

        /* 是否开启权限验证, 默认关闭 */
        private Boolean enable = false;

        /* 用户名, 默认是dtp */
        private String username = "dtp";

        /* 密码, 默认是dtp */
        private String password = "dtp";
    }


}
