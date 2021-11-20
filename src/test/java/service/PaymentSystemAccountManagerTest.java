package service;

import com.paypal.fakepayment.PaymentSystemAccountManager;
import com.paypal.fakepayment.PaymentSystemException;
import com.paypal.fakepayment.PaymentSystemUser;
import com.paypal.fakepayment.model.dto.UserDto;
import com.paypal.fakepayment.model.entities.Account;
import com.paypal.fakepayment.model.entities.User;
import com.paypal.fakepayment.repository.AccountRepository;
import com.paypal.fakepayment.repository.UserRepository;
import com.paypal.fakepayment.service.PaymentSystemAccountManagerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PaymentSystemAccountManagerTest {


    @Mock
    private AccountRepository accountRepository;
    @Mock
    private UserRepository userRepository;
    private PaymentSystemAccountManager subject;
    private PaymentSystemUser user;
    private User userEntity;
    private Account account;



    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        subject = new PaymentSystemAccountManagerImpl(accountRepository, userRepository);
        user = new UserDto.Builder()
                .withEmailAddress("john.doe@host.net")
                .build();

        account = new Account();
        account.setAccountBalance(100);
        account.setAccountNumber("123abc");

        userEntity = new User();
        userEntity.setAccount(account);
        userEntity.setEmailAddress("john.doe@host.net");
        userEntity.setFirstName("john");
        userEntity.setFirstName("doe");
        userEntity.setAccount(account);
        account.setAccountUsers(new HashSet<>());

        List users = new ArrayList<User>();
        users.add(user);


        when(userRepository.save(any())).thenReturn(userEntity);
        when(userRepository.findAll()).thenReturn(users);
        when(userRepository.getById(any())).thenReturn(userEntity);
        when(accountRepository.getById(any())).thenReturn(account);

    }

    @Test
    void shouldCreateAccount() throws PaymentSystemException {
        Assertions.assertNotNull(subject.createAccount(user, 100));
    }

    @Test
    void shouldReturnAccounts(){
        Assertions.assertNotNull(subject.getAllAccounts());
    }

    @Test
    void shouldReturnUserAccount(){
        Assertions.assertNotNull(subject.getUserAccount(user));
    }

    @Test
    void shouldAddUserToAccount() throws PaymentSystemException {
        Assertions.assertNotNull(subject.addUserToAccount(user, "123abc"));
    }

}
