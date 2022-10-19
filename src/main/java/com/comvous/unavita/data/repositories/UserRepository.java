package com.comvous.unavita.data.repositories;

import com.comvous.unavita.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.stream.Stream;
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);
    public Stream<User> findCustomersByLastNameIgnoreCaseContainingAndFirstNameIgnoreCaseContaining(String lastName, String firstName);
    public Stream<User> findCustomersByLastNameIgnoreCaseContaining(String lastName);
}
