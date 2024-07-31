package fr.afpa.Controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import fr.afpa.Contact;

public class ContactBinaryManager<T> implements SerialisationInterface{

    private Contact contact;



    

    public ContactBinaryManager(Contact contact) {
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
    

/**
 * 
 * @param filePath 
 * @param objectToSave
 */
    @Override
    public  void saveList(String filePath, ArrayList<T> objectToSave)  {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(objectToSave);
            System.out.println("Serialized data is saved in " + filePath);
        }catch(IOException exception){
            System.out.println("Erreur lors de la sérialisation : " + exception.getMessage());

        }
       
    }
/**
 * 
 */
    @Override
    public void save(String filePath, Object T) {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(T);
            System.out.println("Serialized data is saved in " + filePath);
        }catch(IOException exception){
            System.out.println("Erreur lors de la sérialisation : " + exception.getMessage());

        }
       

    
    }}