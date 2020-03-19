package cz.addai.watson.sm.transition;

import cz.addai.watson.sm.IState;
import cz.addai.watson.sm.ITransition;

public class UnknownRecipientTransition implements ITransition {

    @Override
    public IState transit(IState source) {
        return null;
    }
}
