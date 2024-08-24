package cn.notcoder.dtp.sdk.dynamicthreadpooltest;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config.properties.DynamicThreadPoolAlarmAutoProperties;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.dto.AlarmMessageDTO;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.dto.UpdateThreadPoolConfigDTO;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.service.IAlarmService;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.service.IDynamicThreadPoolService;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.strategy.alarm.AbstractAlarmStrategy;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RTopic;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
class DynamicThreadPoolTestApplicationTests {

    @Resource
    private ThreadPoolExecutor threadPoolExecutor01;

    @Resource
    private IDynamicThreadPoolService dynamicThreadPoolService;

    @Resource
    private RTopic dynamicThreadPoolAdjustRedisTopic;

    @Resource
    private DynamicThreadPoolAlarmAutoProperties dynamicThreadPoolAlarmAutoProperties;

    @Resource
    private IAlarmService alarmService;

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
            } catch (Exception e) {
            }
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
                                i + 10,
                                50
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
                                i + 10,
                                50
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
                                i + 10,
                                50
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
                                i + 10,
                                50
                        )
                );
            }
        });


    }

    @Test
    void testDingAlarm() throws ApiException {
        final String CUSTOM_ROBOT_TOKEN = dynamicThreadPoolAlarmAutoProperties.getAccessToken().getDingDing();

        //sign字段和timestamp字段必须拼接到请求URL上，否则会出现 310000 的错误信息
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send");
        OapiRobotSendRequest req = new OapiRobotSendRequest();
        /**
         * 发送文本消息
         */
        //定义文本内容
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent("【监控告警】 hello\n 123456");

        //定义 @ 对象
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setIsAtAll(true);

        //设置消息类型
        req.setMsgtype("text");
        req.setText(text);
        req.setAt(at);
        OapiRobotSendResponse rsp = client.execute(req, CUSTOM_ROBOT_TOKEN);

        System.out.println(rsp.getBody());
    }

    @Test
    void testAlarmStrategy() {

        List<AbstractAlarmStrategy> alarmStrategies = AbstractAlarmStrategy.selectStrategy(
                dynamicThreadPoolAlarmAutoProperties.getUsePlatform()
        );
        AlarmMessageDTO alarmMessageDTO = AlarmMessageDTO.buildAlarmMessageDTO("线程池状态异常")
                .appendParameter("测试", 1);

        alarmStrategies.forEach(strategy -> {
            try {
                strategy.send(alarmMessageDTO);
            } catch (ApiException e) {
                log.error("告警推送失败: {} | {}", e.getErrCode(), e.getErrMsg());
            }

        });
    }

    @Test
    void testAlarmService() {
        alarmService.send(
                AlarmMessageDTO
                        .buildAlarmMessageDTO("线程池状态异常")
                        .appendParameter("测试", 1)
        );
    }
}
