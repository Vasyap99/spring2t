package org.example.models;

import javax.persistence.*;
//import jakarta.persistence.*;

@Entity
public class Book {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String getTitle() {
        return title;
    }

    @Column
    private String title;

    @Column
    private String author;

    public Book(){}
    public Book(String title){
        this.title=title;
    }
    public Book(String title,String auth){
        this.title=title;
        author=auth;
    }
}
