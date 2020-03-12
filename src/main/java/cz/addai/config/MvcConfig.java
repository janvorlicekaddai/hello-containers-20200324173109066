package cz.addai.config;

import cz.addai.aspect.RequestResponseAspect;
import cz.addai.web.interceptor.IpAddressInterceptor;
import cz.addai.web.interceptor.LoggingInterceptor;
import cz.addai.web.interceptor.CollectRequestDataInterceptor;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
@ComponentScan(basePackages = {"cz.addai.web"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableWebMvc
@Import(SwaggerConfig.class)
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private LoggingInterceptor loggingInterceptor;

    @Resource
    private IpAddressInterceptor ipAddressInterceptor;

    @Resource
    private CollectRequestDataInterceptor collectRequestDataInterceptor;

    /**
     * Std request / response aspect
     */
    @Bean
    public RequestResponseAspect reqAspect() {
        var aspect = new RequestResponseAspect();
        aspect.addInterceptor(collectRequestDataInterceptor);
        return aspect;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/index.html").addResourceLocations("classpath:/web/index.html");

        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Log low-level request / response
        registry.addInterceptor(loggingInterceptor)
                .addPathPatterns("/**");
        // Add IP address to MDC
        registry.addInterceptor(ipAddressInterceptor)
                .addPathPatterns("/**");
    }
}
