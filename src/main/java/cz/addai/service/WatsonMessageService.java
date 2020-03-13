package cz.addai.service;

import com.ibm.cloud.sdk.core.http.HttpStatus;
import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.watson.assistant.v2.model.*;
import cz.addai.components.session.UserSession;
import cz.addai.web.model.request.MessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.ZonedDateTime;

@Service
public class WatsonMessageService extends AbstractWatsonService {

    @Resource
    private UserSession userSession;

    @Resource
    private WatsonSessionService sessionService;

    @Resource
    private WatsonAnswerLogService answerLogService;

    private Logger logger = LoggerFactory.getLogger(WatsonMessageService.class);



    public MessageResponse sendMessage(MessageRequest messageRequest) {
        if (userSession.getWatsonData() == null) {
            logger.debug("Request to send message but there is no session. Creating session");
            sessionService.openSession();
            assert userSession.getWatsonData() != null;
        }

        logger.debug("Sending message to Watson: {}", messageRequest.getText());

        var watsonData = userSession.getWatsonData();

        var messageContext = watsonData.getMessageContext();
        populateMessageContext(messageContext);

        var inputOptions = new MessageInputOptions.Builder()
                .returnContext(true)
                .build();

        var input = new MessageInput.Builder()
                .text(messageRequest.getText())
                .options(inputOptions)
                .build();

        var options = new MessageOptions.Builder()
                .assistantId(userSession.getClient().getWatsonAssistantId())
                .sessionId(watsonData.getWatsonSessionToken())
                .context(messageContext)
                .input(input)
                .build();

        // Call the service
        var askedAt = ZonedDateTime.now();
        var serviceCall = watsonData.getAssistantService().message(options);
        var response = serviceCall.execute();

        // OK?
        if (response.getStatusCode() != HttpStatus.OK) {
            // Throws an exception
            createSessionErrorResponse(response);
            assert false;
        }

        // Save context to session
        var result = response.getResult();
        messageContext = result.getContext();
        populateMessageContext(messageContext);
        watsonData.setMessageContext(messageContext);

        logger.debug("Watson responded {}", result.getOutput());
        answerLogService.logAnswer(askedAt, messageRequest.getText(), result);

        return result;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
