package cz.addai.watson.sm.transition;

import com.ibm.watson.assistant.v2.model.MessageResponse;
import cz.addai.service.WatsonMessageService;
import cz.addai.watson.sm.IState;
import cz.addai.watson.sm.ITransition;
import cz.addai.watson.sm.state.UnknownRecipientState;

public class UnknownRecipientTransition implements ITransition {

    private static final String UNKNOWN_RECIPIENT_MESSAGE = "DoesNotExistControlAddAI";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private final WatsonMessageService watsonMessageService;
    private MessageResponse messageResponse;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public UnknownRecipientTransition(
            WatsonMessageService watsonMessageService,
            MessageResponse messageResponse) {
        this.watsonMessageService = watsonMessageService;
        this.messageResponse = messageResponse;
    }

    /////////////////////////////////////////////////////

    @Override
    public IState transit(IState source) {
        messageResponse = watsonMessageService.sendMessageInternal(
                messageResponse.getContext(),
                UNKNOWN_RECIPIENT_MESSAGE);

        return new UnknownRecipientState(messageResponse, source.getTransitionFactory());
    }

    @Override
    public String toString() {
        return "TRANSITION:UNKNOWN-RECIPIENT";
    }
}
