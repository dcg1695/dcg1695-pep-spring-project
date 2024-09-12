package com.example.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.entity.Account;
import com.example.entity.Message;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
@RequestMapping("")
public class SocialMediaController {
    
    @Autowired
    public AccountService accountService;
    
    @Autowired
    public MessageService messageService;

    // Registering account (POST)
    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> registerHandler(@RequestBody Account account){
        if (account.getUsername().trim().isEmpty() || account.getPassword().length() < 4){
            return ResponseEntity.status(400).body(null);
        }

        Account newAccount = accountService.createAccount(account);
        if (newAccount == null) return ResponseEntity.status(409).body(null);
        else return ResponseEntity.status(200).body(newAccount);
    }

    // Logging into account (POST)
    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> loginHandler(@RequestBody Account account){
        Account verifiedAccount = accountService.verifyAccount(account);
        if (verifiedAccount == null) return ResponseEntity.status(401).body(null);
        else return ResponseEntity.status(200).body(verifiedAccount);
    }

    // Creating new message (POST)
    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> createMessageHandler(@RequestBody Message message){
        Message postMessage = messageService.createMessage(message);
        if (postMessage == null) return ResponseEntity.status(400).build();
        else return ResponseEntity.status(200).body(postMessage);
    }

    // Getting all messages (GET)
    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessageHandler(){
        List<Message> listOfMessages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(listOfMessages);
    }

    // Getting a message by message id (GET)
    @GetMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Message> getMessageByIdHandler(@PathVariable int messageId){
        Message messageById = messageService.getMessageById(messageId);
        if (messageById == null) return ResponseEntity.status(200).build();
        else return ResponseEntity.status(200).body(messageById);
    }

    // Deleting a message (DELETE)
    @DeleteMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Integer> deleteMessageHandler(@PathVariable int messageId){
        Message deletedMessage = messageService.deleteMessage(messageId);
        if (deletedMessage == null) return ResponseEntity.status(200).body(0);
        else{
            String[] lines = deletedMessage.getMessageText().split("\r|\n");
            return ResponseEntity.status(200).body(lines.length);
        }
    }

    // Updating a message (PATCH)
    @PatchMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Integer> updateMessageHandler(@PathVariable int messageId, @RequestBody Message updatedMessage){
        Message message = messageService.updateMessage(messageId, updatedMessage);
        if (message == null) return ResponseEntity.status(400).body(0);
        else{
            String[] lines = message.getMessageText().split("\r|\n");
            return ResponseEntity.status(200).body(lines.length);
        }
    }

    // Getting messages by account id (GET)
    @GetMapping("/accounts/{accountId}/messages")
    public @ResponseBody ResponseEntity<List<Message>> getMessagesByAccountIdHandler(@PathVariable int accountId){
        List<Message> listofMessages = messageService.getMessagesByAccountId(accountId);
        return ResponseEntity.status(200).body(listofMessages);
    }
}
