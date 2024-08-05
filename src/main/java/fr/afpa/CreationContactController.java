package fr.afpa;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class CreationContactController {

    // implements FXML attributs
    @FXML
    private VBox centerPane;

    @FXML
    private HBox modificationContactBox;

    @FXML
    private VBox contactInfosVBox;
    @FXML
    private GridPane contactGridPane;

    @FXML
    private GridPane buttonGridPane;

    @FXML
    private Button buttonExportJson;

    @FXML
    private Button buttonExportVCard;

    @FXML
    private Button buttonSave;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonDelete;

    @FXML
    private Label requiredLabel;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private MenuButton genderMenuButton;

    @FXML
    private TextField birthDayTextField;

    @FXML
    private TextField pseudoTextField;

    
    @FXML
    private TextField personalNumberTextField;

    @FXML
    private TextField professionalTextField;

    @FXML
    private TextField mailAddressTextField;

    @FXML
    private TextField postalAddressTextField;

    @FXML
    private TextField gitTextField;

    @FXML
    private Button saveButton;

    // Methode save contact
    public boolean persistContact() throws ClassNotFoundException, IOException {
        String lastName = lastNameTextField.getText();
        String firstName = firstNameTextField.getText();
        String gender = genderMenuButton.getText();
        LocalDate birthDate = LocalDate.parse(birthDayTextField.getText(), 
        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String pseudo = pseudoTextField.getText();
        String personnalNumber = personalNumberTextField.getText();
        String professionalNumber = professionalTextField.getText();
        String mailAddress = mailAddressTextField.getText();
        String postalAddress = postalAddressTextField.getText();
        String git = gitTextField.getText();

        Contact newContact = new Contact(
                lastName,
                firstName,
                gender,
                birthDate,
                pseudo,
                personnalNumber,
                professionalNumber,
                mailAddress,
                postalAddress,
                git);

        // Load existing contacts
        ArrayList<Contact> contacts = App.deserializerMethod();

        // Add the new contact to the list
        contacts.add(newContact);

        System.out.println(contacts);

        // Save the updated list of contacts
        App.serializerMethode(contacts);

        System.out.println("Contact saved: " + newContact);

        return false;

    }
}