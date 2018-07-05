package com.moheqionglin.message;

import java.util.Date;

public class Book {

    private Long id;
    private String name;
    private String author;
    private Date time;

    public Book() {
    }

    public Book(Long id, String name, String author, Date time) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", time=" + time +
                '}';
    }
}
