package org.javaee7.arquillian.programmatic.jbosscli;

import java.util.Arrays;
import java.util.List;

public class Database {
    private static final List<String> list = Arrays.asList("Orange","Banana","Apple");


    static public String getAll() {
        return list.toString();
    }

    static public String get(String fruit) {
        return list.contains(fruit) ? fruit : "";
    }

    static public void add(String fruit) {
        list.add(fruit);
    }

    static public void delete(String fruit) {
        list.remove(fruit);
    }
}
