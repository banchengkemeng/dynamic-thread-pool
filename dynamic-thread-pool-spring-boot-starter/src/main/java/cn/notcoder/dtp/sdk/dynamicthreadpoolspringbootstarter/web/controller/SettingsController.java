package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.controller;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config.properties.DynamicThreadPoolWebAutoProperties;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.model.vo.ResponseVO;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.model.vo.SettingsVO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin(allowCredentials = "true", originPatterns = {"http://localhost*", "http://127.0.0.1*"})
public class SettingsController {

    @Resource
    private DynamicThreadPoolWebAutoProperties webAutoProperties;

    @GetMapping("/settingsLZQGJPUFNS")
    public ResponseVO<SettingsVO> settings() {
        return ResponseVO.success(new SettingsVO(
                webAutoProperties.getContextPath(),
                webAutoProperties.getGrafanaDashboardUrl()
        ));
    }
}
