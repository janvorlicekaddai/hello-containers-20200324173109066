package cz.addai.watson.sm.transition;

import com.ibm.watson.assistant.v2.model.MessageResponse;
import cz.addai.persistence.domain.Recipient;
import cz.addai.service.WatsonMessageService;
import cz.addai.watson.sm.IState;
import cz.addai.watson.sm.ITransition;
import cz.addai.watson.sm.state.BaseState;

public class RecipientOkTransition implements ITransition {

    private final WatsonMessageService watsonMessageService;
    private final Recipient recipient;

    private MessageResponse messageResponse;

    public RecipientOkTransition(
            WatsonMessageService watsonMessageService,
            Recipient recipient,
            MessageResponse messageResponse) {
        this.recipient = recipient;
        this.messageResponse = messageResponse;
        this.watsonMessageService = watsonMessageService;
    }

    @Override
    public IState transit(IState source) {
        messageResponse = watsonMessageService.sendMessageInternal(
                messageResponse.getContext(),
                "ExistAndUniqueControlAddAI a je " + recipient.getName()
        );

        return new BaseState(messageResponse, source.getTransitionFactory());
    }

    @Override
    public String toString() {
        return "TRANSITION:RECIPIENT-OK(" + recipient.getName() + ")";
    }
}
