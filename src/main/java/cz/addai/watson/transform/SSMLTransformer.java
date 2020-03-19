package cz.addai.watson.transform;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class SSMLTransformer {

    private static final String AFTER_PERIOD = "<break time=\"200ms\"/>";
    private static final String AFTER_COMMA = "<break time=\"100ms\"/>";
    private static final String AFTER_NL = "<break time=\"400ms\"/>";

    public List<String> transform(List<String> texts) {
        return texts.stream().map(this::transform).collect(Collectors.toList());
    }

    private String transform(String text) {
        String result = text;
        result = result.replaceAll("(?i)"+ Pattern.quote(". "), "." + AFTER_PERIOD + " ");
        result = result.replaceAll("(?i)"+ Pattern.quote(", "), "," + AFTER_COMMA + " ");
        result = result.replaceAll("\r", "");
        result = result.replaceAll("\n", "\n" + AFTER_NL);

        return "<spek>" + result + "</speak>";
    }
}
