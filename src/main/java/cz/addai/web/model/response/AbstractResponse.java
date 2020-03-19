package cz.addai.web.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractResponse {

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @ApiModelProperty(
            value = "Response time including time zone",
            dataType = "java.lang.String",
            example="2017-02-18T16:29:18.102+0000")
    @NotNull
    private ZonedDateTime timestamp;

    // Unique request ID
    @NotNull
    @NotBlank
    @Length(min = 36, max = 36)
    @ApiModelProperty(
            value = "UUID identifier copied from request.",
            dataType = "java.lang.String",
            example="123e4567-e89b-12d3-a456-426655440000")
    private String requestId;

    @NotNull
    @ApiModelProperty(
            value = "True if there is no error",
            dataType = "java.lang.Boolean")
    private boolean success;

    @ApiModelProperty("Optional - additional info like list of recepients, etc")
    private final Map<String, Object> additionalInfo = new HashMap<>();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * OK constructor
     */
    public AbstractResponse(@NotNull @NotBlank @Length(min = 36, max = 36) String requestId) {
        this.timestamp = ZonedDateTime.now();
        this.requestId = requestId;
        this.success = true;
    }

    /////////////////////////////////////////////

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public String getRequestId() {
        return requestId;
    }

    public boolean isSuccess() {
        return success;
    }

    public Map<String, Object> getAdditionalInfo() {
        return additionalInfo;
    }

    public void addAdditionalInfo(String name, Object value) {
        additionalInfo.put(name, value);
    }

}
