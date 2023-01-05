package com.viewnext.infraestructure.exception;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema
public class ApiErrorDetail {
    private String message;
    private String uri;
}
