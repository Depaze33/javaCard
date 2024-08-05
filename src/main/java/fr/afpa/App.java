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
        final ContactBinaryManager manager = new ContactBinaryManager();

        try {
            // Save contact
            String filePath = "contacts.ser";
            manager.saveList(filePath, contacts);

            // Chargement contact vérification

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Chargement de la liste des contacts
    public static ArrayList<Contact> deserializerMethod() throws IOException, ClassNotFoundException {
        try {
            ContactBinaryManager manager = new ContactBinaryManager();
            String filePath = "contacts.ser";
            ArrayList<Contact> loadedContacts = manager.loadList(filePath);

            return loadedContacts;
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }
}
