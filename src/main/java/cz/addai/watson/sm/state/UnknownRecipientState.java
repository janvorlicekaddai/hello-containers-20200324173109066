package cz.addai.watson.sm.state;

import com.ibm.watson.assistant.v2.model.MessageResponse;
import cz.addai.components.session.AdditionalResponseData;
import cz.addai.watson.MessageResponseHelper;
import cz.addai.watson.sm.ITransition;
import cz.addai.watson.sm.transition.TransitionFactory;

public class UnknownRecipientState extends AbstractState {

    protected UnknownRecipientState(TransitionFactory transitionFactory) {
        super(transitionFactory);
    }

    @Override
    public boolean needUserInput() {
        return true;
    }

    @Override
    public MessageResponse askUser(AdditionalResponseData additionalResponseData) {
        return null;
    }

    @Override
    public ITransition createTransition(MessageResponseHelper messageResponseHelper) {
        return null;
    }
}
