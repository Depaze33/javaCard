package fr.afpa;

import java.io.IOException;
import java.util.ArrayList;

public interface Serializer<T> {
    //Methode for serialization
    void saveList(String filePath, ArrayList<T> objectToSave) throws IOException;

    void save(String filePath, T objectToSave) throws IOException;

}
