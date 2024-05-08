package org.example.controllers;

import org.example.models.Book;
import org.example.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class MyController {
    @Autowired
    private BookRepository bookRepository;
    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String homePage(Model model) {
        //
        try {
            bookRepository.save(new Book("title","a1"));
        }catch(Throwable t){
            t.printStackTrace();
        }
        //
        model.addAttribute("appName", appName);
        return "home";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        return "logout";
    }/**/
    @GetMapping("/book/{book}")
    public String book(Model model,@PathVariable String book) {
        Optional<Book> b=bookRepository.findById(Integer.parseInt(book));//
        try {
            System.out.println(bookRepository==null);
            System.out.println(">>>"+bookRepository.count());
            model.addAttribute("book", b.orElse(new Book("<empty>")) .getTitle());
        }catch(Throwable t){
            t.printStackTrace();
            model.addAttribute("not found", book);
        }
        return "book";
    }
    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin";
    }/**/


/*
    @GetMapping("/logout")
    public String logout(Model model) {
        return "";
    }*/


}
