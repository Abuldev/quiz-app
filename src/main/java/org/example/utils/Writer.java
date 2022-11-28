package org.example.utils;


import org.example.domain.Domain;
import org.example.response.Data;
import org.example.response.ResponseEntity;

import java.util.List;

import static org.example.utils.Color.RESET;

public class Writer<T extends Domain> {

    public static void print(Object obj, String color){
        System.out.print(obj + color + RESET);
    }
    public static void print(Object obj, String color, String bg){
        System.out.print(obj + bg + color + RESET);
    }

    public static void println(Object obj, String color){
        System.out.println(color + obj + RESET);
    }
    public static void println(Object obj){
        System.out.println(obj);
    }

    public void print(ResponseEntity<Data<List<T>>> all){

        all.getData().getBody().forEach(s-> println(s,Color.BLUE));
    }
    public static void println(){
        System.out.println();
    }
}
