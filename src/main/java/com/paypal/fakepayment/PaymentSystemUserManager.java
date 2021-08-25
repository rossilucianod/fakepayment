package com.paypal.fakepayment;
public interface PaymentSystemUserManager {

	PaymentSystemUser createUser(String firstName, String lastName,
			String emailAddress) throws PaymentSystemException;
}
