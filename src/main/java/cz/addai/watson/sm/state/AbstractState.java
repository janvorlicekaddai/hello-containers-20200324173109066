package cz.addai.watson.sm.state;

import cz.addai.watson.MessageResponseHelper;
import cz.addai.watson.sm.IState;
import cz.addai.watson.sm.ITransition;
import cz.addai.watson.sm.transition.TransitionFactory;

public abstract class AbstractState implements IState {

    private static final String CHECK_RECIPIENT_ACTION = "checkRecipient";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected final TransitionFactory transitionFactory;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected AbstractState(TransitionFactory transitionFactory) {
        this.transitionFactory = transitionFactory;
    }

    //////////////////////////////////////////////////

    @Override
    public TransitionFactory getTransitionFactory() {
        return transitionFactory;
    }

    protected boolean hasCheckRecipientAction(MessageResponseHelper messageResponseHelper) {
        // Check recipient
        var checkRecipientAction = messageResponseHelper.getUserDefinedAction(CHECK_RECIPIENT_ACTION);
        return checkRecipientAction != null;
    }

    protected ITransition createCheckRecipientTransition(MessageResponseHelper messageResponseHelper) {
        var recipientRawTexts = messageResponseHelper.getTextsBetweenDoubleQuotes();
        if (recipientRawTexts.length == 0) {
            return null;
        }

        // Is the first
        String selectedRecipientName = recipientRawTexts[0].trim();
        if (selectedRecipientName.isBlank()) {
            if (recipientRawTexts.length == 1) {
                return null;
            }
            selectedRecipientName = recipientRawTexts[1].trim();
        }
        if (selectedRecipientName.isBlank()) {
            return null;
        }

        return transitionFactory.createCheckRecipientTransition(selectedRecipientName);
    }

}
