package com.comvous.demo.data.repositories;

import com.comvous.demo.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.stream.Stream;
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);
    public Stream<User> findCustomersByLastNameIgnoreCaseContainingAndFirstNameIgnoreCaseContaining(String lastName, String firstName);
    public Stream<User> findCustomersByLastNameIgnoreCaseContaining(String lastName);

    boolean existsByEmail(String email);
}
