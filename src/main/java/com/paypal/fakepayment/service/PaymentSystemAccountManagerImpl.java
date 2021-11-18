package com.paypal.fakepayment.service;

import com.paypal.fakepayment.PaymentSystemAccount;
import com.paypal.fakepayment.PaymentSystemAccountManager;
import com.paypal.fakepayment.PaymentSystemException;
import com.paypal.fakepayment.PaymentSystemUser;
import com.paypal.fakepayment.repository.AccountRepository;
import com.paypal.fakepayment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class PaymentSystemAccountManagerImpl implements PaymentSystemAccountManager {

    private AccountRepository accountRepository;
    private UserRepository userRepository;

    public PaymentSystemAccountManagerImpl(AccountRepository accountRepository,
                                           UserRepository userRepository){
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PaymentSystemAccount createAccount(PaymentSystemUser user,
                                              double initialBalance) throws PaymentSystemException {
        return null;
    }

    @Override
    public PaymentSystemAccount addUserToAccount(PaymentSystemUser user,
                                                 String accountNumber) throws PaymentSystemException {
        return null;
    }

    @Override
    public Iterator<PaymentSystemAccount> getAllAccounts() {
        return null;
    }

    @Override
    public PaymentSystemAccount getUserAccount(PaymentSystemUser user) {
        return null;
    }

    @Override
    public Iterator<PaymentSystemAccount> findAccountsByFullName(String firstName,
                                                                 String lastName) {
        return null;
    }

    @Override
    public Iterator<PaymentSystemAccount> findAccountsByFirstName(String firstName) {
        return null;
    }

    @Override
    public Iterator<PaymentSystemAccount> findAccountsByLastName(String lastName) {
        return null;
    }
}
