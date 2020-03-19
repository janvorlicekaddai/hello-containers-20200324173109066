package cz.addai.watson.sm.state;

import com.ibm.watson.assistant.v2.model.MessageResponse;
import cz.addai.components.session.AdditionalResponseData;
import cz.addai.components.session.PotentialRecipients;
import cz.addai.persistence.domain.Recipient;
import cz.addai.watson.MessageResponseHelper;
import cz.addai.watson.sm.ITransition;
import cz.addai.watson.sm.transition.TransitionFactory;

import java.util.List;
import java.util.stream.Collectors;

public class AmbiguousRecipientState extends AbstractState {

    private final MessageResponse messageResponse;
    private final List<Recipient> recipients;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public AmbiguousRecipientState(
            MessageResponse messageResponse,
            TransitionFactory transitionFactory,
            List<Recipient> recipients) {
        super(transitionFactory);
        this.messageResponse = messageResponse;
        this.recipients = recipients;
    }

    //////////////////////////////////////////////////////////////

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
        additionalResponseData.setPotentialRecipients(
                new PotentialRecipients(recipients.stream().map(Recipient::getName).collect(Collectors.toList()))
        );
        return messageResponse;
    }
}
