package fr.afpa;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;
    public static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        scene = new Scene(loadFXML("contactList"));

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        // Fixtures.generateFixtures();
        launch();
    }

    static void setRoot(final String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(final String fxml) throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void serializerMethode(ArrayList<Contact> contacts) {
        final ContactBinaryManager binaryManager = new ContactBinaryManager();
        final ContactVCardSerializer vCardSerializer = new ContactVCardSerializer();

        try {
            // Save contacts to binary file
            String binaryFilePath = "contacts.ser";
            binaryManager.saveList(binaryFilePath, contacts);

            // Save contacts to VCard file
            String vCardFilePath = "contacts.vcf";
            vCardSerializer.saveList(vCardFilePath, contacts);

            // Load contacts for verification
            // ArrayList<Contact> loadedContacts = deserializerMethod();
            // System.out.println("Contacts loaded for verification: " +
            // loadedContacts.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to deserialize contacts
    public static ArrayList<Contact> deserializerMethod() throws IOException, ClassNotFoundException {
        try {
            ContactBinaryManager manager = new ContactBinaryManager();
            String filePath = "contacts.ser";
            ArrayList<Contact> loadedContacts = manager.loadList(filePath);

            return loadedContacts;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Save a single contact as VCard
    public static void saveOneContactVCard(Contact contact, String filePath) {
        ContactVCardSerializer vCardSerializer = new ContactVCardSerializer();
        try {
            vCardSerializer.save(filePath, contact);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save a list of contacts as VCard
    public static void saveContactsAsVCard(ArrayList<Contact> contacts, String filePath) {
        ContactVCardSerializer vCardSerializer = new ContactVCardSerializer();
        try {
            vCardSerializer.saveList(filePath, contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void serializerMethode(Contact contact, String filePath) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'serializerMethode'");
    }
}