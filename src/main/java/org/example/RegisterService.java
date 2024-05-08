package org.example;

import org.example.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.example.models.User;

import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {
    @Autowired
    UserRepository ur;
    @Transactional
    public void saveUser(User u){
        ur.save(new User(u.getName(),u.getPass()));
    }
}
