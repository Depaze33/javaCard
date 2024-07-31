package fr.afpa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import fr.afpa.Controller.ContactBinaryManager;

public class App extends Application {

    private static Scene scene;
    private static ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @SuppressWarnings("rawtypes")
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        // Adding a sample contact to the list
        contacts.add(new Contact("Michel", "Henry", "M", LocalDate.of(1965, 5, 25),
                "Mimi", "0656232524", "0556324148", "michel.henry@gmail.com",
                "23 Rue des Poules 33150 Cenon", "https://github.com/Depaze33", "MH"));

        // Serializing and deserializing to demonstrate functionality
        ContactBinaryManager manager = new ContactBinaryManager(null);

        String filePath = "contact.ser";

        try {
            // Save the first contact
            manager.save(filePath, contacts.get(0));
            System.out.println("Contact saved successfully.");
        launch();
    }catch (IOException e) {
        System.err.println("Error during serialization/deserialization: " + e.getMessage());
        e.printStackTrace();
}}}
