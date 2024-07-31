package fr.afpa;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ContactBinaryManager implements Serializer<Contact> {

    

   

    public ContactBinaryManager(Object object) {
        //TODO Auto-generated constructor stub
    }

    /**
     * 
     * @param filePath
     * @param objectToSave
     */
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

    @Override
    public void save(String filePath, Contact objectToSave) throws IOException {
        // try (FileOutputStream fileOut = new FileOutputStream(filePath);
        // ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
        // out.writeObject(objectToSave);
        // System.out.println("Serialized data is saved in " + filePath);
        // } catch (IOException exception) {
        // System.out.println("Error during serialization: " + exception.getMessage());
        // throw exception;
        // }
    }

}