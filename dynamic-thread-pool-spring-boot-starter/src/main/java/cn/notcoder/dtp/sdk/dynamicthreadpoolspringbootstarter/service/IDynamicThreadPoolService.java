package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.service;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.dto.UpdateThreadPoolConfigDTO;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.entity.ThreadPoolConfigEntity;

import java.util.List;

/**
 * 动态线程池服务接口
 */
public interface IDynamicThreadPoolService {

    /**
     * 查询所有线程池
     * @return 线程池列表
     */
    List<ThreadPoolConfigEntity> queryThreadPoolList();

    /**
     * 根据线程池名称查询线程池
     * @param threadPoolName 线程池名称
     * @return 线程池
     */
    ThreadPoolConfigEntity queryThreadPoolByName(String threadPoolName);

    /**
     * 更新线程池配置
     * @param updateThreadPoolConfigDTO 更新线程池传输对象
     * @return 是否更新成功
     */
    Boolean updateThreadPoolConfig(UpdateThreadPoolConfigDTO updateThreadPoolConfigDTO);

}
