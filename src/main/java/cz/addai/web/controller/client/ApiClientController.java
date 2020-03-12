package cz.addai.web.controller.client;

import cz.addai.aspect.AaReq;
import cz.addai.service.WatsonMessageService;
import cz.addai.service.WatsonSessionService;
import cz.addai.web.controller.AbstractController;
import cz.addai.web.model.request.MessageRequest;
import cz.addai.web.model.response.MessageResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class ApiClientController extends AbstractController {

    @Resource
    private WatsonSessionService watsonSessionService;

    @Resource
    private WatsonMessageService watsonMessageService;

    @RequestMapping(
            value="/public/v1/deleteSession",
            method = RequestMethod.DELETE)
    @ApiOperation(tags = {WATSON_TAG, CLIENT_TAG}, value = "Delete Watson session")
    public ResponseEntity<Object> deleteCurrentSession() {
        watsonSessionService.deleteSession();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(
            value="/public/v1/sendMessage",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8",
            consumes = "application/json")
    @ApiOperation(tags = {WATSON_TAG}, value = "Send message to Watson")
    @AaReq
    public MessageResponse sendMessage(@RequestBody @Valid MessageRequest request) {

        super.beforeProcess(request);

        var watsonResponse = watsonMessageService.sendMessage(request);
        var response = new MessageResponse(request.getRequestId(), watsonResponse.toString());

        super.afterProcess(request, response);

        return response;
    }
}
