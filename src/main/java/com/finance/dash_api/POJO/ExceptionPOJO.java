package com.finance.dash_api.POJO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionPOJO extends RuntimeException{

    private String errorStatus;

    private String errorMessage;

    private HttpStatus httpStatus;
}
