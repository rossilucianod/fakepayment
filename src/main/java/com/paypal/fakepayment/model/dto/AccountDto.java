package com.paypal.fakepayment.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.paypal.fakepayment.PaymentSystemAccount;
import com.paypal.fakepayment.PaymentSystemException;
import com.paypal.fakepayment.PaymentSystemUser;

import java.util.Iterator;
import java.util.Set;

@JsonDeserialize(builder = AccountDto.Builder.class)
public class AccountDto implements PaymentSystemAccount {
    private final String accountNumber;
    private final double accountBalance;
    private final Set<PaymentSystemUser> accountUsers;

    private AccountDto(
            String accountNumber,
            double accountBalance,
            Set<PaymentSystemUser> accountUsers
    ){
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.accountUsers = accountUsers;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public Double getAccountBalance() {
        return accountBalance;
    }

    @Override
    public void incrementAccountBalance(Double amount) throws PaymentSystemException {

    }

    @Override
    public void decrementAccountBalance(Double amount) throws PaymentSystemException {

    }

    @Override
    public Iterator<PaymentSystemUser> getAccountUsers() {
        return accountUsers.iterator();
    }

    public static class Builder {
        private String accountNumber;
        private double accountBalance;
        private Set<PaymentSystemUser> accountUsers;

        public Builder withAccountNumber(String accountNumber){
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder withAccountBalance(double accountBalance){
            this.accountBalance = accountBalance;
            return this;
        }

        public Builder withAccountUsers(Set<PaymentSystemUser> accountUsers){
            this.accountUsers = accountUsers;
            return this;
        }

        public AccountDto build() {
            return new AccountDto(
                    accountNumber,
                    accountBalance,
                    accountUsers);
        }
    }
}
