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
        Fixtures.generateFixtures();
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

        try {
            // Save contacts to binary file
            String binaryFilePath = "contacts.ser";
            binaryManager.saveList(binaryFilePath, contacts);

        } catch (IOException e) {
            e.printStackTrace();
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
        throw new UnsupportedOperationException("Unimplemented method 'serializerMethode'");
    }

    public static boolean saveContactJson(Contact contact, String filepath) throws IOException{
        ContactJsonSerialiazer jsonSerializer = new ContactJsonSerialiazer();
        jsonSerializer.save(filepath, contact);
        return true;
    }
}