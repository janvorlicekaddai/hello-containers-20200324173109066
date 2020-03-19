package cz.addai.components.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Component
@RequestScope
public class AdditionalResponseData {

    private PotentialRecipients potentialRecipients;

    private List<String> audioTexts;
    private List<String> ssmlTexts;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public PotentialRecipients getPotentialRecipients() {
        return potentialRecipients;
    }

    public void setPotentialRecipients(PotentialRecipients potentialRecipients) {
        this.potentialRecipients = potentialRecipients;
    }

    public List<String> getAudioTexts() {
        return audioTexts;
    }

    public void setAudioTexts(List<String> audioTexts) {
        this.audioTexts = audioTexts;
    }

    public List<String> getSsmlTexts() {
        return ssmlTexts;
    }

    public void setSsmlTexts(List<String> ssmlTexts) {
        this.ssmlTexts = ssmlTexts;
    }
}
