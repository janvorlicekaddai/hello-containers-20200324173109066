package cz.addai.watson.sm;

import com.ibm.watson.assistant.v2.model.MessageResponse;
import cz.addai.components.session.AdditionalResponseData;
import cz.addai.watson.MessageResponseHelper;
import cz.addai.watson.sm.state.SessionStateProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

@Component
public class StateMachine implements Serializable {

    @Resource
    private SessionStateProvider sessionStateProvider;

    @Resource
    private AdditionalResponseData additionalResponseData;

    private Logger logger = LoggerFactory.getLogger(StateMachine.class);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public MessageResponse handleState(MessageResponse lastResponse) {
        IState currentState = sessionStateProvider.getState(lastResponse);
        while(true) {
            logger.debug("Current state: " + currentState);

            // Detect next transition
            ITransition transition = currentState.createTransition(new MessageResponseHelper(lastResponse));
            if (transition == null) {
                // No transition needed, stay where we are
                logger.debug("No more transitions");
                return lastResponse;
            }

            // Provide transition
            logger.debug("Transiting from: " + currentState + " using: " + transition);
            currentState = transition.transit(currentState);
            logger.debug("   transited to: " + currentState);
            sessionStateProvider.setState(currentState);

            // Break?
            if (currentState.needUserInput()) {
                return currentState.askUser(additionalResponseData);
            }
        }
    }
}
