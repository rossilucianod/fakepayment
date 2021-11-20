package com.paypal.fakepayment.model.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.paypal.fakepayment.model.dto.UserDto;


import java.util.Set;

@JsonDeserialize(builder = PaymentSystemRequest.Builder.class)
public class PaymentSystemRequest {
    private final double amount;
    private final UserDto from;
    private final Set<UserDto> to;


    private PaymentSystemRequest(double amount,
                                 UserDto from,
                                 Set<UserDto> to){
        this.amount = amount;
        this.from = from;
        this.to = to;
    }

    public double getAmount() {
        return amount;
    }

    public UserDto getFrom() {
        return from;
    }

    public Set<UserDto> getTo() {
        return to;
    }

    public static class Builder {
        private double amount;
        private UserDto from;
        private Set<UserDto> to;

        public Builder withAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder withFrom(UserDto from) {
            this.from = from;
            return this;
        }

        public Builder withTo(Set<UserDto> to) {
            this.to = to;
            return this;
        }

        public PaymentSystemRequest build(){
            return new PaymentSystemRequest(
              amount,
              from,
              to
            );
        }

    }

}
