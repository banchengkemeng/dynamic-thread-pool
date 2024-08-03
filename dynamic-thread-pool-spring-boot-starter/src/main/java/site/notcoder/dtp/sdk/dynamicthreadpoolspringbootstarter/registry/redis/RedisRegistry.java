package site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.registry.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.dto.UpdateThreadPoolConfigDTO;
import site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.entity.ThreadPoolConfigEntity;
import site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.registry.IRegistry;

import javax.annotation.Resource;
import java.util.List;

/**
 * Redis 实现注册中心
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisRegistry implements IRegistry {

    private RedissonClient redissonClient;

    @Override
    public void reportThreadPool(List<ThreadPoolConfigEntity> threadPoolConfigEntityList) {

    }

    @Override
    public void reportUpdateThreadPoolConfigParameter(UpdateThreadPoolConfigDTO updateThreadPoolConfigDTO) {

    }
}
