package cz.addai.web.interceptor;

import cz.addai.aspect.IRequestResponseInterceptor;
import cz.addai.components.session.UserSession;
import cz.addai.persistence.domain.Client;
import cz.addai.service.ClientService;
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
    private UserSession userSession;

    @Resource
    private ClientService clientService;

    @Override
    public void beforeHandler(AbstractRequest request) {
        // Request ID
        String requestId = request.getRequestId();
        MDC.put(MDC_REQUEST_ID, requestId);
        userSession.setRequestId(request.getRequestId());

        // Client ID
        String clientId = request.getClientId();
        userSession.setClientId(clientId);

        // Client
        Client client = clientService.getClient(clientId);
        if (client == null) {
            throw new EInvalidClientIdException(clientId);
        }
        userSession.setClient(client);

        logSessionData();
    }

    @Override
    public void afterHandler(AbstractRequest request, AbstractResponse response) {
        MDC.remove(MDC_REQUEST_ID);
    }

    private void logSessionData() {
        logger.debug("Session parameters:");
        logger.debug("   clientId: " + userSession.getClientId());
        if (userSession.getWatsonData() != null) {
            logger.debug("   watsonSessionToken: " + userSession.getWatsonData().getWatsonSessionToken());
        }
    }
}
