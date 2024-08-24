package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.registry.redis;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.dto.AlarmMessageDTO;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.dto.UpdateThreadPoolConfigDTO;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.entity.ThreadPoolConfigEntity;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.enums.RegistryEnum;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.registry.IRegistry;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.service.IAlarmService;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.service.impl.AlarmServiceImpl;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.utils.RedisUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Redis 实现注册中心
 */

@Slf4j
@AllArgsConstructor
public class RedisRegistry implements IRegistry {

    private RedissonClient redissonClient;

    private IAlarmService alarmService;

    @Override
    public void reportThreadPool(List<ThreadPoolConfigEntity> threadPoolConfigEntityList) {
        if (threadPoolConfigEntityList == null || threadPoolConfigEntityList.isEmpty()) {
            return;
        }

        if (AlarmServiceImpl.canSendForThreadPoolDanger()) {
            alarmService.sendIfThreadPoolHasDanger(threadPoolConfigEntityList);
        }


        RList<ThreadPoolConfigEntity> list = RedisUtils.getPoolConfigRList(redissonClient);
        if (list.isEmpty()) {
            list.addAll(threadPoolConfigEntityList);
            return;
        }

        RLock lock = redissonClient.getLock(RegistryEnum.REPORT_THREAD_POOL_CONFIG_LIST_REDIS_LOCK_KEY.getKey());

        try {
            boolean canHasLock = lock.tryLock(3000, 3000, TimeUnit.MILLISECONDS);

            if (canHasLock) {
                reportThreadPoolRealProcess(threadPoolConfigEntityList, list);
            }
        } catch (Exception e) {
            alarmService.send(
                    AlarmMessageDTO
                            .buildAlarmMessageDTO("上报线程池列表出错")
                            .appendParameter("错误原因", e.toString())
            );
            log.error("动态线程池, 上报线程池列表时出现错误: {}", e.toString());
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void reportUpdateThreadPoolConfigParameter(UpdateThreadPoolConfigDTO updateThreadPoolConfigDTO) {
        RedisUtils.setUpdateThreadPoolConfigDTO(
                redissonClient,
                updateThreadPoolConfigDTO
        );

    }

    private void reportThreadPoolRealProcess(
            List<ThreadPoolConfigEntity> threadPoolConfigEntityList,
            RList<ThreadPoolConfigEntity> list ) {
        int index = 0;
        String applicationName = threadPoolConfigEntityList.get(0).getApplicationName();

        int listSize = list.size();
        // 获取本应用线程池的开始索引
        for (int i = 0; i < listSize; i++) {
            ThreadPoolConfigEntity originalPoolConfig = list.get(i);
            // 当前应用位置
            if (Objects.equals(originalPoolConfig.getApplicationName(), applicationName)) {
                index = i;
                break;
            }
        }

        // 更新线程池
        for (int i = 0; i < threadPoolConfigEntityList.size(); i++, index++) {
            ThreadPoolConfigEntity newPoolConfig = threadPoolConfigEntityList.get(i);
            // list已遍历完
            if (index >= listSize) {
                list.add(newPoolConfig);
                continue;
            }

            ThreadPoolConfigEntity originalPoolConfig = list.get(index);
            // list已遍历到下一个应用
            if (!Objects.equals(originalPoolConfig.getApplicationName(), applicationName)) {
                list.add(index + 1, newPoolConfig);
                continue;
            }

            // 当前应用某线程池发生了修改
            if (!Objects.equals(originalPoolConfig.toString(), newPoolConfig.toString())) {
                list.fastRemove(index);
                list.add(index, newPoolConfig);
            }
        }

        // 线程池已不存在，但缓存还在, 从缓存中删除
        while (index < listSize) {
            ThreadPoolConfigEntity originalPoolConfig = list.get(index);
            // 遍历到下一个应用
            if (!Objects.equals(originalPoolConfig.getApplicationName(), applicationName)) {
                break;
            }
            list.fastRemove(index);
            index++;
        }
    }
}
