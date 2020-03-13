package cz.addai.persistence.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "answer_log")
public class AnswerLog implements Serializable {

    @Id
    @GeneratedValue(
            strategy= GenerationType.IDENTITY,
            generator="native"
    )
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "asked_at", nullable = false)
    private ZonedDateTime askedAt;

    @Column(name = "responded_at", nullable = false)
    private ZonedDateTime respondedAt;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "response", nullable = false)
    private String response;

    @Column(name = "intent_confidence", nullable = true)
    private Double intentConfidence;

    @Column(name = "intent", nullable = true)
    private String intent;

    @Column(name = "intents", nullable = true)
    private String intents;

    @Column(name = "entities", nullable = true)
    private String entities;

    @Column(name = "options", nullable = true)
    private String options;

    @Column(name = "full_data", nullable = false)
    private String fullData;

    @Column(name = "client_id", nullable = false)
    private String clientId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getAskedAt() {
        return askedAt;
    }

    public void setAskedAt(ZonedDateTime askedAt) {
        this.askedAt = askedAt;
    }

    public ZonedDateTime getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(ZonedDateTime respondedAt) {
        this.respondedAt = respondedAt;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Double getIntentConfidence() {
        return intentConfidence;
    }

    public void setIntentConfidence(Double intentConfidence) {
        this.intentConfidence = intentConfidence;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getIntents() {
        return intents;
    }

    public void setIntents(String intents) {
        this.intents = intents;
    }

    public String getEntities() {
        return entities;
    }

    public void setEntities(String entities) {
        this.entities = entities;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getFullData() {
        return fullData;
    }

    public void setFullData(String fullData) {
        this.fullData = fullData;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
