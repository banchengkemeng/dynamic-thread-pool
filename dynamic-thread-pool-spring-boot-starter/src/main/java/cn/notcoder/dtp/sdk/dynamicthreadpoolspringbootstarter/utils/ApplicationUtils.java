package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.utils;

import org.springframework.context.ApplicationContext;

public class ApplicationUtils {
    public static String getApplicationName(ApplicationContext applicationContext) {
        return applicationContext.getEnvironment().getProperty("spring.application.name");
    }
}
