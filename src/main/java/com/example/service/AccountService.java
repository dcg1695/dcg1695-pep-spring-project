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
        List<Account> listOfAccounts = accountRepository.findAll();
        for (Account checkedAccount : listOfAccounts){
            if (checkedAccount.getUsername().equals(account.getUsername())){
                return null;
            }
        }
        return accountRepository.save(account);
    }

    public Account verifyAccount(Account account){
        List<Account> listOfAccounts = accountRepository.findAll();
        for (Account checkedAccount : listOfAccounts){
            if (checkedAccount.getUsername().equals(account.getUsername()) && checkedAccount.getPassword().equals(account.getPassword())){
                return checkedAccount;
            }
        }
        return null;
    }

    public void deleteAccount(Integer id){
        Optional<Account> checkAccount = accountRepository.findById(id);
        if (checkAccount.isPresent()) accountRepository.deleteById(id);
    }
}
