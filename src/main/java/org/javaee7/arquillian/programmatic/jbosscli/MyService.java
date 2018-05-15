package org.javaee7.arquillian.programmatic.jbosscli;


import javax.annotation.Resource;
import javax.inject.Named;


@Named
public class MyService {

    @Resource(lookup = "global/a")
    private int a;

    public int getA() {
        return a;
    }
}
