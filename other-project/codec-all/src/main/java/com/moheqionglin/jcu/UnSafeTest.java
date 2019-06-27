package com.moheqionglin.jcu;

import sun.misc.Unsafe;
import sun.reflect.Reflection;

import java.lang.reflect.Field;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-04-26 09:57
 */
public class UnSafeTest {
    static Unsafe unsafe = getUnsafe();

    static long personStaticFieldCountOffset;
    static long personAgeOffset;
    static long personMoneyOffset;
    static long personNameOffset;
    static long personAddressOffset;
    static long addressAddressOffset;

    static {
        try {
            personStaticFieldCountOffset = unsafe.staticFieldOffset(Person.class.getDeclaredField("personCount"));
            personAgeOffset = unsafe.objectFieldOffset(Person.class.getDeclaredField("age"));
            personMoneyOffset = unsafe.objectFieldOffset(Person.class.getDeclaredField("money"));
            personNameOffset = unsafe.objectFieldOffset(Person.class.getDeclaredField("name"));
            personAddressOffset = unsafe.objectFieldOffset(Person.class.getDeclaredField("address"));
            addressAddressOffset = unsafe.objectFieldOffset(Address.class.getDeclaredField("address"));
            System.out.println("personStaticFieldCountOffset=" + personStaticFieldCountOffset +
                    ", personAgeOffset=" + personAgeOffset +
                    ", personMoneyOffset=" + personMoneyOffset +
                    ", personNameOffset=" + personNameOffset +
                    ", personAddressOffset=" + personAddressOffset +
                    ", addressAddressOffset=" + addressAddressOffset);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

        Address address = new Address("上海");
        Person person = new Person(10, 100L, "万里", address);
        System.out.println(person);
        unsafe.compareAndSwapInt(person, personAgeOffset, 10, 11);
        unsafe.compareAndSwapLong(person, personMoneyOffset, 100, 13);
        unsafe.compareAndSwapObject(person, personAddressOffset, address, new Address("北上海"));
        System.out.println(person);
    }


    public static Unsafe getUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    static class Person{
        private static int personCount;
        private int age;
        private long money;
        private String name;
        private Address address;


        public Person(int age, long money, String name, Address address) {
            Class var0 = Reflection.getCallerClass();
            System.out.println(var0 + " " + var0.getClassLoader());
            this.age = age;
            this.money = money;
            this.name = name;
            this.address = address;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public long getMoney() {
            return money;
        }

        public void setMoney(long money) {
            this.money = money;
        }

        public static int getPersonCount() {
            return personCount;
        }

        public static void setPersonCount(int personCount) {
            Person.personCount = personCount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    ", money=" + money +
                    ", name='" + name + '\'' +
                    ", address=" + address +
                    '}';
        }
    }
    static class Address{
        private String address;

        @Override
        public String toString() {
            return "Address{" +
                    "address='" + address + '\'' +
                    '}';
        }

        public Address(String address) {
            this.address = address;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}