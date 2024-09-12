package com.example.service;

import org.springframework.stereotype.Service;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.example.entity.Account;
import java.util.Optional;
import java.util.List;

@Service
@Transactional
public class AccountService {
    
    @Autowired
    AccountRepository accountRepository;

    public Account getAccountById(int id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        return optionalAccount.orElse(null);
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account createAccount(Account account){
        Optional<Account> checkAccount = accountRepository.findById(account.getAccountId());
        if (checkAccount.isPresent()) return null;
        if (checkAccount.get().getUsername().equals(account.getUsername())) return null;

        return accountRepository.save(account);
    }

    public Account verifyAccount(Account account){
        Optional<Account> checkAccount = accountRepository.findById(account.getAccountId());
        if (checkAccount.get().getUsername().equals(account.getUsername()) && checkAccount.get().getPassword().equals(account.getPassword())){
            return checkAccount.get();
        }
        
        return null;
    }

    public void deleteAccount(Integer id){
        accountRepository.deleteById(id);
    }

    public Account getAccountByUsername(String username){
        return accountRepository.findByUsername(username);
    }
}
