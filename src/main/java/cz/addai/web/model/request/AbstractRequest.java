package cz.addai.web.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

public class AbstractRequest {

    // Device time
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @ApiModelProperty(
            value = "Device time including time zone",
            dataType = "java.lang.String",
            example="2017-02-18T16:29:18.102+0000")
    @NotNull
    private ZonedDateTime timestamp;

    // Unique request ID
    @NotNull
    @NotBlank
    @Length(min = 36, max = 36)
    @ApiModelProperty(
            value = "Generated unique UUID request identifier.",
            dataType = "java.lang.String",
            example="123e4567-e89b-12d3-a456-426655440000")
    private String requestId;


    @NotNull
    @NotBlank
    @Length(min = 36, max = 36)
    @ApiModelProperty(
            value = "UUID client identifier. Must correspond with ID of one client in the local database.",
            dataType = "java.lang.String",
            example="123e4567-e89b-12d3-a456-426655440000")
    private String clientId;

    // The request name
    @NotNull
    @NotBlank
    @Length(min = 3, max = 30)
    private String request;

    // Info about the client
    @NotNull
    @Valid
    private RequestClientInfo clientInfo;

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public RequestClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(RequestClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }
}
