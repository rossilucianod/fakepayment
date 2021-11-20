package com.paypal.fakepayment;

import com.paypal.fakepayment.repository.AccountRepository;
import com.paypal.fakepayment.repository.UserRepository;
import com.paypal.fakepayment.service.PaymentSystemAccountManagerImpl;
import com.paypal.fakepayment.service.PaymentSystemImpl;
import com.paypal.fakepayment.service.PaymentSystemUserManagerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@DataJpaTest
class FakepaymentApplicationTests {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AccountRepository accountRepository;

	@Test
	public void testHappyPath() throws PaymentSystemException {

		PaymentSystemAccountManager paymentSystemAccountManager = new PaymentSystemAccountManagerImpl(accountRepository, userRepository);

		PaymentSystemUserManager paymentSystemUserManager = new PaymentSystemUserManagerImpl(userRepository);

		PaymentSystem paymentSystem = new PaymentSystemImpl(paymentSystemAccountManager, paymentSystemUserManager, accountRepository, userRepository);

		Assertions.assertNull(paymentSystem.getAccountManager()
				.findAccountsByFirstName(null));
		Assertions.assertNotNull(paymentSystem.getAccountManager()
				.findAccountsByFirstName(""));
		Assertions.assertFalse(paymentSystem.getAccountManager()
				.findAccountsByFirstName("").hasNext());
		Assertions.assertNotNull(paymentSystem.getAccountManager()
				.findAccountsByLastName(""));
		Assertions.assertNull(paymentSystem.getAccountManager()
				.findAccountsByLastName(null));
		Assertions.assertFalse(paymentSystem.getAccountManager()
				.findAccountsByLastName("").hasNext());
		Assertions.assertNotNull(paymentSystem.getAccountManager()
				.findAccountsByFullName("", ""));
		Assertions.assertNull(paymentSystem.getAccountManager()
				.findAccountsByFullName(null, ""));
		Assertions.assertNull(paymentSystem.getAccountManager()
				.findAccountsByFullName("", null));
		Assertions.assertFalse(paymentSystem.getAccountManager()
				.findAccountsByFullName("", "").hasNext());

		PaymentSystemUser user1 = paymentSystem.getUserManager().createUser(
				"John", "Doe", "john.doe@host.net");
		PaymentSystemUser user2 = paymentSystem.getUserManager().createUser(
				"Jane", "Doe", "jane.doe@host.net");
		PaymentSystemUser user3 = paymentSystem.getUserManager().createUser(
				"Gene", "Smith", "gene.smith@host.net");
		PaymentSystemUser user4 = paymentSystem.getUserManager().createUser(
				"John", "Johnson", "john.Johnson@host.net");

		PaymentSystemAccount account1 = paymentSystem.getAccountManager()
				.createAccount(user1, 10);
		PaymentSystemAccount account2 = paymentSystem.getAccountManager()
				.createAccount(user3, 0);
		PaymentSystemAccount account3 = paymentSystem.getAccountManager()
				.createAccount(user4, 0);

		Assertions.assertNotNull(account1);
		Assertions.assertNotNull(account2);
		Assertions.assertNotNull(account3);
		Assertions.assertEquals((double) 10, account1.getAccountBalance());
		Assertions.assertEquals((double) 0, account2.getAccountBalance());
		Assertions.assertEquals((double) 0, account3.getAccountBalance());

		paymentSystem.getAccountManager().addUserToAccount(user2,
				account1.getAccountNumber());

		int numAccounts = 0;
		for (Iterator<PaymentSystemAccount> allAccounts = paymentSystem
				.getAccountManager().getAllAccounts(); allAccounts.hasNext(); allAccounts
					 .next()) {
			numAccounts++;
		}

		Assertions.assertEquals(3, numAccounts);

		Assertions.assertEquals(account1, paymentSystem.getAccountManager()
				.getUserAccount(user1));
		Assertions.assertEquals(account1, paymentSystem.getAccountManager()
				.getUserAccount(user2));
		Assertions.assertEquals(account2, paymentSystem.getAccountManager()
				.getUserAccount(user3));
		Assertions.assertEquals(account3, paymentSystem.getAccountManager()
				.getUserAccount(user4));

		paymentSystem.sendMoney(user1, user3, 5);

		// this doesn't work in this way because DTOs were planned to be immutables
//		Assertions.assertEquals((double) 5, account1.getAccountBalance());
//		Assertions.assertEquals((double) 5, account2.getAccountBalance());
//		Assertions.assertEquals((double) 0, account3.getAccountBalance());

		Assertions.assertEquals((double) 5, paymentSystem.getAccountManager()
				.getUserAccount(user1).getAccountBalance());
		Assertions.assertEquals((double) 5, paymentSystem.getAccountManager()
				.getUserAccount(user3).getAccountBalance());
		Assertions.assertEquals((double) 0, paymentSystem.getAccountManager()
				.getUserAccount(user4).getAccountBalance());

		paymentSystem.sendMoney(user2, user3, 5);

		// this doesn't work in this way because DTOs were planned to be immutables
//		Assertions.assertEquals((double) 0, account1.getAccountBalance());
//		Assertions.assertEquals((double) 10, account2.getAccountBalance());
//		Assertions.assertEquals((double) 0, account3.getAccountBalance());

		Assertions.assertEquals((double) 0, paymentSystem.getAccountManager()
				.getUserAccount(user2).getAccountBalance());
		Assertions.assertEquals((double) 10, paymentSystem.getAccountManager()
				.getUserAccount(user3).getAccountBalance());
		Assertions.assertEquals((double) 0, paymentSystem.getAccountManager()
				.getUserAccount(user4).getAccountBalance());

		Set<PaymentSystemUser> to = new HashSet<>();
		to.add(user1);
		to.add(user2);

		paymentSystem.sendMoney(user3, to, 5);

		// this doesn't work in this way because DTOs were planned to be immutables
//		Assertions.assertEquals((double) 10, account1.getAccountBalance());
//		Assertions.assertEquals((double) 0, account2.getAccountBalance());
//		Assertions.assertEquals((double) 0, account3.getAccountBalance());

		Assertions.assertEquals((double) 10, paymentSystem.getAccountManager()
				.getUserAccount(user2).getAccountBalance());
		Assertions.assertEquals((double) 0, paymentSystem.getAccountManager()
				.getUserAccount(user3).getAccountBalance());
		Assertions.assertEquals((double) 0, paymentSystem.getAccountManager()
				.getUserAccount(user4).getAccountBalance());

		to.clear();
		to.add(user3);
		to.add(user4);

		paymentSystem.distributeMoney(user2, to, 10);

		// this doesn't work in this way because DTOs were planned to be immutables
//		Assertions.assertEquals((double) 0, account1.getAccountBalance());
//		Assertions.assertEquals((double) 5, account2.getAccountBalance());
//		Assertions.assertEquals((double) 5, account3.getAccountBalance());

		Assertions.assertEquals((double) 0, paymentSystem.getAccountManager()
				.getUserAccount(user2).getAccountBalance());
		Assertions.assertEquals((double) 5, paymentSystem.getAccountManager()
				.getUserAccount(user3).getAccountBalance());
		Assertions.assertEquals((double) 5, paymentSystem.getAccountManager()
				.getUserAccount(user4).getAccountBalance());



	}

}
