package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.config;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.config.properties.DynamicThreadPoolWebAutoProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class FrontendConfig implements WebMvcConfigurer {

    private DynamicThreadPoolWebAutoProperties config;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler(config.getContextPath() + "/**")
                .addResourceLocations("classpath:/web/");
        registry
                .addResourceHandler("/f8e5c81e18c8522/**")
                .addResourceLocations("classpath:/web/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController(
                config.getContextPath(),
                config.getContextPath() + "/index.html"
        );
    }
}
