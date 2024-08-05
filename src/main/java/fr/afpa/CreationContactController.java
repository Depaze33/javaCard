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
    private Label proffesionalPhoneLabel;

    @FXML
    private Label mailLabel;

    @FXML
    private Label postalAdressLabel;

    @FXML
    private Label gitLinkLabel;

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
        firstNameTextField.setText("Marco");
        lastNameTextField.setText("Marco");
        birthDayTextField.setText("2021-01-01");
        // personalNumberTextField.setText(0224452710);
        mailAddressTextField.setText("clarisse@gmail.com");
        postalAddressTextField.setText("32 rue du marchal Galienni 33150 Cenon");
        gitTextField.setText("https://github.com/Depaze33");

        ////

        // hide the edit button

        // buttonExportVCard.setVisible(false);
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
        String personalNumber = personalNumberTextField.getText();
        String professionalNumber = professionalTextField.getText();
        String mailAddress = mailAddressTextField.getText();
        String postalAddress = postalAddressTextField.getText();
        String git = gitTextField.getText();

        // Reset error message if validation passes

        boolean error = false;
        // regex and required fields
        if (lastName.trim().isEmpty() || !lastName.matches("^[a-zA-Z\\s]+$")) {
            lastNameLabel.getStyleClass().add("labelRequired");
            error = true;
        }

        if (firstName.trim().isEmpty() || !firstName.matches("^[a-zA-Z\\s]+$")) {
            firstNameLabel.getStyleClass().add("labelRequired");
            error = true;
        }

        // if (birthDate == null || !birthDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
        // // The date is null or doesn't match the format YYYY-MM-DD
        // birthDayLabel.getStyleClass().add("labelRequired");
        // }

        if (personalNumber.trim().isEmpty() ||
                !personalNumber.matches(
                        "^(\\+?\\d{1,3}[-.\\s]?)?\\(?\\d{1,4}\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$")) {
            personalPhoneLabel.getStyleClass().add("labelRequired");
            error = true;
        }

        if (!professionalNumber.trim().isEmpty() &&!professionalNumber.matches(
            "^(\\+?\\d{1,3}[-.\\s]?)?\\(?\\d{1,4}\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$")) {
            proffesionalPhoneLabel.getStyleClass().add("labelRequired");
            error = true;
        }

        if (mailAddress.trim().isEmpty() &&
                !mailAddress.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            mailLabel.getStyleClass().add("labelRequired");
            error = true;
        }

        if (postalAddress.trim().isEmpty() ||
                !postalAddress.matches(
                        "^\\d+\\s[A-Za-z0-9\\s]+(?:\\s[A-Za-z0-9\\s]+)*(?:,\\s[A-Za-z\\s]+)?(?:\\s\\d{5}(-\\d{4})?)?$")) {
            postalAdressLabel.getStyleClass().add("labelRequired");
            error = true;
        }

        if (!git.trim().isEmpty() && !git.matches(
        "^https?://(github\\.com|gitlab\\.com)/([a-zA-Z0-9._-]+)$")) {
    gitLinkLabel.getStyleClass().add("labelRequired");
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
                    personalNumber,
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
            // App.setRoot("contactList");
            System.out.println("Contact saved: " + newContact);
        }
    }

    // Method to save the current contact as VCard
    @FXML
    private void saveOneContactAsVCard(ActionEvent event) throws IOException {
        Contact contact = new Contact(
                lastNameTextField.getText(),
                firstNameTextField.getText(),
                comboBoxGender.getSelectionModel().getSelectedItem(),
                LocalDate.parse(birthDayTextField.getText()),
                pseudoTextField.getText(),
                personalNumberTextField.getText(),
                professionalTextField.getText(),
                mailAddressTextField.getText(),
                postalAddressTextField.getText(),
                gitTextField.getText());

        String filePath = "Onecontact.vcf";
        App.saveOneContactVCard(contact, filePath);
        System.out.println("Current contact saved as VCard: " + contact);
    }

    // Method to save all contacts as VCard
    @FXML
    private void saveAllContactsAsVCard(ActionEvent event) {
        try {
            ArrayList<Contact> contacts = App.deserializerMethod();
            String filePath = "contacts.vcf";
            App.saveContactsAsVCard(contacts, filePath);
            System.out.println("All contacts saved as VCard.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
