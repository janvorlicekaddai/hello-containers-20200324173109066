package cz.addai.web.controller;

import cz.addai.components.session.AdditionalResponseData;
import cz.addai.web.model.request.AbstractRequest;
import cz.addai.web.model.response.AbstractResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class AbstractController {

    public static final String WATSON_TAG = "Watson";
    public static final String CLIENT_TAG = "Client app";

    public static final String POTENTIAL_RECIPIENTS_ATT_NAME = "potentialRecipients";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Logger logger = LoggerFactory.getLogger(AbstractController.class);

    @Resource
    protected AdditionalResponseData additionalResponseData;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void beforeProcess(AbstractRequest abstractRequest) {

    }

    protected void afterProcess(AbstractRequest abstractRequest, AbstractResponse abstractResponse) {
        // Potential recipients
        if (additionalResponseData.getPotentialRecipients() != null) {
            abstractResponse.addAdditionalInfo(POTENTIAL_RECIPIENTS_ATT_NAME, additionalResponseData.getPotentialRecipients());
        }
    }
}
