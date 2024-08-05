package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class LoginVO implements Serializable {
    private String authToken;
}
