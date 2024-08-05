package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.utils;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.model.vo.CheckAuthVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Random;

public class AuthUtils {

    public static String SESSION_AUTH_KEY = "auth";
    public static String AUTH_HEADER = "Auth-Token";

    public static String generateToken() {
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            token.append((char) (new Random(
                    new Random().nextInt(100) * new Random().nextInt(100)
            ).nextInt('Z' - 'A' + 1) + 'A'));
        }
        return token.toString();
    }

    public static Boolean hashAuth(HttpServletRequest request) {
        return Objects.equals(
                request.getSession().getAttribute(SESSION_AUTH_KEY),
                request.getHeader(AUTH_HEADER)
        );
    }
}
