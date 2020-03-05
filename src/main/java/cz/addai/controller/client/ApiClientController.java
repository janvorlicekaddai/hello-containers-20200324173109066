package cz.addai.controller.client;

import cz.addai.controller.AbstractController;
import cz.addai.service.WatsonSessionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ApiClientController extends AbstractController {

    @Resource
    private WatsonSessionService watsonSessionService;

    @RequestMapping(
            value="/client/v1/deleteSession",
            method = RequestMethod.DELETE)
    @ApiOperation(tags = {WATSON_TAG, CLIENT_TAG}, value = "Delete Watson session")
    public ResponseEntity<Object> deleteCurrentSession() {
        watsonSessionService.deleteSession();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
