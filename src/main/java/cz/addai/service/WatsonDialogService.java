package cz.addai.service;

import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.watson.assistant.v2.model.*;
import cz.addai.components.session.UserSession;
import cz.addai.watson.MessageResponseHelper;
import cz.addai.watson.transform.OutputTextTransformer;
import cz.addai.web.model.request.MessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class WatsonDialogService extends AbstractWatsonService {

    @Resource
    private UserSession userSession;

    @Resource
    private WatsonSessionService sessionService;

    @Resource
    private OutputTextTransformer outputTextTransformer;

    @Resource
    private WatsonMessageService watsonMessageService;

    private Logger logger = LoggerFactory.getLogger(WatsonDialogService.class);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public MessageResponse sendMessage(MessageRequest messageRequest) {
        if (userSession.getWatsonData() == null) {
            logger.debug("Request to send message but there is no session. Creating session");
            sessionService.openSession();
            assert userSession.getWatsonData() != null;
        }

        var messageContext = getOrCreateMessageContext();

        // Save context to session
        var result = watsonMessageService.sendMessage(messageContext, messageRequest.getText());

        messageContext = result.getContext();
        userSession.getWatsonData().setMessageContext(messageContext);

        MessageResponseHelper messageResponseHelper = new MessageResponseHelper(result);
        outputTextTransformer.transform(messageResponseHelper.getTextResponses());

        return result;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private MessageContext getOrCreateMessageContext() {
        var watsonData = userSession.getWatsonData();
        MessageContext messageContext = null;
        if (watsonData != null) {
            messageContext = watsonData.getMessageContext();
        }
        if (messageContext == null) {
            var skills = new MessageContextSkills();
            skills.put(
                    "main skill",
                    new MessageContextSkill.Builder()
                            .userDefined(Map.of("App", "Mobile"))
                            .build()
            );

            messageContext = new MessageContext.Builder()
                    .skills(skills)
                    .build();
        } else {
            populateMessageContext(messageContext);
        }
        return messageContext;
    }

    private void createSessionErrorResponse(Response<MessageResponse> response) {
        String errorMessage = "Message failed. Returned status "
                + response.getStatusCode() + " - " + response.getStatusMessage();
        logger.error(errorMessage);
        throw new AdamServiceException(errorMessage);
    }

    private void populateMessageContext(MessageContext messageContext) {
        if (messageContext == null) {
            return;
        }
        messageContext.skills()
                .get("main skill")
                .userDefined()
                .putIfAbsent("App", "Mobile");
    }
}
