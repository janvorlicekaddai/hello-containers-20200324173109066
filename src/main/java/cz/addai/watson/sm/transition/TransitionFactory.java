package cz.addai.watson.sm.transition;

import com.ibm.watson.assistant.v2.model.MessageResponse;
import cz.addai.persistence.dao.RecipientDao;
import cz.addai.persistence.domain.Recipient;
import cz.addai.service.WatsonMessageService;
import cz.addai.watson.sm.ITransition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TransitionFactory {

    @Resource
    private RecipientDao recipientDao;

    @Resource
    private WatsonMessageService watsonMessageService;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ITransition createCheckRecipientTransition(String recipientEntityValue) {
        return new CheckRecipientTransition(recipientEntityValue, recipientDao);
    }

    public ITransition createUnknownRecipientTransition() {
        return new UnknownRecipientTransition();
    }

    public ITransition createRecipientOkTransition(MessageResponse messageResponse, Recipient recipient) {
        return new RecipientOkTransition(watsonMessageService, recipient, messageResponse);
    }

    public ITransition createAmbiguousOkTransition(MessageResponse messageResponse, List<Recipient> recipients) {
        return new AmbiguousRecipientTransition(watsonMessageService, recipients, messageResponse);
    }
}
