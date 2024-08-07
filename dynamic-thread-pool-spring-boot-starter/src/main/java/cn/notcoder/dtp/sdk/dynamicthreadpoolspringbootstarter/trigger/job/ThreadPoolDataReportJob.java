package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.trigger.job;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config.properties.DynamicThreadPoolRegistryAutoProperties;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.dto.UpdateThreadPoolConfigDTO;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.entity.ThreadPoolConfigEntity;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.registry.IRegistry;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.service.IDynamicThreadPoolService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Slf4j
@AllArgsConstructor
@EnableConfigurationProperties(DynamicThreadPoolRegistryAutoProperties.class)
public class ThreadPoolDataReportJob {

    private IDynamicThreadPoolService dynamicThreadPoolService;

    private IRegistry registry;

    @Scheduled(cron = "${dynamic-thread-pool.registry.report-cron}")
    public void reportThreadPoolData() {
        List<ThreadPoolConfigEntity> threadPoolConfigEntities = dynamicThreadPoolService.queryThreadPoolList();
        registry.reportThreadPool(threadPoolConfigEntities);

        log.info("动态线程池, 上报线程池信息: {}", threadPoolConfigEntities);

        // 遍历每个线程池信息, 上报配置信息
        threadPoolConfigEntities.forEach(threadPoolConfigEntity -> {
            UpdateThreadPoolConfigDTO updateThreadPoolConfigDTO = UpdateThreadPoolConfigDTO
                    .buildUpdateThreadPoolConfigDTO(threadPoolConfigEntity);

            registry.reportUpdateThreadPoolConfigParameter(updateThreadPoolConfigDTO);

            log.info("动态线程池, 上报线程池配置信息: {}", updateThreadPoolConfigDTO);
        });
    }
}
