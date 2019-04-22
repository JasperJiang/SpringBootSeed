package com.example.demo.audit;

import com.example.demo.exception.AppException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LoggingAspect {

    /**
     * Logging aspect that logs the method entering & method exiting
     *
     * @param joinPoint The proceeding join point
     * @return The object
     * @throws Throwable The throwable
     */
    @Around("within(com.example.demo..*) &&! within(com.example.demo.controller..*)" +
            "&& !@annotation(com.example.demo.audit.NoLogging)"
            + "&& !@target(com.example.demo.audit.NoLogging)")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String logName = joinPoint.getSignature().getName();
        logger.info("Entering method {}", logName);

        if (logger.isDebugEnabled()) {
            Object[] args = joinPoint.getArgs();
            if (args != null) {
                logger.debug("Incoming arguments size " + args.length);
                for (Object obj : args)
                    logger.debug("Incoming argument " + obj);
            }
        }
        Object val = joinPoint.proceed();
        logger.info("Exiting method {} ", logName);
        return val;
    }


    @AfterThrowing(pointcut = "within(com.example.demo.controller..*)", throwing = "exception")
    public void auditExceptionInfo(JoinPoint joinPoint, Throwable exception) {

        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String qryStr = req.getQueryString() != null ? "?" + req.getQueryString() : "";

        String requestUrl = req.getScheme() + "://" + req.getServerName()
                + ":" + req.getServerPort() + req.getContextPath() + req.getRequestURI()
                + qryStr;

        logger.error("********************************************************\n");
        logger.error(" Request Url : {} ", requestUrl);

        AppException.ErrorDetails errorDetails;
        if (exception instanceof AppException) {
            errorDetails = ((AppException) exception).getErrorInfo();
            logger.error("Error Details : {}", errorDetails);
            logger.error("", exception);
        }else {
            logger.error("", exception);
        }
        logger.error("********************************************************");
    }
}