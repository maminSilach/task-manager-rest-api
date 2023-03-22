package com.example.tasktracker.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class CustomExceptionHandler{


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseError> badRequest(BadRequestException e) {
        ResponseError responseError = new ResponseError(e.getMessage());
      return new ResponseEntity<>(responseError,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseError> notFoundRequest(NotFoundException e) {
      ResponseError responseError = new ResponseError(e.getMessage());
      return new ResponseEntity<>(responseError,HttpStatus.NOT_FOUND);
    }


}
