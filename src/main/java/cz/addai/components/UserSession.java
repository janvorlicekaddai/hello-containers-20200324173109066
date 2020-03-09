package cz.addai.components;

import com.ibm.watson.assistant.v2.model.MessageContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSession implements Serializable {

    // Once the session is crated (opened)
    private String watsonSessionToken;

    // May be null
    private MessageContext messageContext;

    public String getWatsonSessionToken() {
        return watsonSessionToken;
    }

    public void setWatsonSessionToken(String watsonSessionToken) {
        this.watsonSessionToken = watsonSessionToken;
    }

    public boolean hasSession() {
        return watsonSessionToken != null;
    }

    public void deleteSession() {
        watsonSessionToken = null;
    }

    public MessageContext getMessageContext() {
        return messageContext;
    }

    public void setMessageContext(MessageContext messageContext) {
        this.messageContext = messageContext;
    }
}
