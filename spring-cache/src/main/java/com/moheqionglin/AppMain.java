package com.moheqionglin;

import com.moheqionglin.message.Animal;
import com.moheqionglin.message.Book;
import com.moheqionglin.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

public class AppMain {


    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(EcacheConfig.class);

        BookService bookService = (BookService) applicationContext.getBean("bookService");

        Book book3 = generateRandomBook(3l);
        for(long i = 1; i < 10000; i ++){
            bookService.createBook(generateRandomBook(i));
        }
        bookService.queryBook(book3);
    }

    public static Book generateRandomBook(Long id){
        return new Book(id, "name-" + id, "author-" + id, new Date());
    }

    public static Animal generateRandomAnimal(Long id){
        return new Animal("name-" + id, "color-" + id, id);
    }
}
