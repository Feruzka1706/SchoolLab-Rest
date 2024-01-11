package com.cydeo.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionWrapper {

    private LocalDateTime timeStamp;
    private Integer status;
    private String message;
    private String path;

    private Integer errorCount;
    private List<ValidationError> validationErrorList;

}
