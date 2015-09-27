package edu.hm.cs.fs.common.model;

public class ExceptionResponse {

  private int errorCode;
  private String exception;
  private String message;
  private String url;

  public int getErrorCode() {
    return errorCode;
  }
  
  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }
  
  public String getException() {
    return exception;
  }

  public void setException(String exception) {
    this.exception = exception;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}
