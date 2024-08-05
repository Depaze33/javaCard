package fr.afpa;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private Label lastNameLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label genderLabel;

    @FXML
    private Label birthDayLabel;

  
    @FXML
    private Label personalPhoneLabel;

    @FXML
    private Label mailLabel;

    @FXML
    private Label postalAdressLabel;
    
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
    private TextField birthDayTextField;
    @FXML
    private ComboBox<String> comboBoxGender;

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

    @FXML
    private VBox vBoxErrors;

    public void initialize() {

        // ONLY DEBUG CODE
        birthDayTextField.setText("2021-01-01");

        ////

        // hide the edit button
        buttonEdit.setVisible(false);
        buttonDelete.setVisible(false);

        // add elemetns to gender ComboBox
        ObservableList<String> genders = FXCollections.observableArrayList();
        genders.add("M");
        genders.add("F");
        genders.add("NB");
        comboBoxGender.setItems(genders);
        comboBoxGender.getSelectionModel().selectFirst();
    }

    // Methode save contact
    public void persistContact() throws ClassNotFoundException, IOException {
        // get all the information from the fields
        // declaration of variables for each information

        StringBuilder errorMessages = new StringBuilder();
        String lastName = lastNameTextField.getText();

        String firstName = firstNameTextField.getText();
        String gender = comboBoxGender.getPromptText();
        LocalDate birthDate = LocalDate.now();
        String pseudo = pseudoTextField.getText();
        String personnalNumber = personalNumberTextField.getText();
        String professionalNumber = professionalTextField.getText();
        String mailAddress = mailAddressTextField.getText();
        String postalAddress = postalAddressTextField.getText();
        String git = gitTextField.getText();

        // Reset error message if validation passes

        boolean error = false;
        // regex and required fields
        if (lastName.trim().isEmpty()) {
            lastNameLabel.getStyleClass().add("labelRequired");
            error = true;
        }

        if (firstName.trim().isEmpty()) {
            firstNameLabel.getStyleClass().add("labelRequired");
            error = true;
        }


        if (birthDate == null) {
            // La date est null
            birthDayLabel.getStyleClass().add("labelRequired");
        }
       
        if (personnalNumber.trim().isEmpty()) {
            personalPhoneLabel.getStyleClass().add("labelRequired");
            error = true;
        }
        if (mailAddress.trim().isEmpty()) {
            mailLabel.getStyleClass().add("labelRequired");
            error = true;
        }

        if (postalAddress.trim().isEmpty()) {
            postalAdressLabel.getStyleClass().add("labelRequired");
            error = true;
        }

    

        if (!error) {
            // instanciation of a new Contact object
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
            App.setRoot("contactList");
            System.out.println("Contact saved: " + newContact);
        }
    }
}
