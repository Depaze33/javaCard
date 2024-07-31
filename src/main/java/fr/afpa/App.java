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

    @SuppressWarnings("rawtypes")
    public static void main(final String[] args) throws ClassNotFoundException, IOException {

        // Adding a sample contact to the list
        contacts.add(new Contact("MICHEL", "Henry", "M", LocalDate.of(1965, 5, 25),
                "Mimi", "0656232524", "0556324148", "michel.henry@gmail.com",
                "23 Rue des Poules 33150 Cenon", "https://github.com/MichelHenry"));
        contacts.add(new Contact("PAUL", "Geraldine", "F", LocalDate.of(1980, 6, 10),
                "Gege", "0656232524", "0556324148", "geraldine.paul@gmail.com",
                "50 Rue du Puche 33520 ", "https://github.com/PaulGeraldine"));
        contacts.add(new Contact("ZARYA", "Emy", "N-B", LocalDate.of(2002, 5, 20),
                "Em", "0656232524", "0556324148", "Emy.du33@gmail.com",
                "50 Rue du Puche 33520 ", "https://github.com/Emy33"));

        System.out.println(contacts);

        // Serializing and deserializing to demonstrate functionality
        final ContactBinaryManager manager = new ContactBinaryManager(null);

        try {
            // Sauvegarde des contact
            String filePath = "contact.ser";
            manager.saveList(filePath, contacts);

            // Chargement du contact pour vérification

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Sauvegarde du contact
            String filePath = "contact.ser";
            manager.save(filePath, (new Contact("MICHEL", "Henry", "M", LocalDate.of(1965, 5, 25),
                    "Mimi", "0656232524", "0556324148", "michel.henry@gmail.com",
                    "23 Rue des Poules 33150 Cenon", "https://github.com/MichelHenry")));

            // Chargement du contact pour vérification

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
