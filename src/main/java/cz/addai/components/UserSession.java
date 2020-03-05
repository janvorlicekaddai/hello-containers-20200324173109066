package cz.addai.components;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSession implements Serializable {

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

    public void deleteSession() {
        watsonSessionToken = null;
    }
}
