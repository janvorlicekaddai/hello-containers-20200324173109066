package cz.addai.aspect;

import cz.addai.web.model.request.AbstractRequest;
import cz.addai.web.model.response.AbstractResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;

import java.util.ArrayList;
import java.util.List;

@Aspect
public class RequestResponseAspect {

    private Logger logger = LoggerFactory.getLogger(RequestResponseAspect.class);

    private final List<IRequestResponseInterceptor> interceptors = new ArrayList<>();

    public void addInterceptor(IRequestResponseInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    /**
     * Pointcut = all methods annotated with @AaReq
     */
    @Pointcut("execution(@cz.addai.aspect.AaReq * *(..))")
    public void annotatedPointCut() {
        // No code
    }

    @Around("annotatedPointCut()")
    public Object logTimeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        // We are interested only for MethodInvocationProceedingJoinPoint
        if (joinPoint instanceof MethodInvocationProceedingJoinPoint) {
            MethodInvocationProceedingJoinPoint jp = (MethodInvocationProceedingJoinPoint) joinPoint;

            // Method arguments
            Object[] args = joinPoint.getArgs();

            // We expect only one argument
            AbstractRequest request = null;
            if (args.length == 1 && args[0] instanceof AbstractRequest) {
                request = (AbstractRequest) args[0];
                interceptRequest(request);
            }

            // Call original method
            try {
                Object retVal = joinPoint.proceed();
                if (retVal instanceof AbstractResponse) {
                    interceptResponse(request, (AbstractResponse) retVal);
                }

                return retVal;
            } catch (Exception ex) {
                logger.error("Controller exception", ex);
                throw ex;
            }

        } else {
            // just proceed
            return joinPoint.proceed();
        }
    }

    private void interceptRequest(AbstractRequest request) {
        interceptors.forEach(i-> i.beforeHandler(request));
    }

    private void interceptResponse(AbstractRequest request, AbstractResponse response) {
        interceptors.forEach(i-> i.afterHandler(request, response));
    }
}
