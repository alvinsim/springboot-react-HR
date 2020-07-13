package com.example.hr.controller.responsehandling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiResponse {

    public static final String RESPONSE_STATUS_ERROR = "error";
    public static final String RESPONSE_STATUS_FAIL = "fail";
    public static final String RESPONSE_STATUS_SUCCESS = "success";

    private String status;
    private String message;
    @Builder.Default
    private Object data = null;
}
