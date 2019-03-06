package com.example.testapp.Model;

import com.example.testapp.Model.Pet;

import java.util.List;

public class PetResponse {
    private String message;
    private List<Pet> data;

    public List<Pet> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}