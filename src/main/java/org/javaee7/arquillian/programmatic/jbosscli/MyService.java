package org.javaee7.arquillian.programmatic.jbosscli;


import javax.inject.Named;


@Named
public class MyService {

    public String getAll() {
        System.out.println("GET ALL");
        return Database.getAll();
    }


    public String getByName(String payload) {
        System.out.println("GET BY NAME");
        return Database.get(payload);
    }

    public void add(String payload) {
        System.out.println("ADD");
        Database.add(payload);
    }


    public void put(String payload) {
        System.out.println("PUT");
        Database.add(payload);
    }


    public void delete(String payload) {
        System.out.println("DELETE");
        Database.delete(payload);
    }
}
