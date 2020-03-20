package cz.addai.watson.transform;

import cz.addai.components.session.AdditionalResponseData;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class OutputTextTransformer {

    @Resource
    private AudioTextTransformer audioTextTransformer;

    @Resource
    private SSMLTransformer ssmlTransformer;

    @Resource
    private AdditionalResponseData additionalResponseData;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void transform(List<String> texts) {
        List<String> audioTexts = audioTextTransformer.transform(texts);
        additionalResponseData.setAudioTexts(audioTexts);

        List<String> ssmlText = ssmlTransformer.transform(audioTexts);
        additionalResponseData.setSsmlTexts(ssmlText);
    }
}
