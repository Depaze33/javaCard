package fr.afpa;

import java.io.IOException;
import java.util.ArrayList;

public interface Serializer<T> {
void saveList(String filePath, ArrayList<T> objectToSave) throws IOException;

    
    void save(String filePath, T objectToSave) throws IOException;
    
    
} 
