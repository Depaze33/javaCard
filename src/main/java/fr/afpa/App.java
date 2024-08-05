package fr.afpa;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadListener;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("contactList"));

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        ArrayList<Contact> contacts = new ArrayList<>();
        // contacts.add(new Contact("MICHEL", "Henry", "M", LocalDate.of(1965, 5, 25),
        // "Mimi", "0656232524", "0556324148", "michel.henry@gmail.com",
        // "23 Rue des Poules 33150 Cenon", "https://github.com/MichelHenry"));
        // contacts.add(new Contact("PAUL", "Geraldine", "F", LocalDate.of(1980, 6, 10),
        // "Gege", "0656232524", "0556324148", "geraldine.paul@gmail.com",
        // "50 Rue du Puche 33520 ", "https://github.com/PaulGeraldine"));
        // contacts.add(new Contact("ZARYA", "Emy", "N-B", LocalDate.of(2002, 5, 20),
        // "Em", "0656232524", "0556324148", "Emy.du33@gmail.com",
        // "50 Rue du Puche 33520 ", "https://github.com/Emy33"));

        serializerMethode(contacts);
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
            // System.out.println("Contacts loaded for verification: " + loadedContacts.size());
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
}
