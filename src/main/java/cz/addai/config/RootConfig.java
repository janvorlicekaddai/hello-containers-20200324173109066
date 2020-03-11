package cz.addai.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
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
        return JsonMapper.builder()
                .addModule(new ParameterNamesModule())
                .addModule(new Jdk8Module())
                .addModule(new JavaTimeModule())
                .build();
    }
}
