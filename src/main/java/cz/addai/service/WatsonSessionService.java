package cz.addai.service;

import com.ibm.cloud.sdk.core.http.HttpStatus;
import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.DeleteSessionOptions;
import com.ibm.watson.assistant.v2.model.SessionResponse;
import cz.addai.components.session.UserSession;
import cz.addai.watson.WatsonAssistantFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WatsonSessionService extends AbstractWatsonService {

    @Resource
    private UserSession userSession;

    private Logger logger = LoggerFactory.getLogger(WatsonSessionService.class);

    public void openSession() {
        if (userSession.getWatsonData() != null && userSession.getWatsonData().hasSession()) {
            logger.debug("Request to open new Watson session but there is already one: {}. Ignore.",
                    userSession.getWatsonData().getWatsonSessionToken());
            return;
        }

        if (userSession.getWatsonData() == null) {
            var watsonAssistantFactory = new WatsonAssistantFactory(userSession);
            watsonAssistantFactory.decorate();
        }
        var watsonData = userSession.getWatsonData();

        var options = new CreateSessionOptions.Builder()
                .assistantId(userSession.getClient().getWatsonAssistantId())
                .build();
        var serviceCall = watsonData.getAssistantService().createSession(options);
        var response = serviceCall.execute();

        if (response.getStatusCode() != HttpStatus.CREATED) {
            createSessionErrorResponse(response);
        } else {
            createSessionSuccess(response);
        }
    }

    public void deleteSession() {
        if (userSession.getWatsonData() == null || !userSession.getWatsonData().hasSession()) {
            logger.debug("Request to delete Watson session but there is no session opened. Ignore.");
            return;
        }

        var watsonData = userSession.getWatsonData();

        var options = new DeleteSessionOptions.Builder()
                .assistantId(userSession.getClient().getWatsonAssistantId())
                .sessionId(watsonData.getWatsonSessionToken())
                .build();

        var serviceCall = watsonData.getAssistantService().deleteSession(options);
        try {
            var response = serviceCall.execute();

            if (response.getStatusCode() != HttpStatus.OK) {
                deleteSessionErrorResponse(response);
            } else {
                deleteSessionSuccess();
            }
        } catch (RuntimeException ex) {
            logger.error("Error deleting session", ex);
            userSession.deleteWatsonData();
            throw ex;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void createSessionSuccess(Response<SessionResponse> response) {
        SessionResponse sessionResponse = response.getResult();
        logger.debug("New Watson session opened. Token: {}", sessionResponse.getSessionId());
        userSession.getWatsonData().setWatsonSessionToken(sessionResponse.getSessionId());
    }

    private void createSessionErrorResponse(Response<SessionResponse> response) {
        String errorMessage = "Session creation failed. Returned status "
                + response.getStatusCode() + " - " + response.getStatusMessage();
        logger.error(errorMessage);
        throw new AdamServiceException(errorMessage);
    }

    private void deleteSessionSuccess() {
        logger.debug("Watson session was deleted.");
        userSession.deleteWatsonData();
    }

    private void deleteSessionErrorResponse(Response<Void> response) {
        String errorMessage = "Session deletion failed. Returned status "
                + response.getStatusCode() + " - " + response.getStatusMessage();
        logger.error(errorMessage);
        throw new AdamServiceException(errorMessage);
    }
}
