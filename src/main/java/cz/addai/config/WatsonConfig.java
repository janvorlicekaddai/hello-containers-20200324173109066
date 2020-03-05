package cz.addai.config;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v2.Assistant;
import cz.addai.components.AdamConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ibm.watson.discovery.v2.Discovery;

import javax.annotation.Resource;

@Configuration
public class WatsonConfig {

    private static final String DISCOVERY_VERSION_NAME = "2019-04-30";
    private static final String ASSISTANT_VERSION_NAME = "2019-02-28";

    @Resource
    private AdamConfig adamConfig;

    @Bean
    public Authenticator watsonAuthenticator() {
        return new IamAuthenticator(adamConfig.getWatsonApiKey());
    }

    @Bean
    public Discovery discoveryService(Authenticator authenticator) {
        Discovery service = new Discovery(DISCOVERY_VERSION_NAME, authenticator);
        service.setServiceUrl(adamConfig.getWatsonUrl());
        return service;
    }

    @Bean
    public Assistant assistantService(Authenticator authenticator) {
        Assistant service = new Assistant(ASSISTANT_VERSION_NAME, authenticator);
        service.setServiceUrl(adamConfig.getWatsonUrl());
        return service;
    }
}
