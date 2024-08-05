package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.utils;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.dto.UpdateThreadPoolConfigDTO;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.entity.ThreadPoolConfigEntity;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.enums.RegistryEnum;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;

import java.time.Duration;
import java.util.Objects;

/**
 * Redis操作工具类
 */
public class RedisUtils {
    public static UpdateThreadPoolConfigDTO getUpdateThreadPoolConfigDTO(
            RedissonClient redissonClient,
            String applicationName,
            String poolName ) {
        return getUpdateThreadPoolConfigDTOBucket(
                redissonClient,
                applicationName,
                poolName
        ).get();
    }

    public static void setUpdateThreadPoolConfigDTO(
            RedissonClient redissonClient,
            UpdateThreadPoolConfigDTO updateThreadPoolConfigDTO ) {
        RBucket<UpdateThreadPoolConfigDTO> updateThreadPoolConfigDTOBucket = getUpdateThreadPoolConfigDTOBucket(
                redissonClient,
                updateThreadPoolConfigDTO.getApplicationName(),
                updateThreadPoolConfigDTO.getThreadPoolName()
        );

        updateThreadPoolConfigDTOBucket.set(
                updateThreadPoolConfigDTO,
                Duration.ofDays(30)
        );
    }

    public static RList<ThreadPoolConfigEntity> getPoolConfigRList(RedissonClient redissonClient) {
        return redissonClient.getList(RegistryEnum.THREAD_POOL_CONFIG_LIST_KEY.getKey());
    }

    public static ThreadPoolConfigEntity getPoolConfigByPoolName(RedissonClient redissonClient, String poolName) {
        RList<ThreadPoolConfigEntity> threadPoolConfigEntities = redissonClient.getList(
                RegistryEnum.THREAD_POOL_CONFIG_LIST_KEY.getKey()
        );
        for (ThreadPoolConfigEntity threadPoolConfigEntity : threadPoolConfigEntities) {
            if (Objects.equals(threadPoolConfigEntity.getThreadPoolName(), poolName)) {
                return threadPoolConfigEntity;
            }
        }
        return null;
    }

    private static RBucket<UpdateThreadPoolConfigDTO> getUpdateThreadPoolConfigDTOBucket(
            RedissonClient redissonClient,
            String applicationName,
            String poolName) {
        return redissonClient.getBucket(
                buildRedisKey(
                        applicationName,
                        poolName
                )
        );
    }

    private static String buildRedisKey(String applicationName, String poolName) {
        return String.format(
                "%s_%s_%s",
                RegistryEnum.THREAD_POOL_CONFIG_PARAMETER_LIST_KEY.getKey(),
                applicationName,
                poolName
        );
    }
}
