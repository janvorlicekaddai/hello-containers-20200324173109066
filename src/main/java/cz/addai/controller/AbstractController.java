package cz.addai.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class AbstractController {

    private Logger logger = LoggerFactory.getLogger(AbstractController.class);

    public static final String WATSON_TAG = "Watson";
    public static final String CLIENT_TAG = "Client app";

    public Logger getLogger() {
        return logger;
    }

//    protected void populateRequestContextFromRequest(AbstractRequest request) {
//        requestContext.setRequestClientInfo(request.getClientInfo());
//        requestContext.setRequestSessionInfo(request.getSessionInfo());
//    }
}
