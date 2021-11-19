package com.paypal.fakepayment.repository;

import com.paypal.fakepayment.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByFirstNameAndLastName(String firstName, String lastName);

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

}
