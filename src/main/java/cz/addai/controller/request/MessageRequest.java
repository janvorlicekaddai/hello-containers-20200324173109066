package cz.addai.controller.request;

import io.swagger.annotations.ApiModel;

@ApiModel
public class MessageRequest extends AbstractRequest {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
