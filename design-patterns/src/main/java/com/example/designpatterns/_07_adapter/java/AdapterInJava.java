package com.example.designpatterns._07_adapter.java;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class AdapterInJava {
    public static void main(String[] args) {
        // collections
        List<String> strings = Arrays.asList("a", "b", "c"); // 배열을 인자로 넘겨주었지만, 리스트로 반환하여 리턴
        Enumeration<String> enumeration  = Collections.enumeration(strings);

    }
}
