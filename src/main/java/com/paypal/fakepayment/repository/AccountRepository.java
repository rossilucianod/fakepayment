package com.paypal.fakepayment.repository;

import com.paypal.fakepayment.model.entities.Account;
import com.paypal.fakepayment.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Set<Account> findAccountsByAccountUsersEquals(User user);
}
