package service;

import com.paypal.fakepayment.*;
import com.paypal.fakepayment.model.dto.AccountDto;
import com.paypal.fakepayment.model.dto.UserDto;
import com.paypal.fakepayment.model.entities.Account;
import com.paypal.fakepayment.model.entities.User;
import com.paypal.fakepayment.repository.AccountRepository;
import com.paypal.fakepayment.repository.UserRepository;
import com.paypal.fakepayment.service.PaymentSystemCustomerManager;
import com.paypal.fakepayment.service.PaymentSystemCustomerManagerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PaymentSystemCustomerManagerTest {

    private PaymentSystemCustomerManager subject;

    @Mock
    private PaymentSystemUserManager paymentSystemUserManager;
    @Mock
    private PaymentSystemAccountManager paymentSystemAccountManager;
    @Mock
    private PaymentSystem paymentSystem;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AccountRepository accountRepository;
    private User userEntity;
    private Account account;
    private PaymentSystemUser user;

    @BeforeEach
    void setUp() throws PaymentSystemException {

        MockitoAnnotations.openMocks(this);
        subject = new PaymentSystemCustomerManagerImpl(
                paymentSystemUserManager,
                paymentSystemAccountManager,
                paymentSystem,
                userRepository,
                accountRepository
        );

        account = new Account();
        account.setAccountBalance(100);
        account.setAccountNumber("123abc");

        user = new UserDto.Builder()
                .withEmailAddress("john.doe@host.net")
                .withFirstName("john")
                .withLastName("doe")
//                .withAccount(account)
                .build();


        userEntity = new User();
        userEntity.setAccount(account);
        userEntity.setEmailAddress("john.doe@host.net");
        userEntity.setFirstName("john");
        userEntity.setFirstName("doe");
        userEntity.setAccount(account);
        account.setAccountUsers(new HashSet<>());

        List users = new ArrayList<User>();
        users.add(user);

        List usersPaymentSystemUser = new ArrayList<PaymentSystemUser>();
        usersPaymentSystemUser.add(new UserDto.Builder()
                .withAccount(
                        new AccountDto.Builder()
                                .withAccountBalance(account.getAccountBalance())
                                .withAccountNumber(account.getAccountNumber())
                                .build()
                )
                .withFirstName(userEntity.getLastName())
                .withLastName(userEntity.getFirstName())
                .withEmailAddress(userEntity.getEmailAddress())
                .build());



        when(userRepository.save(any())).thenReturn(userEntity);
        when(userRepository.findAll()).thenReturn(users);
        when(userRepository.getById(any())).thenReturn(userEntity);
        when(accountRepository.getById(any())).thenReturn(account);
        when(paymentSystemUserManager.createUser(user.getFirstName(),
                user.getLastName(),
                user.getEmailAddress())).thenReturn(user);
        when(userRepository.findAll()).thenReturn(usersPaymentSystemUser);
    }

    @Test
    void getUserData() {
        Assertions.assertNotNull(subject.getUserData("john.doe@host.net"));
    }

    @Test
    void createCustomer() throws PaymentSystemException {
        Assertions.assertNotNull(subject.createCustomer(user));
    }

    @Test
    void createAccount() throws PaymentSystemException {
        Assertions.assertNotNull(subject.createAccount(user, 100));
    }

    @Test
    void customerKpi() {
        Assertions.assertNotNull(subject.customerKpi("john.doe@host.net"));
    }
}