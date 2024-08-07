package cn.notcoder.dtp.sdk.dynamicthreadpooltest;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.dto.UpdateThreadPoolConfigDTO;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.service.IDynamicThreadPoolService;
import org.junit.jupiter.api.Test;
import org.redisson.api.RTopic;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class DynamicThreadPoolTestApplicationTests {

    @Resource
    private ThreadPoolExecutor threadPoolExecutor01;

    @Resource
    private IDynamicThreadPoolService dynamicThreadPoolService;

    @Resource
    private RTopic dynamicThreadPoolAdjustRedisTopic;

    @Test
    void contextLoads() {
    }

    @Test
    void testThreadPool() throws Exception {

        Thread t = new Thread(() -> {
            try {
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

//                    List<ThreadPoolConfigEntity> threadPoolConfigEntities = dynamicThreadPoolService.queryThreadPoolList();
//                    System.out.println(threadPoolConfigEntities);
//
//                    if (startDuration == 3) {
//                        dynamicThreadPoolService.updateThreadPoolConfig(
//                                new UpdateThreadPoolConfigDTO(
//                                        "dynamic-thread-pool-test",
//                                        "threadPoolExecutor01",
//                                        88,
//                                        666
//                                )
//                        );
//                    }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    Thread.sleep((random.nextInt(10) + 1) * 1000);
                }
            } catch (Exception e) {}
        });

        t.start();
    }

    @Test
    void testDynamicThreadPoolRedisTopic() {

        threadPoolExecutor01.execute(() -> {
            for (int i = 10; i < 20; i++) {
                dynamicThreadPoolAdjustRedisTopic.publish(
                        new UpdateThreadPoolConfigDTO(
                                "dynamic-thread-pool-1",
                                "threadPoolExecutor01",
                                i,
                                i + 10
                        )
                );
            }
        });

        threadPoolExecutor01.execute(() -> {
            for (int i = 20; i < 30; i++) {
                dynamicThreadPoolAdjustRedisTopic.publish(
                        new UpdateThreadPoolConfigDTO(
                                "dynamic-thread-pool-1",
                                "threadPoolExecutor02",
                                i,
                                i + 10
                        )
                );
            }
        });

        threadPoolExecutor01.execute(() -> {
            for (int i = 50; i < 60; i++) {
                dynamicThreadPoolAdjustRedisTopic.publish(
                        new UpdateThreadPoolConfigDTO(
                                "dynamic-thread-pool-2",
                                "threadPoolExecutor01",
                                i,
                                i + 10
                        )
                );
            }
        });

        threadPoolExecutor01.execute(() -> {
            for (int i = 60; i < 70; i++) {
                dynamicThreadPoolAdjustRedisTopic.publish(
                        new UpdateThreadPoolConfigDTO(
                                "dynamic-thread-pool-2",
                                "threadPoolExecutor02",
                                i,
                                i + 10
                        )
                );
            }
        });


    }

}
