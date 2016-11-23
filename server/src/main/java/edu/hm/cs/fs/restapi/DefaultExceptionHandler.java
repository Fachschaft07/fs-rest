package edu.hm.cs.fs.restapi;

import edu.hm.cs.fs.common.constant.ErrorCode;
import edu.hm.cs.fs.common.model.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class DefaultExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);


    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse defaultErrorHandler(final HttpServletRequest request, final Exception exception) {
        logger.error(exception.getMessage(), exception);

        final ExceptionResponse resp = new ExceptionResponse();

        resp.setErrorCode(ErrorCode.getErrorCodeByException(exception.getClass().getName()).getCode());
        resp.setUrl(request.getServletPath());
        resp.setException(exception.getClass().getName());
        resp.setMessage(exception.getMessage());

        return resp;
    }
}
