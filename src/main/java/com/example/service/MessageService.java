package com.example.service;

import org.springframework.stereotype.Service;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.entity.Message;
import com.example.entity.Account;
import java.util.*;

@Service
@Transactional
public class MessageService {
    
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    AccountRepository accountRepository;

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        return optionalMessage.orElse(null);
    }

    public List<Message> getMessagesByAccountId(Integer accountId){
        List<Message> listOfMessages = messageRepository.findAll();
        List<Message> listOfUpdatedMessages = new ArrayList();
        for (Message message : listOfMessages){
            if (message.getPostedBy().equals(accountId)){
                listOfUpdatedMessages.add(message);
            }
        }
        return listOfUpdatedMessages;
    }

    public Message createMessage(Message message){
        if (message.getMessageText().trim().isEmpty() || message.getMessageText().length() > 255){
            return null;
        }
        
        List<Account> accountList = accountRepository.findAll();
        for (Account checkedAccount : accountList){
            if (checkedAccount.getAccountId().equals(message.getPostedBy())){
                return messageRepository.save(message);
            }
        }
        
        return null;
    }

    public Message updateMessage(int messageId, Message message){
        if (message.getMessageText().trim().isEmpty() || message.getMessageText().length() > 255){
            return null;
        }

        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()){
            Message updatedMessage = optionalMessage.get();
            updatedMessage.setMessageText(message.getMessageText());
            return messageRepository.save(updatedMessage);
        }
        return null;
    }

    public Message deleteMessage(int messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()){
            Message deletedMessage = optionalMessage.get();
            messageRepository.deleteById(messageId);
            return deletedMessage;
        }

        return null;
    }
}
