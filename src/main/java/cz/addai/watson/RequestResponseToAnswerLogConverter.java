package cz.addai.watson;

import com.ibm.watson.assistant.v2.model.MessageResponse;
import cz.addai.persistence.domain.AnswerLog;

import java.time.ZonedDateTime;

/**
 * Prepares data for AnswerLog entity
 */
public class RequestResponseToAnswerLogConverter {

    public static AnswerLog convert(ZonedDateTime askedAt, String clientId, String request, MessageResponse messageResponse) {

        var messageResponseHelper = new MessageResponseHelper(messageResponse);
        var answerLog = new AnswerLog();

        answerLog.setClientId(clientId);
        answerLog.setAskedAt(askedAt);
        answerLog.setRespondedAt(ZonedDateTime.now());

        answerLog.setQuestion(request);
        answerLog.setResponse(messageResponseHelper.getTextResponse());

        answerLog.setIntent(messageResponseHelper.getFirstIntentText());
        answerLog.setIntentConfidence(messageResponseHelper.getFirstIntentConfidence());
        answerLog.setIntents(messageResponseHelper.getIntentsAsJsonString());

        answerLog.setEntities(messageResponseHelper.getEntitiesAsJsonString());
        answerLog.setOptions(messageResponseHelper.getOptionsAsJsonString());

        answerLog.setFullData(messageResponse.toString());

        return answerLog;
    }
}
