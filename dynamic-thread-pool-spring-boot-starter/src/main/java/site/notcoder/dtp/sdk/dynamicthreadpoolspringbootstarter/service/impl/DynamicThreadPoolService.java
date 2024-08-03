package site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.dto.UpdateThreadPoolConfigDTO;
import site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.entity.ThreadPoolConfigEntity;
import site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.service.IDynamicThreadPoolService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 动态线程池服务
 */

@NoArgsConstructor
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

        threadPoolExecutor.setCorePoolSize(updateThreadPoolConfigDTO.getCorePoolSize());
        threadPoolExecutor.setMaximumPoolSize(updateThreadPoolConfigDTO.getMaximumPoolSize());

        return true;
    }
}
