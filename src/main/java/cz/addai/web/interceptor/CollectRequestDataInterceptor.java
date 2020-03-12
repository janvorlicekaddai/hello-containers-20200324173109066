package cz.addai.web.interceptor;

import cz.addai.aspect.IRequestResponseInterceptor;
import cz.addai.aspect.RequestResponseAspect;
import cz.addai.components.UserSession;
import cz.addai.web.model.request.AbstractRequest;
import cz.addai.web.model.response.AbstractResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CollectRequestDataInterceptor implements IRequestResponseInterceptor {

    private static final String MDC_REQUEST_ID = "requestId";

    private Logger logger = LoggerFactory.getLogger(CollectRequestDataInterceptor.class);

    @Resource
    protected UserSession userSession;

    @Override
    public void beforeHandler(AbstractRequest request) {
        // Request ID
        String requestId = request.getRequestId();
        MDC.put(MDC_REQUEST_ID, requestId);
        userSession.setRequestId(request.getRequestId());

        // Client ID
        String clientId = request.getClientId();
        userSession.setClientId(clientId);

        logSessionData();
    }

    @Override
    public void afterHandler(AbstractRequest request, AbstractResponse response) {
        MDC.remove(MDC_REQUEST_ID);
    }

    private void logSessionData() {
        logger.debug("Session parameters:");
        logger.debug("   clientId: " + userSession.getClientId());
        logger.debug("   watsonSessionToken: " + userSession.getWatsonSessionToken());
    }
}
