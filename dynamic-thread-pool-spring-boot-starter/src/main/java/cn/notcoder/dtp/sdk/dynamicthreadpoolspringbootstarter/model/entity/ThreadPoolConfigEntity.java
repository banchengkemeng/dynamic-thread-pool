package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置对象
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreadPoolConfigEntity {

    /** 应用名称 */
    private String applicationName;
    /** 线程池名称 */
    private String threadPoolName;
    /** 核心线程数 */
    private Integer corePoolSize;
    /** 最大线程数 */
    private Integer maximumPoolSize;
    /** 当前活跃线程数 */
    private Integer activeThreadCount;
    /** 池中线程数 */
    private int poolSize;
    /** 队列类型 */
    private String queueType;
    /** 队列中任务数 */
    private Integer queueSize;
    /** 队列剩余的任务数 */
    private Integer remainingCapacity;

    /**
     * 建造一个线程池配置对象
     * @param applicationName 应用名
     * @param threadPoolName 线程池名
     * @param executor 线程池执行器对象
     * @return 线程池配置对象
     */
    public static ThreadPoolConfigEntity buildThreadPoolConfigEntity(
            String applicationName,
            String threadPoolName,
            ThreadPoolExecutor executor) {

        return new ThreadPoolConfigEntity(
                applicationName,
                threadPoolName,
                executor.getCorePoolSize(),
                executor.getMaximumPoolSize(),
                executor.getActiveCount(),
                executor.getPoolSize(),
                executor.getQueue().getClass().getSimpleName(),
                executor.getQueue().size(),
                executor.getQueue().remainingCapacity()
        );
    }
}
