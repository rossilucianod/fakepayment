package com.paypal.fakepayment.service;

import com.paypal.fakepayment.*;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PaymentSystemImpl implements PaymentSystem {

    private PaymentSystemAccountManager paymentSystemAccountManager;
    private PaymentSystemUserManager paymentSystemUserManager;

    public PaymentSystemImpl(
            PaymentSystemAccountManager paymentSystemAccountManager,
            PaymentSystemUserManager paymentSystemUserManager) {
        this.paymentSystemAccountManager = paymentSystemAccountManager;
        this.paymentSystemUserManager = paymentSystemUserManager;
    }


    @Override
    public PaymentSystemAccountManager getAccountManager() {
        return paymentSystemAccountManager;
    }

    @Override
    public PaymentSystemUserManager getUserManager() {
        return paymentSystemUserManager;
    }

    @Override
    public void sendMoney(PaymentSystemUser from, PaymentSystemUser to, double amount) throws PaymentSystemException {

    }

    @Override
    public void sendMoney(PaymentSystemUser from, Set<PaymentSystemUser> to, double amount) throws PaymentSystemException {

    }

    @Override
    public void distributeMoney(PaymentSystemUser from, Set<PaymentSystemUser> to, double amount) throws PaymentSystemException {

    }
}
