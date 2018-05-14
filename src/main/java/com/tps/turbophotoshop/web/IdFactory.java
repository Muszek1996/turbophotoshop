package com.tps.turbophotoshop.web;

public class IdFactory {
    static int id = 999;


    static public String getId(){
        return String.valueOf(increment());
    }

    static private int increment(){
        return ++id;
    }

    static private int decrement(){
        return --id;
    }

}
