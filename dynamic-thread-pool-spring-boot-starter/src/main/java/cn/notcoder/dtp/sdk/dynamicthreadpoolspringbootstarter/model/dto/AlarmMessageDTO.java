package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmMessageDTO {
    private String message;
    private LinkedHashMap<String, String> parameters;
    private String remarks;

    public static AlarmMessageDTO buildAlarmMessageDTO(
            String message,
            LinkedHashMap<String, String > parameters,
            String remarks
    ) {
        return new AlarmMessageDTO(
                message,
                parameters,
                remarks
        );
    }

    public static AlarmMessageDTO buildAlarmMessageDTO(
            String message,
            String remarks
    ) {
        return AlarmMessageDTO.buildAlarmMessageDTO(
                message,
                new LinkedHashMap<>(),
                remarks
        );
    }

    public static AlarmMessageDTO buildAlarmMessageDTO(
            String message
    ) {
        return AlarmMessageDTO.buildAlarmMessageDTO(
                message,
                new LinkedHashMap<>(),
                null
        );
    }

    public <T> AlarmMessageDTO appendParameter(String k, T v) {
        parameters.put(
                k,
                v.toString()
        );
        return this;
    }
}
