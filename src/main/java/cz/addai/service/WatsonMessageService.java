package cz.addai.service;

import com.ibm.cloud.sdk.core.http.HttpStatus;
import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.watson.assistant.v2.model.*;
import cz.addai.components.session.UserSession;
import cz.addai.watson.sm.StateMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.ZonedDateTime;

@Service
public class WatsonMessageService {

    @Resource
    private UserSession userSession;

    @Resource
    private WatsonAnswerLogService answerLogService;

    @Resource
    private StateMachine stateMachine;

    private Logger logger = LoggerFactory.getLogger(WatsonMessageService.class);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public MessageResponse sendMessage(MessageContext messageContext, String text) {

        var askedAt = ZonedDateTime.now();

        var result = sendMessageInternal(messageContext, text);

        // Handle states
        result = stateMachine.handleState(result);

        logger.debug("Watson responded {}", result.getOutput());
        answerLogService.logAnswer(askedAt, text, result);

        return result;
    }

    public MessageResponse sendMessageInternal(MessageContext messageContext, String text) {

        logger.debug("Sending message to Watson: {}", text);

        var watsonData = userSession.getWatsonData();

        var inputOptions = new MessageInputOptions.Builder()
                .returnContext(true)
                .build();

        var input = new MessageInput.Builder()
                .text(text)
                .options(inputOptions)
                .build();

        var options = new MessageOptions.Builder()
                .assistantId(userSession.getClient().getWatsonAssistantId())
                .sessionId(watsonData.getWatsonSessionToken())
                .context(messageContext)
                .input(input)
                .build();

        // Call the service
        var serviceCall = watsonData.getAssistantService().message(options);
        try {
            var response = serviceCall.execute();

            // OK?
            if (response.getStatusCode() != HttpStatus.OK) {
                // Throws an exception
                createSessionErrorResponse(response);
                assert false;
            }

            // Save context to session
            return response.getResult();
        } catch (RuntimeException ex) {
            logger.error("Error sending message", ex);
            userSession.deleteWatsonData();
            throw ex;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void createSessionErrorResponse(Response<MessageResponse> response) {
        String errorMessage = "Message failed. Returned status "
                + response.getStatusCode() + " - " + response.getStatusMessage();
        logger.error(errorMessage);
        throw new AdamServiceException(errorMessage);
    }

}
