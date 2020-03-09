package cz.addai.service;

import com.ibm.cloud.sdk.core.http.HttpStatus;
import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.*;
import cz.addai.components.AdamConfig;
import cz.addai.controller.request.MessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WatsonMessageService extends AbstractWatsonService {

    @Resource
    private AdamConfig adamConfig;

    @Resource
    private Assistant assistantService;

    @Resource
    private WatsonSessionService sessionService;

    private Logger logger = LoggerFactory.getLogger(WatsonMessageService.class);

    public String sendMessage(MessageRequest messageRequest) {
        if (!userSession.hasSession()) {
            logger.debug("Request to send message but there is no session. Creating session");
            sessionService.openSession();
            assert userSession.hasSession();
        }

        logger.debug("Sending message to Watson");

        var input = new MessageInput.Builder()
                .text(messageRequest.getText())
                .build();

        var options = new MessageOptions.Builder()
                .assistantId(adamConfig.getWatsonAssistantId())
                .sessionId(userSession.getWatsonSessionToken())
                .context(userSession.getMessageContext())
                .input(input)
                .build();

        // Call the service
        var serviceCall = assistantService.message(options);
        var response = serviceCall.execute();

        // OK?
        if (response.getStatusCode() != HttpStatus.OK) {
            // Throws an exception
            createSessionErrorResponse(response);
        }

        // Save context to session
        var result = response.getResult();
        userSession.setMessageContext(result.getContext());

        return result.getOutput().toString();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void createSessionErrorResponse(Response<MessageResponse> response) {
        String errorMessage = "Message failed. Returned status "
                + response.getStatusCode() + " - " + response.getStatusMessage();
        logger.error(errorMessage);
        throw new AdamServiceException(errorMessage);
    }

}
