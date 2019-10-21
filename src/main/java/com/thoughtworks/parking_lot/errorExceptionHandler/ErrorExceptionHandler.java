package com.thoughtworks.parking_lot.errorExceptionHandler;

import com.thoughtworks.parking_lot.errorMessage.ErrorMessage;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorMessage error404(NotFoundException e){
        ErrorMessage errMsg = new ErrorMessage();
        errMsg.setCode(404);
        errMsg.setMessage(e.getMessage());
        return errMsg;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage error400(BadRequestException e){
        ErrorMessage errMsg = new ErrorMessage();
        errMsg.setCode(400);
        errMsg.setMessage(e.getMessage());
        return errMsg;
    }
}
