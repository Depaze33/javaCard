package fr.afpa.Controller;

import java.io.IOException;
import java.util.ArrayList;

public interface SerialisationInterface<T> {
void saveList(String filePath, ArrayList<T> objectToSave) throws IOException;

    
    void save(String filePath, T objectToSave) throws IOException;
    
    
} 
