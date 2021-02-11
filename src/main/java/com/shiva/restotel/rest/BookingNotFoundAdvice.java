package com.shiva.restotel.rest;

import org.hibernate.TransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class BookingNotFoundAdvice extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BookingNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String bookingNotFoundHandler(BookingNotFound bnf){
        return bnf.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidRequestParameter(TransactionSystemException te){
        return te.getOriginalException().getCause().getMessage();
    }

    @ResponseBody
    @ExceptionHandler(RoomNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String roomIdNotFound(RoomNotFound rnf){
        return rnf.getMessage();
    }
}
