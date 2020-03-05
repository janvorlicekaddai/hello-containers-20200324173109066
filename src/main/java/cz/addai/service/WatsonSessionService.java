package cz.addai.service;

import com.ibm.cloud.sdk.core.http.HttpStatus;
import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.DeleteSessionOptions;
import com.ibm.watson.assistant.v2.model.SessionResponse;
import cz.addai.components.AdamConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WatsonSessionService extends AbstractWatsonService {

    @Resource
    private AdamConfig adamConfig;

    @Resource
    private Assistant assistantService;

    private Logger logger = LoggerFactory.getLogger(WatsonSessionService.class);

    public void openSession() {
        if (userSession.hasSession()) {
            logger.debug("Request to open new Watson session but there is already one: {}. Ignore.", userSession.getWatsonSessionToken());
            return;
        }

        var options = new CreateSessionOptions.Builder()
                .assistantId(adamConfig.getWatsonAssistantId())
                .build();
        var serviceCall = assistantService.createSession(options);
        var response = serviceCall.execute();

        if (response.getStatusCode() != HttpStatus.CREATED) {
            createSessionErrorResponse(response);
        } else {
            createSessionSuccess(response);
        }
    }

    public void deleteSession() {
        if (!userSession.hasSession()) {
            logger.debug("Request to delete Watson session but there is no session opened. Ignore.");
            return;
        }

        var options = new DeleteSessionOptions.Builder()
                .assistantId(adamConfig.getWatsonAssistantId())
                .sessionId(userSession.getWatsonSessionToken())
                .build();

        var serviceCall = assistantService.deleteSession(options);
        var response = serviceCall.execute();

        if (response.getStatusCode() != HttpStatus.OK) {
            deleteSessionErrorResponse(response);
        } else {
            deleteSessionSuccess();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void createSessionSuccess(Response<SessionResponse> response) {
        SessionResponse sessionResponse = response.getResult();
        logger.debug("New Watson session opened. Token: {}", sessionResponse.getSessionId());
        userSession.setWatsonSessionToken(sessionResponse.getSessionId());
    }

    private void createSessionErrorResponse(Response<SessionResponse> response) {
        String errorMessage = "Session creation failed. Returned status "
                + response.getStatusCode() + " - " + response.getStatusMessage();
        logger.error(errorMessage);
        throw new AdamServiceException(errorMessage);
    }

    private void deleteSessionSuccess() {
        logger.debug("Watson session was deleted.");
        userSession.deleteSession();
    }

    private void deleteSessionErrorResponse(Response<Void> response) {
        String errorMessage = "Session deletion failed. Returned status "
                + response.getStatusCode() + " - " + response.getStatusMessage();
        logger.error(errorMessage);
        throw new AdamServiceException(errorMessage);
    }
}
