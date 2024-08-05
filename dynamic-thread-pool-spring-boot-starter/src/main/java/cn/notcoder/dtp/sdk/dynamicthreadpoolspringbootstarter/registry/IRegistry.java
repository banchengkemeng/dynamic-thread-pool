package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.registry;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.dto.UpdateThreadPoolConfigDTO;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.entity.ThreadPoolConfigEntity;

import java.util.List;

/**
 * 注册中心接口
 */

public interface IRegistry {

    /**
     * 上报全部线程池配置信息
     * @param threadPoolConfigEntityList 线程池列表
     */
    void reportThreadPool(List<ThreadPoolConfigEntity> threadPoolConfigEntityList);


    /**
     * 上报更新线程池配置参数
     * @param updateThreadPoolConfigDTO 更新线程池配置传输对象
     */
    void reportUpdateThreadPoolConfigParameter(UpdateThreadPoolConfigDTO updateThreadPoolConfigDTO);
}
