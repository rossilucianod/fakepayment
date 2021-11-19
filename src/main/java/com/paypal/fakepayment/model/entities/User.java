package com.paypal.fakepayment.model.entities;

import javax.persistence.*;

@Entity
public class User {
    @Id
    private String emailAddress;
    private String firstName;
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "accountNumber")
    private Account account;

        public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
