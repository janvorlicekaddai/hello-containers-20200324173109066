package cz.addai.web.model.response;

import io.swagger.annotations.ApiModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
public class ErrorResponse extends AbstractResponse {

    @NotNull
    private final ErrorMessage errorMessage;

    public ErrorResponse(@NotNull @NotBlank @Length(min = 36, max = 36) String requestId, @NotNull ErrorMessage errorMessage) {
        super(requestId);
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
