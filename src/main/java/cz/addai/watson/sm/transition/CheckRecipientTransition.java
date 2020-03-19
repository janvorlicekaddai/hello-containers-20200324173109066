package cz.addai.watson.sm.transition;

import cz.addai.persistence.dao.RecipientDao;
import cz.addai.persistence.domain.Recipient;
import cz.addai.watson.sm.IState;
import cz.addai.watson.sm.ITransition;
import cz.addai.watson.sm.state.CheckRecipientState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CheckRecipientTransition implements ITransition {

    private final String recipientEntityValue;
    private final RecipientDao recipientDao;

    private Logger logger = LoggerFactory.getLogger(CheckRecipientTransition.class);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public CheckRecipientTransition(String recipientEntityValue, RecipientDao recipientDao) {
        this.recipientEntityValue = recipientEntityValue;
        this.recipientDao = recipientDao;
        logger.debug("Recipient entity value is: {}", recipientEntityValue);
    }

    //////////////////////////////////////////////////

    @Override
    public IState transit(IState source) {
        List<Recipient> recipients;
        // Single word?
        if (StringUtils.containsWhitespace(recipientEntityValue)) {
            recipients = recipientDao.findExactMatch(recipientEntityValue);
        } else {
            recipients = recipientDao.findMatch(recipientEntityValue);
        }
        if (recipients.isEmpty()) {
            logger.debug("Found NO recipient");
        } else {
            logger.debug("Found recipients: {}", recipients.stream().map(Recipient::getName).collect(Collectors.joining(",")));
        }

        return new CheckRecipientState(source.getTransitionFactory(), recipients);
    }

    @Override
    public String toString() {
        return "TRANSITION:CHECK-RECIPIENT";
    }
}
