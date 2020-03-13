package cz.addai.persistence.domain;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "client")
public class Client implements Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "watson_apikey")
    private String watsonApiKey;

    @Column(name = "watson_assistant_id")
    private String watsonAssistantId;

    @Column(name = "watson_url")
    private String watsonUrl;

    @Column(name = "watson_version")
    private String watsonVersion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWatsonApiKey() {
        return watsonApiKey;
    }

    public void setWatsonApiKey(String watsonApiKey) {
        this.watsonApiKey = watsonApiKey;
    }

    public String getWatsonAssistantId() {
        return watsonAssistantId;
    }

    public void setWatsonAssistantId(String watsonAssistantId) {
        this.watsonAssistantId = watsonAssistantId;
    }

    public String getWatsonUrl() {
        return watsonUrl;
    }

    public void setWatsonUrl(String watsonUrl) {
        this.watsonUrl = watsonUrl;
    }

    public String getWatsonVersion() {
        return watsonVersion;
    }

    public void setWatsonVersion(String watsonVersion) {
        this.watsonVersion = watsonVersion;
    }
}
