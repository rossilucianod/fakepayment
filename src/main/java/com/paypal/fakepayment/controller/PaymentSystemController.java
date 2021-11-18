package com.paypal.fakepayment.controller;


import com.paypal.fakepayment.PaymentSystemException;
import com.paypal.fakepayment.PaymentSystemUser;
import com.paypal.fakepayment.PaymentSystemUserManager;
import com.paypal.fakepayment.model.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/fakepay")
public class PaymentSystemController {

    private PaymentSystemUserManager paymentSystemUserManager;

    public PaymentSystemController (PaymentSystemUserManager paymentSystemUserManager){
        this.paymentSystemUserManager = paymentSystemUserManager;
    }

    @GetMapping("/customerlist")
    public ResponseEntity<List<UserDto>> customerList(){
        List<UserDto> users = new ArrayList<>();
        users.add(new UserDto.Builder()
                        .withFirstName("Ocano")
                        .withLastName("ocano")
                        .withEmailAddress("ocano@ocano")
                .build());

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/customerKpi")
    public ResponseEntity<List<UserDto>> customerKpi(){
        List<UserDto> users = new ArrayList<>();
        users.add(new UserDto.Builder()
                .withFirstName("Ocano")
                .withLastName("ocano")
                .withEmailAddress("ocano@ocano")
                .build());

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/sendMoney")
    public ResponseEntity<List<UserDto>> sendMoney(){
        List<UserDto> users = new ArrayList<>();
        users.add(new UserDto.Builder()
                .withFirstName("Ocano")
                .withLastName("ocano")
                .withEmailAddress("ocano@ocano")
                .build());

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/distribute")
    public ResponseEntity<List<UserDto>> distribute(){
        List<UserDto> users = new ArrayList<>();
        users.add(new UserDto.Builder()
                .withFirstName("Ocano")
                .withLastName("ocano")
                .withEmailAddress("ocano@ocano")
                .build());

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/customer")
    public ResponseEntity<PaymentSystemUser> createUser(@RequestBody UserDto userDto) throws PaymentSystemException {
        return new ResponseEntity<>(
                paymentSystemUserManager.createUser(
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmailAddress()), HttpStatus.OK);
    }

}
