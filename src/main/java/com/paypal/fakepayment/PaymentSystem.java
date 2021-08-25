package com.paypal.fakepayment;
import java.util.Set;


public interface PaymentSystem {

	PaymentSystemAccountManager getAccountManager();
	
	PaymentSystemUserManager getUserManager();

	void sendMoney(PaymentSystemUser from, PaymentSystemUser to, double amount)
			throws PaymentSystemException;

	void sendMoney(PaymentSystemUser from, Set<PaymentSystemUser> to,
			double amount) throws PaymentSystemException;

	void distributeMoney(PaymentSystemUser from, Set<PaymentSystemUser> to,
			double amount) throws PaymentSystemException;
}
