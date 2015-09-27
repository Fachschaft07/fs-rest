package edu.hm.cs.fs.common.constant;

public enum ErrorCode {

  ERROR_101(101, "java.lang.RuntimeException"),
  ERROR_103(103, "org.springframework.web.bind.MissingServletRequestParameterException"),
  ERROR_107(107, "java.lang.IllegalStateException"),
  ERROR_109(109, "java.io.IOException"),
  ERROR_113(113, "javax.xml.xpath.XPathExpressionException");
  
  private final int code;
  private final String exception;
  
  ErrorCode(int code, String exception) {
    this.code = code;
    this.exception = exception;
  }
  
  public int getCode() {
    return this.code;
  }
  
  public String getException() {
    return exception;
  }
  
  public static ErrorCode getErrorCodeByCode(int code){
    for(ErrorCode errorCode:ErrorCode.values()){
      if(errorCode.getCode() == code){
        return errorCode;
      }
    }
    
    return ERROR_101;
  }
  
  public static ErrorCode getErrorCodeByException(String exception){
    for(ErrorCode code:ErrorCode.values()){
      if(code.getException().equals(exception)) {
          return code;
      }
    }
    
    return ERROR_101;
  }
}
