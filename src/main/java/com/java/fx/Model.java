package com.java.fx;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Model{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name, phone;
    @Override
    public String toString(){
        return "ID: "+id+"   -   Name: "+name+"   -   Phone: "+phone;
    }
}
