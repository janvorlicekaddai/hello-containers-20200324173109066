package cz.addai.components.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class AdditionalResponseData {

    private PotentialRecipients potentialRecipients;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public PotentialRecipients getPotentialRecipients() {
        return potentialRecipients;
    }

    public void setPotentialRecipients(PotentialRecipients potentialRecipients) {
        this.potentialRecipients = potentialRecipients;
    }
}
