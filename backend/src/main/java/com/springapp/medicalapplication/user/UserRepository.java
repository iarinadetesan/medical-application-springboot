package com.springapp.medicalapplication.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);


    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<User> findByRole(Role role);
}