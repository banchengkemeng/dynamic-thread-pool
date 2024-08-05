package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {
    SUCCESS(200, "操作成功"),
    FAILED(400, "操作失败"),
    NO_AUTH(401, "无权限"),
    NOT_FOUNT(404, "未找到"),
    SYSTEM_ERROR(500, "系统错误");

    private final Integer status;
    private final String message;
}
