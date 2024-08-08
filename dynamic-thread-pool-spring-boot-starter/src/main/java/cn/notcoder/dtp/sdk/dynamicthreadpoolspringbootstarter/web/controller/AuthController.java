package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.controller;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config.properties.DynamicThreadPoolWebAutoProperties;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.exception.BusinessException;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.model.dto.LoginDTO;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.model.enums.ResponseEnum;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.model.vo.CheckAuthVO;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.model.vo.LoginVO;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.model.vo.ResponseVO;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.utils.AuthUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@RequestMapping(value = "${dynamic-thread-pool.web.context-path}/auth")
@CrossOrigin(allowCredentials = "true", originPatterns = {"http://localhost*", "http://127.0.0.1*"})
public class AuthController {
    @Resource
    private DynamicThreadPoolWebAutoProperties dynamicThreadPoolWebAutoProperties;

    @PostMapping("/login")
    public ResponseVO<LoginVO> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        DynamicThreadPoolWebAutoProperties.Auth authConfig = dynamicThreadPoolWebAutoProperties.getAuth();

        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        if (!Objects.equals(username, authConfig.getUsername()) ||
                !Objects.equals(password, authConfig.getPassword())) {
            throw new BusinessException(ResponseEnum.FAILED, "用户名或密码错误");
        }

        String token = AuthUtils.generateToken();
        request.getSession().setAttribute(AuthUtils.SESSION_AUTH_KEY, token);
        return ResponseVO.success(new LoginVO(token));
    }

    @GetMapping("/check")
    public ResponseVO<CheckAuthVO> checkAuth(HttpServletRequest request) {
        return ResponseVO.success(new CheckAuthVO(AuthUtils.hashAuth(request)));
    }
}
