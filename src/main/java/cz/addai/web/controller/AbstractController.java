package cz.addai.web.controller;

import cz.addai.web.model.request.AbstractRequest;
import cz.addai.web.model.response.AbstractResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractController {

    private Logger logger = LoggerFactory.getLogger(AbstractController.class);

    public static final String WATSON_TAG = "Watson";
    public static final String CLIENT_TAG = "Client app";

    public Logger getLogger() {
        return logger;
    }

    protected void beforeProcess(AbstractRequest abstractRequest) {

    }

    protected void afterProcess(AbstractRequest abstractRequest, AbstractResponse abstractResponse) {

    }

//    protected void populateRequestContextFromRequest(AbstractRequest request) {
//        requestContext.setRequestClientInfo(request.getClientInfo());
//        requestContext.setRequestSessionInfo(request.getSessionInfo());
//    }

}
