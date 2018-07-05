package com.moheqionglin.service;


import com.moheqionglin.dao.BookDao;
import com.moheqionglin.message.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class BookService {

    @Autowired
    private BookDao bookDao;

    @Cacheable(value = "book", key = "#book.id")
    public void createBook(Book book){
        System.out.println("create book " + book);
    }

    @Cacheable(value = "book", key = "#book.id")
    public void deleteBook(Book book){
        System.out.println("delete book " + book);
    }

    @Cacheable(value = "book", key = "#book.id")
    public void updateBook(Book book){
        System.out.println("update book " + book);

    }

    @Cacheable(value = "book", key = "#book.id")
    public Book queryBook(Book book){
        System.out.println("query book " + book);
        return bookDao.queryBook(book);
    }
}
