package cz.addai.components.session;

import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.MessageContext;
import com.ibm.watson.discovery.v2.Discovery;

public class WatsonData {

    // Discovery
    private Discovery discoveryService;

    // Assistant
    private Assistant assistantService;

    // Watson message context
    private MessageContext messageContext;

    // Once the session is crated (opened)
    private String watsonSessionToken;


    public String getWatsonSessionToken() {
        return watsonSessionToken;
    }

    public void setWatsonSessionToken(String watsonSessionToken) {
        this.watsonSessionToken = watsonSessionToken;
    }

    public boolean hasSession() {
        return watsonSessionToken != null;
    }

    public Discovery getDiscoveryService() {
        return discoveryService;
    }

    public void setDiscoveryService(Discovery discoveryService) {
        this.discoveryService = discoveryService;
    }

    public Assistant getAssistantService() {
        return assistantService;
    }

    public void setAssistantService(Assistant assistantService) {
        this.assistantService = assistantService;
    }

    public MessageContext getMessageContext() {
        return messageContext;
    }

    public void setMessageContext(MessageContext messageContext) {
        this.messageContext = messageContext;
    }
}
