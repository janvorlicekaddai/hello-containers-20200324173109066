package cz.addai.watson;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.discovery.v2.Discovery;
import cz.addai.components.session.UserSession;
import cz.addai.components.session.WatsonData;
import cz.addai.persistence.domain.Client;

/**
 * Decorate UserSession with Watson assistant
 */
public class WatsonAssistantFactory {

    private static final String DISCOVERY_VERSION_NAME = "2019-04-30";

    private final UserSession userSession;
    private final Client client;

    public WatsonAssistantFactory(UserSession userSession) {
        assert userSession.getClient() != null;
        assert userSession.getWatsonData() == null;
        this.userSession = userSession;
        this.client = userSession.getClient();
    }

    public void decorate() {
        WatsonData watsonData = new WatsonData();
        var authenticator = watsonAuthenticator();
        watsonData.setDiscoveryService(discoveryService(authenticator));
        watsonData.setAssistantService(assistantService(authenticator));
        userSession.setWatsonData(watsonData);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Authenticator watsonAuthenticator() {
        return new IamAuthenticator(client.getWatsonApiKey());
    }

    private Discovery discoveryService(Authenticator authenticator) {
        Discovery service = new Discovery(DISCOVERY_VERSION_NAME, authenticator);
        service.setServiceUrl(client.getWatsonUrl());
        return service;
    }

    public Assistant assistantService(Authenticator authenticator) {
        Assistant service = new Assistant(client.getWatsonVersion(), authenticator);
        service.setServiceUrl(client.getWatsonUrl());
        return service;
    }

}
