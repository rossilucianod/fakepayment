package com.paypal.fakepayment.service;

import com.paypal.fakepayment.PaymentSystemAccount;
import com.paypal.fakepayment.PaymentSystemException;
import com.paypal.fakepayment.PaymentSystemUser;
import com.paypal.fakepayment.model.dto.AccountDto;

import java.util.List;
import java.util.Set;

public interface PaymentSystemCustomerManager {

    PaymentSystemUser getUserData(String email);

    PaymentSystemUser createCustomer(PaymentSystemUser user) throws PaymentSystemException;

    List<PaymentSystemUser> loadAllCustomers();

    PaymentSystemUser createAccount(PaymentSystemUser user,
                                    double initialBalance) throws PaymentSystemException;

    Set<AccountDto> customerKpi(String userEmail);


}
