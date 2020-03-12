package cz.addai.aspect;

import cz.addai.web.model.request.AbstractRequest;
import cz.addai.web.model.response.AbstractResponse;

public interface IRequestResponseInterceptor {

    void beforeHandler(AbstractRequest request);
    void afterHandler(AbstractRequest request, AbstractResponse response);

}
