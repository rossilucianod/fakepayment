package com.paypal.fakepayment.service;

import com.paypal.fakepayment.PaymentSystemAccount;
import com.paypal.fakepayment.PaymentSystemAccountManager;
import com.paypal.fakepayment.PaymentSystemException;
import com.paypal.fakepayment.PaymentSystemUser;
import com.paypal.fakepayment.model.dto.AccountDto;
import com.paypal.fakepayment.model.dto.UserDto;
import com.paypal.fakepayment.model.entities.Account;
import com.paypal.fakepayment.model.entities.User;
import com.paypal.fakepayment.repository.AccountRepository;
import com.paypal.fakepayment.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PaymentSystemAccountManagerImpl implements PaymentSystemAccountManager {

    private AccountRepository accountRepository;
    private UserRepository userRepository;

    public PaymentSystemAccountManagerImpl(AccountRepository accountRepository,
                                           UserRepository userRepository){
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PaymentSystemAccount createAccount(PaymentSystemUser user,
                                              double initialBalance) throws PaymentSystemException {

        try {
            User newUserDto = buildNewUserEntity(user, initialBalance);
            accountRepository.save(newUserDto.getAccount());
            User newUser = userRepository.save(newUserDto);
            return buildAccountDtoFromEntity(newUser.getAccount());
        } catch(DataAccessException e){
            throw new PaymentSystemException("There was and error creating a new Account", e);
        }

    }

    @Override
    public PaymentSystemAccount addUserToAccount(PaymentSystemUser user,
                                                 String accountNumber) throws PaymentSystemException {
        try {

            User existingUser = userRepository.getById(user.getEmailAddress());
            Account existingAccount = accountRepository.getById(accountNumber);
            existingAccount.getAccountUsers().add(existingUser);
            accountRepository.save(existingAccount);
            existingUser.setAccount(existingAccount);
            userRepository.save(existingUser);

            return  buildAccountDtoFromEntity(existingAccount);

        } catch(DataAccessException e) {
            throw new PaymentSystemException("There was an error adding a user to an account", e);
        }

    }

    @Override
    public Iterator<PaymentSystemAccount> getAllAccounts() {
        List<PaymentSystemAccount> allAccounts = accountRepository
                .findAll().stream()
                .map(this::buildAccountDtoFromEntity)
                .collect(Collectors.toList());

        return allAccounts.iterator();
    }

    @Override
    public PaymentSystemAccount getUserAccount(PaymentSystemUser userDto) {
        User user = userRepository.getById(userDto.getEmailAddress());
        return buildAccountDtoFromEntity(user.getAccount());
    }

    @Override
    public Iterator<PaymentSystemAccount> findAccountsByFullName(String firstName,
                                                                 String lastName) {

        if(firstName == null || lastName == null){
            return null;
        }

        List<PaymentSystemAccount> accountsByFullName = userRepository
                .findByFirstNameAndLastName(firstName, lastName)
                    .stream()
                    .map(user -> buildAccountDtoFromEntity(user.getAccount()))
                    .collect(Collectors.toList());

        return accountsByFullName.iterator();
    }

    @Override
    public Iterator<PaymentSystemAccount> findAccountsByFirstName(String firstName) {

        if(firstName == null){
            return null;
        }

        List<PaymentSystemAccount> accountsByFullName = userRepository
                .findByFirstName(firstName)
                .stream()
                .map(user -> buildAccountDtoFromEntity(user.getAccount()))
                .collect(Collectors.toList());

        return accountsByFullName.iterator();
    }

    @Override
    public Iterator<PaymentSystemAccount> findAccountsByLastName(String lastName) {

        if(lastName == null){
            return null;
        }

        List<PaymentSystemAccount> accountsByFullName = userRepository
                .findByLastName(lastName)
                .stream()
                .map(user -> buildAccountDtoFromEntity(user.getAccount()))
                .collect(Collectors.toList());

        return accountsByFullName.iterator();
    }

    private User buildNewUserEntity(PaymentSystemUser paymentSystemUser, double initialBalance){
        User user = new User();
        Set initialAccountUsers = new HashSet<User>();
        Account account = new Account();
        account.setAccountNumber(UUID.randomUUID().toString());
        account.setAccountBalance(initialBalance);
        user.setEmailAddress(paymentSystemUser.getEmailAddress());
        user.setFirstName(paymentSystemUser.getFirstName());
        user.setLastName(paymentSystemUser.getLastName());
        user.setAccount(account);
        initialAccountUsers.add(user);
        account.setAccountUsers(initialAccountUsers);

        return user;
    }

    private AccountDto buildAccountDtoFromEntity(Account account){
        return new AccountDto.Builder()
                .withAccountNumber(account.getAccountNumber())
                .withAccountBalance(account.getAccountBalance())
                .withAccountUsers(
                        account.getAccountUsers().stream()
                                .map(
                                        user -> new UserDto.Builder()
                                .withEmailAddress(user.getEmailAddress())
                                .withLastName(user.getLastName())
                                .withFirstName(user.getFirstName())
                                .build()
                                ).collect(Collectors.toSet())
                )
                .build();
    }

}
