package com.cognifia.lms.common.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CommonResult<T> implements Serializable {
    boolean successful;
    T data;
    String message;
}
