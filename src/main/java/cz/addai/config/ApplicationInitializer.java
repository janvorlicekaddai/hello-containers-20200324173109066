package cz.addai.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Arrays;

public class ApplicationInitializer implements WebApplicationInitializer {

    private Logger logger = LoggerFactory.getLogger(ApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext container) {

        logger.info("Initializing ADAM API application");

        var url = getClass().getClassLoader().getResource("/logback.xml");
        logger.info("URL: {}", url);

        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        ConfigurableEnvironment env = rootContext.getEnvironment();
        String adminProfile = env.getProperty("adam.profile");
        logger.debug("Value of property adam.profile: {}", adminProfile);
        env.setActiveProfiles(adminProfile != null ? adminProfile : AdamEnvironment.DEVELOPMENT.name());
        if (logger.isDebugEnabled()) {
            logger.debug("Set active profiles: {}", Arrays.toString(env.getActiveProfiles()));
        }

        rootContext.register(RootConfig.class);

        // Manage the lifecycle of the root application context
        container.addListener(new ContextLoaderListener(rootContext));

        // Create the dispatcher servlet's Spring application context
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(MvcConfig.class);

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        //Register jacksons jodatime object mapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
    }
}
