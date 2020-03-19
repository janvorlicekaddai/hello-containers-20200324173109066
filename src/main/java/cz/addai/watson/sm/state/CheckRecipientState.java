package cz.addai.watson.sm.state;

import com.ibm.watson.assistant.v2.model.MessageResponse;
import cz.addai.components.session.AdditionalResponseData;
import cz.addai.persistence.domain.Recipient;
import cz.addai.watson.MessageResponseHelper;
import cz.addai.watson.sm.ITransition;
import cz.addai.watson.sm.transition.TransitionFactory;

import java.util.List;

public class CheckRecipientState extends AbstractState {

    private final List<Recipient> recipients;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public CheckRecipientState(TransitionFactory transitionFactory, List<Recipient> recipients) {
        super(transitionFactory);
        this.recipients = recipients;
    }

    /////////////////////////////////////////////////////

    @Override
    public boolean needUserInput() {
        return false;
    }

    @Override
    public MessageResponse askUser(AdditionalResponseData additionalResponseData) {
        return null;
    }

    @Override
    public ITransition createTransition(MessageResponseHelper messageResponseHelper) {
        if (recipients.isEmpty()) {
            return transitionFactory.createUnknownRecipientTransition(messageResponseHelper.getMessageResponse());
        }
        if (recipients.size() == 1) {
            return transitionFactory.createRecipientOkTransition(messageResponseHelper.getMessageResponse(), recipients.get(0));
        }
        return transitionFactory.createAmbiguousOkTransition(messageResponseHelper.getMessageResponse(), recipients);
    }

    @Override
    public String toString() {
        return "STATE:CHECK-RECIPIENT";
    }
}
