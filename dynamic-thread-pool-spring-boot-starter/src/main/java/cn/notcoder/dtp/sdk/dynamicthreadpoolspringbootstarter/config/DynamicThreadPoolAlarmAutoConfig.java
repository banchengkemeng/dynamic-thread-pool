package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config.properties.DynamicThreadPoolAlarmAutoProperties;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.service.IAlarmService;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.service.impl.AlarmServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "dynamic-thread-pool.alarm", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(DynamicThreadPoolAlarmAutoProperties.class)
@ComponentScan("cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.strategy")
public class DynamicThreadPoolAlarmAutoConfig {

    @Bean
    public IAlarmService alarmService() {
        return new AlarmServiceImpl();
    }
}
