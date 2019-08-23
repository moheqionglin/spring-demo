package com.moheqionglin.alth.LRU;

import java.util.HashMap;

public class LruDemo<T> {
    private Object[] items;
    private HashMap<T, Integer> map;
    private int size;
    private int index;

    public LruDemo() {
        this(8);
    }

    public LruDemo(int size) {
        this.size = size;
        this.items = new Object[size];
        this.map = new HashMap<>(16);
        this.index = 0;
    }

    public void put(T t) {
        Integer value = map.get(t);
        if (value == null) {
            if (index >= size) {
                // 满了，需要移除第一个元素
                for (int i = 1; i < size; i++) {
                    items[i - 1] = items[i];
                    map.replace((T) items[i - 1], i);
                }
                index -= 1;
            }
            items[index] = t;
            map.put(t, index);
            index += 1;
        } else {
            for (int i = value; i < index; i++) {
                items[i] = items[i + 1];
                map.replace((T) items[i], i);
            }
            items[index - 1] = t;
            map.replace(t, index - 1);
        }
    }

    public void getAll() {
        for (int i = 0; i < index; i++) {
            System.out.println(items[i]);
        }
        System.out.println("======");
    }

    public static void main(String[] args) {
        LruDemo<String> lruDemo = new LruDemo<String>(6);
        lruDemo.put("aliace");
        lruDemo.put("bob");
        lruDemo.put("cat");
        lruDemo.put("dog");
        lruDemo.put("egg");
        lruDemo.getAll();
        lruDemo.put("bob");
        lruDemo.getAll();
        lruDemo.put("fine");
        lruDemo.put("good");
        lruDemo.getAll();
    }
}