package com.paypal.fakepayment.service;

import com.paypal.fakepayment.PaymentSystemException;
import com.paypal.fakepayment.PaymentSystemUser;
import com.paypal.fakepayment.PaymentSystemUserManager;
import com.paypal.fakepayment.model.dto.UserDto;
import com.paypal.fakepayment.model.entities.User;
import com.paypal.fakepayment.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PaymentSystemUserManagerImpl implements PaymentSystemUserManager {

    private UserRepository userRepository;

    public PaymentSystemUserManagerImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public PaymentSystemUser createUser(String firstName,
                                        String lastName,
                                        String emailAddress) throws PaymentSystemException {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmailAddress(emailAddress);

        try {
            return convertToDto(userRepository.save(user));
        }catch (DataAccessException e){
            throw new PaymentSystemException("There was an error saving a new user", e);
        }
    }

    private UserDto convertToDto(User user){
        return new UserDto.Builder()
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withEmailAddress(user.getEmailAddress())
                .build();
    }
}
