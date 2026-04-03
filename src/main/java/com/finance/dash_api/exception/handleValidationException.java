package com.finance.dash_api.exception;

import com.finance.dash_api.POJO.ExceptionPOJO;
import com.finance.dash_api.POJO.ResponseEntityPOJO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseEntityPOJO<Void>> handleValidationException(
            MethodArgumentNotValidException ex) {

        String errorMsg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        return ResponseEntity.badRequest()
                .body(new ResponseEntityPOJO<>("Failure2", errorMsg, null));
    }

    @ExceptionHandler(ExceptionPOJO.class)
    public ResponseEntity<ResponseEntityPOJO<Void>> handleCostumeException(ExceptionPOJO ex){
        return ResponseEntity.status(ex.getHttpStatus()).body(new  ResponseEntityPOJO<>("Failed" , ex.getErrorMessage() , null ));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseEntityPOJO<Void>> handleGenericException(Exception ex) {
        String message = ex.getMessage().substring(0,ex.getMessage().indexOf(':'));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseEntityPOJO<>("Failure1", message,null));
    }
}