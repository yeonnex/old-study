package com.example.designpatterns._05_prototype.java;

import java.util.ArrayList;
import java.util.List;

public class JavaCollectionExample {
    public static void main(String[] args) {
        Student seoyeon = new Student("seoyeon");
        Student somang = new Student("somang");

        List<Student> students = new ArrayList<>();
        students.add(seoyeon);
        students.add(somang);

        List<Student> shallowCopy = new ArrayList<>(students); // 이 방법을 실무에서 clone 보다 많이 씀


    }
}
