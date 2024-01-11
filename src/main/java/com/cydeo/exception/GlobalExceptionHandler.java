package com.cydeo.exception;


import com.cydeo.dto.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

       @ExceptionHandler(NotFoundException.class)
       public ResponseEntity<ExceptionWrapper> handleNotFoundException(
               NotFoundException exception, HttpServletRequest request){
           //create a json with ExceptionWrapper and return body with 404 status code
          ExceptionWrapper exceptionWrapper = new ExceptionWrapper();
          exceptionWrapper.setTimeStamp(LocalDateTime.now());
          exceptionWrapper.setMessage(exception.getMessage());
          exceptionWrapper.setStatus(HttpStatus.NOT_FOUND.value());
          exceptionWrapper.setPath(request.getRequestURI());

          return ResponseEntity.status(HttpStatus.NOT_FOUND)
                  .body(exceptionWrapper);
       }





}
