package fr.afpa;

import java.util.ArrayList;

public interface Deserializer<T> {
//Methode to Deserializaiton
    ArrayList<T> loadList(String filPath);

    T load(String filePath);

}
