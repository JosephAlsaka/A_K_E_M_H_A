package com.grad.akemha.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private Integer statuesCode;
    private String msg;
    private T data;
}

//public class BaseResponse {
//    private Integer statuesCode;
//    private String msg;
//}
