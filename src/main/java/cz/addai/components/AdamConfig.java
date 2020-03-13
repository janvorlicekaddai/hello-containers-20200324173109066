package cz.addai.components;

import cz.addai.config.AdamEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import javax.annotation.Resource;

@Component
public class AdamConfig implements InitializingBean {

    // Note: exists since Spring 4.3
    public static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(
                    MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    StandardCharsets.UTF_8);

    private Logger logger = LoggerFactory.getLogger(AdamConfig.class);

    @Resource
    private Environment env;

    @Resource
    private ResourceLoader resourceLoader;

    private AdamEnvironment adamEnvironment;

    ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO: find better solution - manage more profiles then one
        String firstActiveProfile = env.getActiveProfiles()[0];
        try {
            adamEnvironment = AdamEnvironment.valueOf(firstActiveProfile);
        } catch (Exception e) {
            logger.error("Unknown ADAM environment: {}", firstActiveProfile);
            throw new IllegalArgumentException("Cannot set AdamEnvironment from spring.profiles.active", e);
        }
    }

    /**
     * Get generic property
     */
    public String getProperty(String name) {
        return env.getProperty(name);
    }

    /**
     * Get ADAM environment
     */
    public AdamEnvironment getAdamEnvironment() {
        return adamEnvironment;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // JPA

    public String getHibernateDialect() {
        return getProperty("app.datasource.dialect");
    }

    public String getJdbcDriver() {
        return getProperty("app.datasource.driver");
    }

    public String getJdbcUrl() {
        return getProperty("app.datasource.jdbc-url");
    }

    public String getDbUserName() {
        return getProperty("app.datasource.username");
    }

    public String getDbPassword() {
        return getProperty("app.datasource.password");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // REST client

    public int getConnectionRequestTimeoutSeconds() {
        return Integer.parseInt(getProperty("be.connectionRequestTimeout"));
    }

    public int getConnectTimeoutSeconds() {
        return Integer.parseInt(getProperty("be.connectionTimeout"));
    }

    public int getSocketTimeoutSeconds() {
        return Integer.parseInt(getProperty("be.socketTimeout"));
    }

    public int getPoolMax() {
        return Integer.parseInt(getProperty("be.pool.max"));
    }

    public int getPoolMaxPerRoute() {
        return Integer.parseInt(getProperty("be.pool.maxPerRoute"));
    }

    public int getRestConnectionRequestTimeoutSeconds() {
        return Integer.parseInt(getProperty("be.rest.connectionRequestTimeout"));
    }

    public int getRestConnectTimeoutSeconds() {
        return Integer.parseInt(getProperty("be.rest.connectionTimeout"));
    }

    public int getRestSocketTimeoutSeconds() {
        return Integer.parseInt(getProperty("be.rest.socketTimeout"));
    }

    public int getRestPoolMax() {
        return Integer.parseInt(getProperty("be.rest.pool.max"));
    }

    public int getRestPoolMaxPerRoute() {
        return Integer.parseInt(getProperty("be.rest.pool.maxPerRoute"));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Watson

//    public String getWatsonUrl() {
//        return getProperty("watson.url");
//    }
//
//    public String getWatsonAssistantId() {
//        return getProperty("watson.assistantId");
//    }
//
//    public String getWatsonApiKey() {
//        return getProperty("watson.apiKey");
//    }
}
