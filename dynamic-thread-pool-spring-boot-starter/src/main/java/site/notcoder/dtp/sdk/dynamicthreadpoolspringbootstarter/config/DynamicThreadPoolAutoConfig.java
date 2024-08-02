package site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.service.DynamicThreadPoolService;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自动配置入口
 */
@Configuration
@Slf4j
public class DynamicThreadPoolAutoConfig {

    @Bean
    public DynamicThreadPoolService dynamicThreadPoolService(
            ApplicationContext applicationContext,
            Map<String, ThreadPoolExecutor> threadPoolExecutorMap) {
        String applicationName = applicationContext.getEnvironment().getProperty("spring.application.name");
        if (StringUtils.isBlank(applicationName)) {
            log.warn("动态线程池启动提示。SpringBoot 应用未配置应用名(spring.application.name)");
        }

        return new DynamicThreadPoolService(applicationName, threadPoolExecutorMap);
    }
}
