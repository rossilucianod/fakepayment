package com.paypal.fakepayment.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.paypal.fakepayment.PaymentSystemUser;

@JsonDeserialize(builder = UserDto.Builder.class)
public class UserDto implements PaymentSystemUser {

    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final AccountDto accountDto;

    private UserDto(String firstName,
                    String lastName,
                    String emailAddress,
                    AccountDto accountDto){
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.accountDto = accountDto;
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
        public Builder withAccountDto(AccountDto accountDto){
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
