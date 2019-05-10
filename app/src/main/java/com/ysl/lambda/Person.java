package com.ysl.lambda;

import java.time.temporal.Temporal;
import java.util.Comparator;

public class Person {
    private String name;
    private int age;
    private double score;


    public Person() {
    }

    public Person(String name, int age, double score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }

    public static void main(String[] args) {
        Person person1 = new Person("s", 12, 3.0);
        Person person2 = new Person("s", 78, 56.0);

        test1();
        test2();
    }

    public static void test1() {
        //实现接口，比较两个的age大小
        PersonInterface<Person> lamdba3Interface =(p1,p2) -> p1.getAge().compareTo(p2.getAge());
        System.out.println(lamdba3Interface);
    }
    public static void test2() {
        //实现接口，比较两个的score大小
        PersonInterface<Person> lamdba3Interface =(p1,p2) -> p1.getScore().compareTo(p2.getScore());
        System.out.println(lamdba3Interface);
    }
}
