package com.paypal.fakepayment.controller;


import com.paypal.fakepayment.*;
import com.paypal.fakepayment.model.dto.AccountDto;
import com.paypal.fakepayment.model.dto.UserDto;
import com.paypal.fakepayment.model.request.PaymentSystemRequest;
import com.paypal.fakepayment.service.PaymentSystemCustomerManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fakepay")
public class PaymentSystemController {


    private PaymentSystemCustomerManager paymentSystemCustomerManager;
    private PaymentSystem paymentSystem;

    public PaymentSystemController (PaymentSystemCustomerManager paymentSystemCustomerManager,
                                    PaymentSystem paymentSystem){
        this.paymentSystemCustomerManager = paymentSystemCustomerManager;
        this.paymentSystem = paymentSystem;
    }

    @Operation(summary = "Customer Information", description = "List Customer Information by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation") })

    @GetMapping("/customerlist/{email}")
    public ResponseEntity<PaymentSystemUser> customerList(@PathVariable String email){

        return new ResponseEntity<>(paymentSystemCustomerManager.getUserData(email), HttpStatus.OK);
    }

    @Operation(summary = "Customer KPI", description = "Load accounts and balance related to this request user by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation") })
    @GetMapping("/customerkpi/{email}")
    public ResponseEntity<Set<AccountDto>> customerKpi(@PathVariable String email){
        return new ResponseEntity<>(paymentSystemCustomerManager.customerKpi(email), HttpStatus.OK);
    }

    @Operation(summary = "Send Money to Account", description = "Send specified amount in request to a list of users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation") })
    @PostMapping("/sendmoney")
    public ResponseEntity<String> sendMoney(@RequestBody PaymentSystemRequest request) throws PaymentSystemException {
        paymentSystem.sendMoney(request.getFrom(), convertUserDtosToPaymentSystemUsers(request.getTo()), request.getAmount());
        return new ResponseEntity<>("Money Sent", HttpStatus.OK);
    }

    @Operation(summary = "Distribute Money to Accounts", description = "Split the given amount in request between listed users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation") })
    @PostMapping("/distribute")
    public ResponseEntity<String> distribute(@RequestBody PaymentSystemRequest request) throws PaymentSystemException {
        paymentSystem.distributeMoney(request.getFrom(), convertUserDtosToPaymentSystemUsers(request.getTo()), request.getAmount());
        return new ResponseEntity<>("Money Distributed", HttpStatus.OK);
    }

    @Operation(summary = "Create Customer", description = "Given an User in request create a new one in system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user successfully created") })
    @PostMapping("/customers")
    public ResponseEntity<PaymentSystemUser> createCustomer(@RequestBody UserDto userDto) throws PaymentSystemException {
        return new ResponseEntity<>(
                paymentSystemCustomerManager.createCustomer(userDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Customer to account", description = "Add a user to an existing account by given email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User successfully added to the account") })
    @PostMapping("/customers/{email}/accounts/{account}")
    public ResponseEntity<PaymentSystemAccount> addCustomerToAccount(@PathVariable String email, @PathVariable String account) throws PaymentSystemException {
        UserDto user = new UserDto.Builder()
                .withEmailAddress(email)
                .build();
        return new ResponseEntity<>(
                paymentSystem.getAccountManager().addUserToAccount(user, account), HttpStatus.CREATED);
    }

    @Operation(summary = "List Users", description = "List all users in DB (No Pagination)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation") })
    @GetMapping("/customers")
    public ResponseEntity<List<PaymentSystemUser>> listUsers() {
        return new ResponseEntity<>(
                paymentSystemCustomerManager.loadAllCustomers(), HttpStatus.OK);
    }

    @Operation(summary = "Create Account", description = "Given a user, creates a new account with initial balance ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "successful operation") })
    @PostMapping("/customers/accounts")
    public ResponseEntity<PaymentSystemUser> createCustomerAccount(@RequestBody PaymentSystemRequest request) throws PaymentSystemException {
        return new ResponseEntity<>(
                paymentSystemCustomerManager.createAccount(request.getFrom(), request.getAmount()), HttpStatus.CREATED);
    }

    private Set<PaymentSystemUser> convertUserDtosToPaymentSystemUsers(Set<UserDto> users){
        return users.stream()
                .map(userDto -> (PaymentSystemUser)userDto)
                .collect(Collectors.toSet());
    }

}
