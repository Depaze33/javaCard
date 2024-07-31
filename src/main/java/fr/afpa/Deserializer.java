package fr.afpa;

import java.util.ArrayList;

public interface Deserializer<T> {
    
    ArrayList<T> loadList(String filPath);
    T load(String filePath);

}
