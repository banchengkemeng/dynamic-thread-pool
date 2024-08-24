package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.strategy.alarm;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config.properties.DynamicThreadPoolAlarmAutoProperties;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.dto.AlarmMessageDTO;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.enums.AlarmStrategyEnum;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class DingDingAlarmStrategy extends AbstractAlarmStrategy{

    @Resource
    private DynamicThreadPoolAlarmAutoProperties config;

    @Override
    public String getStrategyName() {
        return AlarmStrategyEnum.DING_DING.getValue();
    }

    @Override
    public void send(AlarmMessageDTO message) throws ApiException {

        String token = config.getAccessToken().getDingDing();

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send");
        OapiRobotSendRequest req = new OapiRobotSendRequest();

        //定义文本内容
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(buildMessageContent(message));

        //定义 @ 对象
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setIsAtAll(true);

        //设置消息类型
        req.setMsgtype("text");
        req.setText(text);
        req.setAt(at);
        OapiRobotSendResponse rsp = client.execute(req, token);

        if (rsp.isSuccess()) {
            return;
        }

        throw new ApiException(rsp.getErrcode().toString(), rsp.getErrmsg());
    }
}
