package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.service.impl;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.dto.UpdateThreadPoolConfigDTO;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.entity.ThreadPoolConfigEntity;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.service.IDynamicThreadPoolService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 动态线程池服务
 */

@Slf4j
@AllArgsConstructor
public class DynamicThreadPoolService implements IDynamicThreadPoolService {

    private String applicationName;

    private Map<String, ThreadPoolExecutor> threadPoolExecutorMap;

    @Override
    public List<ThreadPoolConfigEntity> queryThreadPoolList() {
        ArrayList<ThreadPoolConfigEntity> threadPools = new ArrayList<>();

        threadPoolExecutorMap.forEach((beanName, executor) -> threadPools.add(
                ThreadPoolConfigEntity.buildThreadPoolConfigEntity(
                        applicationName,
                        beanName,
                        executor
                )
        ));

        return threadPools;
    }

    @Override
    public ThreadPoolConfigEntity queryThreadPoolByName(String threadPoolName) {
        return ThreadPoolConfigEntity.buildThreadPoolConfigEntity(
                applicationName,
                threadPoolName,
                threadPoolExecutorMap.get(threadPoolName)
        );
    }

    @Override
    public Boolean updateThreadPoolConfig(UpdateThreadPoolConfigDTO updateThreadPoolConfigDTO) {
        if (updateThreadPoolConfigDTO == null) {
            return false;
        }

        String threadPoolName = updateThreadPoolConfigDTO.getThreadPoolName();
        if (threadPoolName == null) {
            return false;
        }

        ThreadPoolExecutor threadPoolExecutor = threadPoolExecutorMap.get(threadPoolName);
        if (threadPoolExecutor == null) {
            return false;
        }

        Integer corePoolSize = updateThreadPoolConfigDTO.getCorePoolSize();
        Integer maximumPoolSize = updateThreadPoolConfigDTO.getMaximumPoolSize();
        // CorePoolSize 小于等于 MaximumPoolSize, 否则发出告警
        if (maximumPoolSize < corePoolSize) {
            // TODO 告警
            log.error("动态线程池, 变更配置时出错(最大线程数小于核心线程数): {}", updateThreadPoolConfigDTO);
            return false;
        }

        // 变更时注意设置值的顺序, 始终满足CorePoolSize 小于等于 MaximumPoolSize
        if (corePoolSize < threadPoolExecutor.getMaximumPoolSize()) {
            threadPoolExecutor.setCorePoolSize(updateThreadPoolConfigDTO.getCorePoolSize());
            threadPoolExecutor.setMaximumPoolSize(updateThreadPoolConfigDTO.getMaximumPoolSize());
        } else {
            threadPoolExecutor.setMaximumPoolSize(updateThreadPoolConfigDTO.getMaximumPoolSize());
            threadPoolExecutor.setCorePoolSize(updateThreadPoolConfigDTO.getCorePoolSize());
        }

        return true;
    }
}