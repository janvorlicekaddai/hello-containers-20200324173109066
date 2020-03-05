package cz.addai.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = {"cz.addai.components", "cz.addai.service"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories
@EnableTransactionManagement
@Import({JpaConfig.class, WatsonConfig.class})
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource(value = "classpath:local.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "file:${adamproperties}", ignoreResourceNotFound = true)})
public class RootConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
