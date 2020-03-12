package cz.addai.web.interceptor;

import cz.addai.service.WatsonSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoggingInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(WatsonSessionService.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.debug("Received request: [" + request.getMethod() + "]" + request.getRequestURI());

        var headerNames = request.getHeaderNames();
        logger.debug("HTTP headers:");
        while(headerNames.hasMoreElements()) {
            var name = headerNames.nextElement();
            var value = request.getHeader(name);
            logger.debug("   " + name + " : " + value);
        }
       return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex != null){
            logger.error("The request ended with exception: ", ex);
            return;
        }
        logger.debug("Request processing finished. HTTP STATUS: " + response.getStatus());
        logger.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

}
