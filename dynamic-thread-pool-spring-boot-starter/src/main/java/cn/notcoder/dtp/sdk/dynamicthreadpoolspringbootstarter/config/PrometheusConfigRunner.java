package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.entity.ThreadPoolConfigEntity;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.hook.ResizableCapacityLinkedBlockingQueue;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.utils.ApplicationUtils;
import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.config.MeterFilterReply;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.actuate.autoconfigure.metrics.LogbackMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

@NoArgsConstructor
@AllArgsConstructor
public class PrometheusConfigRunner implements ApplicationRunner {

    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String[] beanNamesForType = applicationContext.getBeanNamesForType(ThreadPoolExecutor.class);
        for (String beanName : beanNamesForType) {
            ThreadPoolExecutor executor = (ThreadPoolExecutor) applicationContext.getBean(beanName);
            registerThreadPool(
                    ApplicationUtils.getApplicationName(applicationContext),
                    beanName,
                    executor
            );
        }
    }

    private void registerThreadPool(
            String applicationName,
            String poolName,
            ThreadPoolExecutor executor
    ) {
        List<Tag> tags = Arrays.asList(
                new ImmutableTag("applicationName", applicationName),
                new ImmutableTag("poolName", poolName)
        );
        Metrics.gauge("thread_pool_core_size", tags, executor, ThreadPoolExecutor::getCorePoolSize);
        Metrics.gauge("thread_pool_max_size", tags, executor, ThreadPoolExecutor::getMaximumPoolSize);
        Metrics.gauge("thread_pool_active_thread_count", tags, executor, ThreadPoolExecutor::getActiveCount);
        Metrics.gauge("thread_pool_size", tags, executor, ThreadPoolExecutor::getPoolSize);
        Metrics.gauge("thread_pool_queue_size", tags, executor,
                (threadPoolExecutor) -> threadPoolExecutor.getQueue().size()
        );
        Metrics.gauge("thread_pool_queue_remaining_capacity", tags, executor,
                (threadPoolExecutor) -> threadPoolExecutor.getQueue().remainingCapacity()
        );
    }
}
