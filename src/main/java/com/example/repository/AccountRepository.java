package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    
    // @Query("SELECT a FROM Account a WHERE a.username = ?1")
    // Account findByUsername(String username);
}
