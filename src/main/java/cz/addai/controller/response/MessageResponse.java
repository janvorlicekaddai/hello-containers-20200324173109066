package cz.addai.controller.response;

import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.annotations.ApiModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
public class MessageResponse extends AbstractResponse {

    @NotNull
    @JsonRawValue
    private final String watsonResponse;

    public MessageResponse(@NotNull @NotBlank @Length(min = 36, max = 36) String requestId, String watsonResponse) {
        super(requestId);
        this.watsonResponse = watsonResponse;
    }

    public String getWatsonResponse() {
        return watsonResponse;
    }
}
