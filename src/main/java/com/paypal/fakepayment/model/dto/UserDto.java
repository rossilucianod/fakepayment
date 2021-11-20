package com.paypal.fakepayment.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.paypal.fakepayment.PaymentSystemUser;

import java.util.Objects;

@JsonDeserialize(builder = UserDto.Builder.class)
public class UserDto implements PaymentSystemUser {

    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final AccountDto account;

    private UserDto(String firstName,
                    String lastName,
                    String emailAddress,
                    AccountDto accountDto){
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.account = accountDto;
    }

    public AccountDto getAccount() {
        return account;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(firstName, userDto.firstName) && Objects.equals(lastName, userDto.lastName) && Objects.equals(emailAddress, userDto.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, emailAddress);
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String emailAddress;
        private AccountDto accountDto;

        public Builder withFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public Builder withEmailAddress(String emailAddress){
            this.emailAddress = emailAddress;
            return this;
        }
        public Builder withAccount(AccountDto accountDto){
            this.accountDto = accountDto;
            return this;
        }

        public UserDto build(){
            return new UserDto(
                    firstName,
                    lastName,
                    emailAddress,
                    accountDto
            );
        }
    }

}
