package cz.addai.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.Instant;

//@Entity
//@Table(name = "answer_log")
public class AnswerLog {

    private String id;

    @Column(name = "session_id", nullable = false)
    private String sessionId;


    @Column(name = "asked_at", nullable = false)
    private Instant askedAt;

    @Column(name = "answered_at", nullable = false)
    private Instant answeredAt;


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
}
