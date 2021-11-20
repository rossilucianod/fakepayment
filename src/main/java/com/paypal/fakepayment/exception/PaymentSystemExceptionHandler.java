package com.paypal.fakepayment.exception;

import com.paypal.fakepayment.PaymentSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class PaymentSystemExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(PaymentSystemExceptionHandler.class);

    @ExceptionHandler(PaymentSystemException.class)
    public ResponseEntity<String> handlePaymentSystemException(PaymentSystemException e){
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
