package cz.addai.watson.sm.transition;

import com.ibm.watson.assistant.v2.model.MessageResponse;
import cz.addai.persistence.domain.Recipient;
import cz.addai.service.WatsonMessageService;
import cz.addai.watson.sm.IState;
import cz.addai.watson.sm.ITransition;
import cz.addai.watson.sm.state.AmbiguousRecipientState;

import java.util.List;

public class AmbiguousRecipientTransition implements ITransition {

    private final WatsonMessageService watsonMessageService;
    private final List<Recipient> recipients;

    private MessageResponse messageResponse;

    public AmbiguousRecipientTransition(
            WatsonMessageService watsonMessageService,
            List<Recipient> recipients,
            MessageResponse messageResponse) {
        this.watsonMessageService = watsonMessageService;
        this.recipients = recipients;
        this.messageResponse = messageResponse;
    }

    @Override
    public IState transit(IState source) {
        messageResponse = watsonMessageService.sendMessageInternal(
                messageResponse.getContext(),
                "MoreRecipientsControlAddAI");

        return new AmbiguousRecipientState(messageResponse, source.getTransitionFactory(), recipients);
    }
}
