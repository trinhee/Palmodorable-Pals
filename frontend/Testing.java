package frontend;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import backend.*;
public class Testing {
    public static void main(String[] args) {
        PetsDictionary petsDictionary = new PetsDictionary();
        Settings settings = Settings.getInstance();
        System.out.println("Settings loaded: " + settings);
        System.out.println("Pets loaded: " + petsDictionary);
        Pet pet = petsDictionary.getPetByName("Buddy");
        System.out.println(pet.getPetType());
    }
}