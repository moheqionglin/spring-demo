package com.moheqionglin.service;


import com.moheqionglin.message.Animal;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class AnimalService {

    @Cacheable(value = "animal", key = "#animal.id")
    public void createAnimal(Animal animal){
        System.out.println("create animal " + animal);
    }

    @Cacheable(value = "animal", key = "#animal.id")
    public void deleteAnimal(Animal animal){
        System.out.println("delete animal " + animal);
    }

    @Cacheable(value = "animal", key = "#animal.id")
    public void updateAnimal(Animal animal){
        System.out.println("update animal " + animal);
    }

    @Cacheable(value = "animal", key = "#animal.id")
    public void queryAnimal(Animal animal){
        System.out.println("query animal " + animal);
    }
}
