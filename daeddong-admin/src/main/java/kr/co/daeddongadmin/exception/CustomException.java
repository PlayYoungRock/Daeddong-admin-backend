package kr.co.daeddongadmin.exception;

public class CustomException extends RuntimeException {

    private String customMessage;
    private String customErrorCode;

    public CustomException(String message, String errorCode) {
        super(message);
        this.customMessage = message;
        this.customErrorCode = errorCode;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public String getCustomErrorCode() {
        return customErrorCode;
    }
}
