package cz.addai.controller.response;

import io.swagger.annotations.ApiModel;

@ApiModel
public class ErrorMessage {

    public static enum ErrorAction {NONE, LOGOUT}

    private String errorCode;
    private String errorMessage;
    private ErrorAction errorAction;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorAction getErrorAction() {
        return errorAction;
    }

    public void setErrorAction(ErrorAction errorAction) {
        this.errorAction = errorAction;
    }

    public String toString() {
        return "ERROR CODE: " + errorCode + ", TEXT: " + errorMessage + ", ERROR ACTION: " + errorAction;
    }
}
