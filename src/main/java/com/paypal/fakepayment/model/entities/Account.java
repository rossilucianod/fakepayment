package com.paypal.fakepayment.model.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Account {

    @Id
    private String accountNumber;
    private double accountBalance;
    @OneToMany
    private Set<User> accountUsers;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Set<User> getAccountUsers() {
        return accountUsers;
    }

    public void setAccountUsers(Set<User> accountUsers) {
        this.accountUsers = accountUsers;
    }
}
