package com.paypal.fakepayment.service;

import com.paypal.fakepayment.*;
import com.paypal.fakepayment.model.entities.Account;
import com.paypal.fakepayment.repository.AccountRepository;
import com.paypal.fakepayment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PaymentSystemImpl implements PaymentSystem {

    private PaymentSystemAccountManager paymentSystemAccountManager;
    private PaymentSystemUserManager paymentSystemUserManager;
    private AccountRepository accountRepository;
    private UserRepository userRepository;

    public PaymentSystemImpl(
            PaymentSystemAccountManager paymentSystemAccountManager,
            PaymentSystemUserManager paymentSystemUserManager,
            AccountRepository accountRepository,
            UserRepository userRepository) {
        this.paymentSystemAccountManager = paymentSystemAccountManager;
        this.paymentSystemUserManager = paymentSystemUserManager;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
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

        PaymentSystemAccount fromAccount = paymentSystemAccountManager.getUserAccount(from);
        if(fromAccount.getAccountBalance() < amount){
            throw new PaymentSystemException("User has not sufficient funds in the account");
        }

        PaymentSystemAccount toAccount = paymentSystemAccountManager.getUserAccount(to);
        toAccount.incrementAccountBalance(amount);
        fromAccount.decrementAccountBalance(amount);

        Set accountsToSave = new HashSet<PaymentSystemAccount>();
        accountsToSave.add(fromAccount);
        accountsToSave.add(toAccount);
        saveAccountChanges(accountsToSave);

    }

    @Override
    public void sendMoney(PaymentSystemUser from, Set<PaymentSystemUser> to, double amount) throws PaymentSystemException {
        for (PaymentSystemUser user : to) {
            sendMoney(from, user, amount);
        }
    }

    @Override
    public void distributeMoney(PaymentSystemUser from, Set<PaymentSystemUser> to, double amount) throws PaymentSystemException {
        PaymentSystemAccount fromAccount = paymentSystemAccountManager.getUserAccount(from);
        if(fromAccount.getAccountBalance() * to.size() < amount){
            throw new PaymentSystemException("User has not sufficient funds in the account");
        }

        double amountToSent = amount / to.size();

        for (PaymentSystemUser user : to) {
            sendMoney(from, user, amountToSent);
        }

    }

    private Set<Account> getAccountsFromDtos(Set<PaymentSystemAccount> accountDtos){
        return accountDtos.stream()
                .map(accountDto -> {
                    Account existingAccount = accountRepository.findById(accountDto.getAccountNumber()).get();
                    existingAccount.setAccountBalance(accountDto.getAccountBalance());
                    return existingAccount;
                })
                .collect(Collectors.toSet());
    }

    protected void saveAccountChanges(Set<PaymentSystemAccount> accounts){
        getAccountsFromDtos(accounts).forEach(account -> accountRepository.save(account));
    }

}
