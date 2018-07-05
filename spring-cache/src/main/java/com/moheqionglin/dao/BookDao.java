package com.moheqionglin.dao;

import com.moheqionglin.message.Book;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao {

    public void createBook(Book book){
//        System.out.println("不走缓存->create book " + book);
    }

    public void deleteBook(Book book){
//        System.out.println("delete book " + book);
    }

    public void updateBook(Book book){
//        System.out.println("update book " + book);
    }

    public Book queryBook(Book book){
        System.out.println("从数据库查询数据 query book " + book);
        return book;
    }
}
