package cz.addai.watson.sm.state;

import com.ibm.watson.assistant.v2.model.MessageResponse;
import cz.addai.components.session.AdditionalResponseData;
import cz.addai.watson.MessageResponseHelper;
import cz.addai.watson.sm.ITransition;
import cz.addai.watson.sm.transition.TransitionFactory;

public class BaseState extends AbstractState {

    private final MessageResponse messageResponse;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public BaseState(MessageResponse messageResponse, TransitionFactory transitionFactory) {
        super(transitionFactory);
        this.messageResponse = messageResponse;
    }

    /////////////////////////////////////////////////

    @Override
    public ITransition createTransition(MessageResponseHelper messageResponseHelper) {

        if (hasCheckRecipientAction(messageResponseHelper)) {
            return createCheckRecipientTransition(messageResponseHelper);
        }

        // No transition
        return null;
    }

    @Override
    public boolean needUserInput() {
        return true;
    }

    @Override
    public MessageResponse askUser(AdditionalResponseData additionalResponseData) {
        return messageResponse;
    }

    @Override
    public String toString() {
        return "STATE:BASE";
    }
}
