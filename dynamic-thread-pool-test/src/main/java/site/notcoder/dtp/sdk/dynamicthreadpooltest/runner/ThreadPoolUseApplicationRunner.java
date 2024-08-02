package site.notcoder.dtp.sdk.dynamicthreadpooltest.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.dto.UpdateThreadPoolConfigDTO;
import site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.entity.ThreadPoolConfigEntity;
import site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.service.DynamicThreadPoolService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ThreadPoolUseApplicationRunner implements ApplicationRunner {

    @Resource
    private ThreadPoolExecutor threadPoolExecutor01;

    @Resource
    private DynamicThreadPoolService dynamicThreadPoolService;

    @Override
    public void run(ApplicationArguments args) throws InterruptedException {
        while (true) {
            Random random = new Random();
            int startDuration = random.nextInt(5) + 1;
            int runDuration = random.nextInt(10) + 1;

            threadPoolExecutor01.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(startDuration);
                    System.out.printf("启动花费时间: %ds\n", startDuration);

                    TimeUnit.SECONDS.sleep(runDuration);
                    System.out.printf("运行花费时间: %ds\n", runDuration);

                    List<ThreadPoolConfigEntity> threadPoolConfigEntities = dynamicThreadPoolService.queryThreadPoolList();
                    System.out.println(threadPoolConfigEntities);

                    if (startDuration == 3) {
                        dynamicThreadPoolService.updateThreadPoolConfig(
                                new UpdateThreadPoolConfigDTO(
                                        "threadPoolExecutor01",
                                        88,
                                        666
                                )
                        );
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            Thread.sleep((random.nextInt(10) + 1) * 1000);
        }
    }
}
