package fr.afpa;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ContactBinaryManager<T> implements Serializer<Contact>, Deserializer<Contact> {

    public ContactBinaryManager() {
    }

    /**
     * 
     * @param filePath
     * @param objectToSave
     */
    ////Methode serialize contact list
    @Override
    public void saveList(String filePath, ArrayList<Contact> objectToSave) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(objectToSave);
            System.out.println("Serialized list of data is saved in " + filePath);
        } catch (IOException e) {
            System.out.println("Error during serialization: " + e.getMessage());
            throw e;
        }
    }
//Methode Deserialize just one contact 
    @Override
    public void save(String filePath, Contact objectToSave) {
        throw new UnsupportedOperationException("Pas besoin de cette méthode pour le moment");

    }
/**
     * 
     * @param filePath
     * 
     */ 
    //Methode Deserialize contact list
    @Override
    public ArrayList<Contact> loadList(String filePath) {
        try (FileInputStream fileIn = new FileInputStream(filePath);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (ArrayList<Contact>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error during deserialization: " + e.getMessage());
            try {
                throw e;
            } catch (Exception e1) {

                e1.printStackTrace();
            }
        }
        return null;
    }
 

     // Methode Deserialisation just one contact
    @Override
    public Contact load(String filePath) {
        throw new UnsupportedOperationException("Pas besoin de cette méthode pour le moment");

    }

}