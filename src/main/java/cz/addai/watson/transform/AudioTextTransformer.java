package cz.addai.watson.transform;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class AudioTextTransformer implements InitializingBean {

    private static final String DICTIONARY_RESOURCE = "classpath:audioText.json";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Map<String,String> dictionary;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void afterPropertiesSet() throws Exception {
        File file = ResourceUtils.getFile(DICTIONARY_RESOURCE);
        String content = new String(Files.readAllBytes(file.toPath()));

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {}.getType();

        dictionary = gson.fromJson(content, type);
    }

    public List<String> transform(List<String> texts) {
        return texts.stream().map(this::transform).collect(Collectors.toList());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private String transform(String text) {
        String result = text;
        for(Map.Entry<String,String> dictionaryItem : dictionary.entrySet()) {
            result = transform(result, dictionaryItem);
        }
        return result;
    }

    private String transform(String text, Map.Entry<String,String> dictionaryItem) {
        return text.replaceAll("(?i)"+ Pattern.quote(dictionaryItem.getKey()), dictionaryItem.getValue());
    }
}
