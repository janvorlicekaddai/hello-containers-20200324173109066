package cz.addai.watson.sm.state;

import com.ibm.watson.assistant.v2.model.MessageResponse;
import cz.addai.watson.sm.IState;
import cz.addai.watson.sm.transition.TransitionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionStateProvider {

    @Resource
    private TransitionFactory transitionFactory;

    private IState state;

    public IState getState(MessageResponse lastResponse) {
        if (state == null) {
            state = new BaseState(lastResponse, transitionFactory);
        }
        return state;
    }

    public void setState(IState state) {
        this.state = state;
    }
}
