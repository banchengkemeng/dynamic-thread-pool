package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.trigger.listener;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.dto.UpdateThreadPoolConfigDTO;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.entity.ThreadPoolConfigEntity;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.registry.IRegistry;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.service.IDynamicThreadPoolService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.listener.MessageListener;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class ThreadPoolConfigAdjustListener implements MessageListener<UpdateThreadPoolConfigDTO> {

    private IDynamicThreadPoolService dynamicThreadPoolService;

    private IRegistry registry;

    @Override
    public void onMessage(CharSequence charSequence, UpdateThreadPoolConfigDTO updateThreadPoolConfigDTO) {
        Boolean success = dynamicThreadPoolService.updateThreadPoolConfig(updateThreadPoolConfigDTO);
        if (!success) {
            log.warn("动态线程池, 配置变更结果: {}, 配置参数: {}", success, updateThreadPoolConfigDTO);
        }
        log.info("动态线程池, 配置变更结果: {}, 配置参数: {}", success, updateThreadPoolConfigDTO);

        List<ThreadPoolConfigEntity> threadPoolConfigEntities = dynamicThreadPoolService.queryThreadPoolList();
        registry.reportThreadPool(threadPoolConfigEntities);
        log.info("动态线程池, 上报线程池信息: {}", threadPoolConfigEntities);

        UpdateThreadPoolConfigDTO updateThreadPoolConfigDTOCurrent = UpdateThreadPoolConfigDTO.buildUpdateThreadPoolConfigDTO(
                dynamicThreadPoolService.queryThreadPoolByName(
                        updateThreadPoolConfigDTO.getThreadPoolName()
                )
        );
        registry.reportUpdateThreadPoolConfigParameter(
                updateThreadPoolConfigDTO
        );
        log.info("动态线程池, 上报配置参数: {}", updateThreadPoolConfigDTOCurrent);
    }
}
