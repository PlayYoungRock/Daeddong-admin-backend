package kr.co.daeddongadmin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomExceptionHandler {

    public static ResponseEntity<Object> handleCustomException(CustomException ex) {
    String customErrorMessage = ex.getCustomMessage();
    String customErrorCode = ex.getCustomErrorCode();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getCustomErrorResponse(customErrorMessage, customErrorCode));
}

    private static Object getCustomErrorResponse(String errorMessage, String errorCode) {
        return "{\"resultCode\": \"" + errorCode + "\", \"errorMessage\": \"" + errorMessage + "\"}";
    }
}
