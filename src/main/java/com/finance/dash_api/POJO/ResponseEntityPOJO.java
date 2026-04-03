package com.finance.dash_api.POJO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntityPOJO<T> {
    private String status ;
    private String message ;
    private T data;

}
