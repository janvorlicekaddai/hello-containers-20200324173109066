package cz.addai.watson.sm;

import com.ibm.watson.assistant.v2.model.MessageResponse;
import cz.addai.components.session.AdditionalResponseData;
import cz.addai.watson.MessageResponseHelper;
import cz.addai.watson.sm.transition.TransitionFactory;

import java.io.Serializable;

public interface IState extends Serializable {

    boolean needUserInput();

    MessageResponse askUser(AdditionalResponseData additionalResponseData);

    ITransition createTransition(MessageResponseHelper messageResponseHelper);

    TransitionFactory getTransitionFactory();

}
