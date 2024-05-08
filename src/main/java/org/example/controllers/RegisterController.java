package org.example.controllers;

import org.example.RegisterService;
import org.example.models.Book;
import org.example.repo.BookRepository;
import org.example.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

import org.example.models.User;


import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    RegisterService rs;

    @Autowired
    UserRepository ur;
    @GetMapping("/register")
    public String register1(@ModelAttribute("u") User u){
        return "register";
    }
    @PostMapping("/register")
    public String register2(@ModelAttribute("u") @Valid User u, BindingResult br){
        /*try {
            ur.save(new User(u.getName(),u.getPass()));
        }catch(Throwable t){
            t.printStackTrace();
        }*/
        rs.saveUser(new User(u.getName(),u.getPass()));
        return "redirect:login";
    }
}
