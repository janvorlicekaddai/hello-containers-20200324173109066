package cz.addai.watson;

import com.ibm.cloud.sdk.core.util.GsonSingleton;
import com.ibm.watson.assistant.v2.model.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Wrapper around MessageResponse
 */
public class MessageResponseHelper {

    private static final String RESPONSE_TYPE_TEXT = "text";
    private static final String RESPONSE_TYPE_OPTION = "option";

    private final MessageResponse messageResponse;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public MessageResponseHelper(MessageResponse messageResponse) {
        this.messageResponse = messageResponse;
    }

    /////////////////////////////////////////////////

    public MessageResponse getMessageResponse() {
        return messageResponse;
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

//    public List<DialogNodeAction> getActions() {
//        return messageResponse.getOutput().getActions();
//    }

    public List<Map<String,Object>> getUserDefinedActions() {
        var userDefined = messageResponse.getOutput().getUserDefined();
        if (userDefined == null || userDefined.isEmpty()) {
            return null;
        }
        return (List) userDefined.get("actions");
    }

    public Map<String,Object> getUserDefinedAction(String actionName) {
        List<Map<String,Object>> actions = getUserDefinedActions();
        if (actions == null || actions.isEmpty()) {
            return null;
        }
        return actions.stream().filter(a-> a.get("name").equals(actionName)).findAny().orElse(null);
    }

//    public Map<String,Object> getAction(String actionName) {
//        return getUserDefinedAction(actionName);
//    }

//    public RuntimeEntity getEntity(String entityName) {
//        var entities = getEntities();
//        if (entities == null || entities.isEmpty()) {
//            return null;
//        }
//        return entities.stream().filter(e->e.entity().equals(entityName)).findAny().orElse(null);
//    }

    public String[] getTextsBetweenDoubleQuotes() {
        String text = getTextResponse();
        return StringUtils.substringsBetween(text , "\"", "\"");
    }
}
