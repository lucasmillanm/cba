package com.elpatron.cba.repository;

import com.elpatron.cba.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("select count(s)>0 from User s where s.username = ?1")
    boolean existsByUsername(String username);
}
