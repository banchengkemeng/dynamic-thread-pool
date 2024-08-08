package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config;


import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config.properties.DynamicThreadPoolWebAutoProperties;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.controller.AuthController;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.controller.SettingsController;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.controller.ThreadPoolController;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.exception.DynamicThreadPoolWebGlobalExceptionHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(DynamicThreadPoolWebAutoProperties.class)
public class DynamicThreadPoolWebAutoConfig {
    @Bean
    public AuthController dynamicThreadPoolAuthController() {
        return new AuthController();
    }

    @Bean
    public ThreadPoolController dynamicThreadPoolThreadPoolController() {
        return new ThreadPoolController();
    }

    @Bean
    public SettingsController dynamicThreadPoolSettingsController() {
        return new SettingsController();
    }

    @Bean
    public DynamicThreadPoolWebGlobalExceptionHandler dynamicThreadPoolWebGlobalExceptionHandler() {
        return new DynamicThreadPoolWebGlobalExceptionHandler();
    }
}