package edu.hm.cs.fs.restapi;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import edu.hm.cs.fs.common.constant.ErrorCode;
import edu.hm.cs.fs.common.model.ExceptionResponse;

@ControllerAdvice
public class DefaultExceptionHandler {

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ExceptionResponse defaultErrorHandler(HttpServletRequest request, Exception e) {
    ExceptionResponse resp = new ExceptionResponse();

    resp.setErrorCode(ErrorCode.getErrorCodeByException(e.getClass().getName()).getCode());
    resp.setUrl(request.getRequestURL().toString());
    resp.setException(e.getClass().getName());
    resp.setMessage(e.getMessage());

    return resp;
  }
}
