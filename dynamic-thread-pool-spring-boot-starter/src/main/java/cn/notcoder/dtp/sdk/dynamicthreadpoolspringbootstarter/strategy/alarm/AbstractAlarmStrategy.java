package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.strategy.alarm;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.dto.AlarmMessageDTO;
import com.taobao.api.ApiException;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Data
@Component
public abstract class AbstractAlarmStrategy implements IAlarmStrategy {

    private static HashMap<String, AbstractAlarmStrategy> strategiesMap = new HashMap<>();

    public abstract String getStrategyName();

    @PostConstruct
    private void initStrategy() {
        strategiesMap.put(getStrategyName(), this);
    }

    public static List<AbstractAlarmStrategy> selectStrategy(List<String > strategies) {
        ArrayList<AbstractAlarmStrategy> abstractAlarmStrategies = new ArrayList<>();
        strategies.forEach(strategy -> {
            abstractAlarmStrategies.add(strategiesMap.get(strategy));
        });
        return abstractAlarmStrategies;
    }

    public static List<AbstractAlarmStrategy> selectStrategy(String strategies) {
        return selectStrategy(Collections.singletonList(strategies));
    }

    @Override
    public abstract void send(AlarmMessageDTO message) throws ApiException;

    protected String buildMessageContent(AlarmMessageDTO message) {
        StringBuilder content = new StringBuilder();
        HashMap<String, String> parameters = message.getParameters();
        String remarks = message.getRemarks();

        content.append("【监控告警】 ").append(message.getMessage()).append("\n");
        parameters.forEach((k, v) -> {
            content.append(" ").append(k).append(": ").append(v).append("\n");
        });
        if (remarks != null) {
            content.append(" ").append(remarks).append("\n");
        }
        return content.toString();
    }
}
