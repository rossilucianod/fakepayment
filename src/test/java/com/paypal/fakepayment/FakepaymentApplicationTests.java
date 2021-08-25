package com.paypal.fakepayment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class FakepaymentApplicationTests {

	@Test
	public void testHappyPath() throws PaymentSystemException {

		PaymentSystem paymentSystem = new PaymentSystemImpl();

		Assert.assertNull(paymentSystem.getAccountManager()
				.findAccountsByFirstName(null));
		Assert.assertNotNull(paymentSystem.getAccountManager()
				.findAccountsByFirstName(""));
		Assert.assertFalse(paymentSystem.getAccountManager()
				.findAccountsByFirstName("").hasNext());
		Assert.assertNotNull(paymentSystem.getAccountManager()
				.findAccountsByLastName(""));
		Assert.assertNull(paymentSystem.getAccountManager()
				.findAccountsByLastName(null));
		Assert.assertFalse(paymentSystem.getAccountManager()
				.findAccountsByLastName("").hasNext());
		Assert.assertNotNull(paymentSystem.getAccountManager()
				.findAccountsByFullName("", ""));
		Assert.assertNull(paymentSystem.getAccountManager()
				.findAccountsByFullName(null, ""));
		Assert.assertNull(paymentSystem.getAccountManager()
				.findAccountsByFullName("", null));
		Assert.assertFalse(paymentSystem.getAccountManager()
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

		Assert.assertNotNull(account1);
		Assert.assertNotNull(account2);
		Assert.assertNotNull(account3);
		Assert.assertEquals((double) 10, account1.getAccountBalance());
		Assert.assertEquals((double) 0, account2.getAccountBalance());
		Assert.assertEquals((double) 0, account3.getAccountBalance());

		paymentSystem.getAccountManager().addUserToAccount(user2,
				account1.getAccountNumber());

		int numAccounts = 0;
		for (Iterator<PaymentSystemAccount> allAccounts = paymentSystem
				.getAccountManager().getAllAccounts(); allAccounts.hasNext(); allAccounts
					 .next()) {
			numAccounts++;
		}

		Assert.assertEquals(3, numAccounts);

		Assert.assertEquals(account1, paymentSystem.getAccountManager()
				.getUserAccount(user1));
		Assert.assertEquals(account1, paymentSystem.getAccountManager()
				.getUserAccount(user2));
		Assert.assertEquals(account2, paymentSystem.getAccountManager()
				.getUserAccount(user3));
		Assert.assertEquals(account3, paymentSystem.getAccountManager()
				.getUserAccount(user4));

		paymentSystem.sendMoney(user1, user3, 5);

		Assert.assertEquals((double) 5, account1.getAccountBalance());
		Assert.assertEquals((double) 5, account2.getAccountBalance());
		Assert.assertEquals((double) 0, account3.getAccountBalance());

		paymentSystem.sendMoney(user2, user3, 5);

		Assert.assertEquals((double) 0, account1.getAccountBalance());
		Assert.assertEquals((double) 10, account2.getAccountBalance());
		Assert.assertEquals((double) 0, account3.getAccountBalance());

		Set<PaymentSystemUser> to = new HashSet<PaymentSystemUser>();
		to.add(user1);
		to.add(user2);

		paymentSystem.sendMoney(user3, to, 5);

		Assert.assertEquals((double) 10, account1.getAccountBalance());
		Assert.assertEquals((double) 0, account2.getAccountBalance());
		Assert.assertEquals((double) 0, account3.getAccountBalance());

		to.clear();
		to.add(user3);
		to.add(user4);

		paymentSystem.distributeMoney(user2, to, 10);

		Assert.assertEquals((double) 0, account1.getAccountBalance());
		Assert.assertEquals((double) 5, account2.getAccountBalance());
		Assert.assertEquals((double) 5, account3.getAccountBalance());
	}
}
