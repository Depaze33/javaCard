package fr.afpa;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;
    private static ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    public void start(final Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(final String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(final String fxml) throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void main(final String[] args) throws ClassNotFoundException, IOException {

        contacts.add(new Contact("MICHEL", "Henry", "M", LocalDate.of(1965, 5, 25),
        "Mimi", "0656232524", "0556324148", "michel.henry@gmail.com",
        "23 Rue des Poules 33150 Cenon", "https://github.com/MichelHenry"));
        contacts.add(new Contact("PAUL", "Geraldine", "F", LocalDate.of(1980, 6, 10),
        "Gege", "0656232524", "0556324148", "geraldine.paul@gmail.com",
        "50 Rue du Puche 33520 ", "https://github.com/PaulGeraldine"));
        contacts.add(new Contact("ZARYA", "Emy", "N-B", LocalDate.of(2002, 5, 20),
        "Em", "0656232524", "0556324148", "Emy.du33@gmail.com",
        "50 Rue du Puche 33520 ", "https://github.com/Emy33"));
App.serializerMethode();
App.deserializerMethod();
        // Adding a sample contact to the list
       

        // System.out.println(contacts);

        // Serializing to demonstrate functionality

    }

    public static void serializerMethode() {
        final ContactBinaryManager manager = new ContactBinaryManager();

        try {
        // Sauvegarde des contact
        String filePath = "contacts.ser";
        manager.saveList(filePath, contacts);

        // Chargement contact vérification

        } catch (IOException e) {
        e.printStackTrace();
        }
    }

   
        // Chargement de la liste des contacts
        public static void deserializerMethod() throws IOException, ClassNotFoundException {
            ContactBinaryManager manager = new ContactBinaryManager();
            String filePath = "contacts.ser";
            ArrayList<Contact> loadedContacts = manager.loadList(filePath);
            System.out.println("Loaded contacts: " + loadedContacts);
        }
    }

