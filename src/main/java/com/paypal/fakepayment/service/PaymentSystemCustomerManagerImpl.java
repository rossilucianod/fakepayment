package com.paypal.fakepayment.service;

import com.paypal.fakepayment.*;
import com.paypal.fakepayment.model.dto.AccountDto;
import com.paypal.fakepayment.model.dto.UserDto;
import com.paypal.fakepayment.model.entities.Account;
import com.paypal.fakepayment.model.entities.User;
import com.paypal.fakepayment.repository.AccountRepository;
import com.paypal.fakepayment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentSystemCustomerManagerImpl implements PaymentSystemCustomerManager{

    private PaymentSystemUserManager paymentSystemUserManager;
    private PaymentSystemAccountManager paymentSystemAccountManager;
    private PaymentSystem paymentSystem;

    private UserRepository userRepository;
    private AccountRepository accountRepository;



    public PaymentSystemCustomerManagerImpl (PaymentSystemUserManager paymentSystemUserManager,
                                             PaymentSystemAccountManager paymentSystemAccountManager,
                                             PaymentSystem paymentSystem,
                                             UserRepository userRepository,
                                             AccountRepository accountRepository){

        this.paymentSystemUserManager = paymentSystemUserManager;
        this.paymentSystemAccountManager = paymentSystemAccountManager;
        this.paymentSystem = paymentSystem;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public PaymentSystemUser getUserData(String email) {
        return buildUserDto(userRepository.getById(email));
    }

    @Override
    public PaymentSystemUser createCustomer(PaymentSystemUser user) throws PaymentSystemException {
        return paymentSystemUserManager.createUser(user.getFirstName(),
                user.getLastName(),
                user.getEmailAddress());
    }

    @Override
    public List<PaymentSystemUser> loadAllCustomers() {
        return userRepository.findAll()
                .stream()
                .map(user -> buildUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public PaymentSystemUser createAccount(PaymentSystemUser user, double initialBalance) throws PaymentSystemException {
        paymentSystemAccountManager.createAccount(user, initialBalance);
        return getUserData(user.getEmailAddress());
    }

    @Override
    public Set<AccountDto> customerKpi(String email) {
        User user = userRepository.getById(email);
        Set<Account> accountByUser = accountRepository.findAccountsByAccountUsersEquals(user);

        return accountByUser.stream()
                .map(account ->
                    new AccountDto.Builder()
                            .withAccountNumber(account.getAccountNumber())
                            .withAccountBalance(account.getAccountBalance())
                        .build())
                .collect(Collectors.toSet());
    }

    private UserDto buildUserDto(User userEntity){
        AccountDto account = null;
        if(userEntity.getAccount() != null){
            account = new AccountDto.Builder()
                    .withAccountBalance(userEntity.getAccount().getAccountBalance())
                    .withAccountNumber(userEntity.getAccount().getAccountNumber())
                    .build();
        }
        return new UserDto.Builder()
                .withEmailAddress(userEntity.getEmailAddress())
                .withLastName(userEntity.getLastName())
                .withFirstName(userEntity.getFirstName())
                .withAccount(account)
                .build();
    }

    private Set<PaymentSystemAccount> allAccountsAsSet() {
        Iterator<PaymentSystemAccount> allAccounts = paymentSystemAccountManager.getAllAccounts();
        Set<PaymentSystemAccount> accounts = new HashSet<>();
        while(allAccounts.hasNext()){
            accounts.add(allAccounts.next());
        }
        return accounts;
    }

    private Set<PaymentSystemUser> userAsSet(PaymentSystemAccount account){
        Iterator<PaymentSystemUser> usersInAccount = account.getAccountUsers();
        Set<PaymentSystemUser> users = new HashSet<>();
        while(usersInAccount.hasNext()){
            users.add(usersInAccount.next());
        }
        return users;
    }

}
