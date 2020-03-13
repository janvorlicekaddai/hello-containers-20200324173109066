package cz.addai.watson;

import com.ibm.cloud.sdk.core.util.GsonSingleton;
import com.ibm.watson.assistant.v2.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.RuntimeEntity;
import com.ibm.watson.assistant.v2.model.RuntimeIntent;
import com.ibm.watson.assistant.v2.model.RuntimeResponseGeneric;

import java.util.List;

/**
 * Wrapper around MessageResponse
 */
public class MessageResponseHelper {

    private static final String RESPONSE_TYPE_TEXT = "text";
    private static final String RESPONSE_TYPE_OPTION = "option";

    private final MessageResponse messageResponse;

    public MessageResponseHelper(MessageResponse messageResponse) {
        this.messageResponse = messageResponse;
    }

    public List<RuntimeResponseGeneric> getGeneric() {
        return messageResponse.getOutput().getGeneric();
    }

    public String getTextResponse() {
        List<RuntimeResponseGeneric> generics = getGeneric();
        if (generics == null || generics.isEmpty()) {
            return "";
        }

        return generics.stream()
                .filter(g-> g.responseType().equals(RESPONSE_TYPE_TEXT))
                .findAny()
                .map(RuntimeResponseGeneric::text)
                .orElse("");
    }

    public RuntimeResponseGeneric getOptions() {
        List<RuntimeResponseGeneric> generics = getGeneric();
        if (generics == null || generics.isEmpty()) {
            return null;
        }

        return generics.stream().filter(g-> g.responseType().equals(RESPONSE_TYPE_OPTION)).findAny().orElse(null);
    }

    public List<RuntimeIntent> getIntents() {
        return messageResponse.getOutput().getIntents();
    }

    public List<RuntimeEntity> getEntities() {
        return messageResponse.getOutput().getEntities();
    }

    public RuntimeIntent getFirstIntentOrNull() {
        List<RuntimeIntent> intents = getIntents();
        if (intents == null || intents.isEmpty()) {
            return null;
        }

        return intents.get(0);
    }

    public String getFirstIntentText() {
        var intent = getFirstIntentOrNull();
        if (intent == null) {
            return null;
        }
        return intent.intent();
    }

    public Double getFirstIntentConfidence() {
        var intent = getFirstIntentOrNull();
        if (intent == null) {
            return null;
        }
        return intent.confidence();
    }

    public String getIntentsAsJsonString() {
        return GsonSingleton.getGson().toJson(getIntents());
    }

    public String getEntitiesAsJsonString() {
        return GsonSingleton.getGson().toJson(getEntities());
    }

    public String getOptionsAsJsonString() {
        var options = getOptions();
        if (options == null) {
            return null;
        }
        return GsonSingleton.getGson().toJson(options.options());
    }
}
