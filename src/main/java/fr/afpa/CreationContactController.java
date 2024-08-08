package fr.afpa;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
    private DatePicker birthDayTextField;

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

    public static String id = null;

    public static String getId() {
        return CreationContactController.id;
    }

    public static void setId(String id) {
        CreationContactController.id = id;
    }

    public void initialize() throws ClassNotFoundException, IOException {
        // hide the edit button

        buttonEdit.setVisible(false);
        buttonDelete.setVisible(false);
        birthDayTextField.getEditor().setDisable(true);

        buttonExportVCard.setOnAction(event -> {
            try {
                this.saveOneContact("vcf");
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }

        });
        buttonExportJson.setOnAction(event -> {
            try {
                this.saveOneContact("json");
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }

        });

        // add elemetns to gender ComboBox
        ObservableList<String> genders = FXCollections.observableArrayList();
        genders.add("M");
        genders.add("F");
        genders.add("NB");
        comboBoxGender.setItems(genders);
        comboBoxGender.getSelectionModel().selectFirst();

        // fill contact infos if it's an edit
        if (id != null) {
            buttonEdit.setVisible(true);
            buttonDelete.setVisible(true);
            // get the contact obj thx to the id
            Contact contact = Contact.findContactById(id);
            buttonExportVCard.setOnAction(event -> {
                try {
                    this.saveOneContact(contact, "vcf");
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            });
            buttonExportJson.setOnAction(event -> {
                try {
                    this.saveOneContact(contact, "json");
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            });
           buttonDelete.setOnAction(event -> {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Deletion confirmation");
    alert.setHeaderText("Are you sure you want to delete this contact?");
    alert.setContentText("This action is irreversible.");

    // Affiche l'alerte et attends la réponse de l'utilisateur
    alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
            try {
                this.delOneContact(id); // Supprime le contact seulement si la confirmation est positive
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
    });
});


            // fill each field & disable them
            lastNameTextField.setText(contact.getLastName());
            lastNameTextField.setDisable(true);
            firstNameTextField.setText(contact.getFirstName());
            firstNameTextField.setDisable(true);
            comboBoxGender.getSelectionModel().select(contact.getGender());
            comboBoxGender.setDisable(true);
            birthDayTextField.setValue(contact.getBirthDate());
            birthDayTextField.setDisable(true);
            pseudoTextField.setText(contact.getPseudo());
            pseudoTextField.setDisable(true);
            personalNumberTextField.setText(contact.getPrivateNumber());
            personalNumberTextField.setDisable(true);
            professionalTextField.setText(contact.getProfessionalNumber());
            professionalTextField.setDisable(true);
            mailAddressTextField.setText(contact.getMailAdress());
            mailAddressTextField.setDisable(true);
            postalAddressTextField.setText(contact.getPostalAdress());
            postalAddressTextField.setDisable(true);
            gitTextField.setText(contact.getGithub());
            gitTextField.setDisable(true);

            buttonSave.setDisable(true);
        }
    }

    private boolean delOneContact(String id) throws IOException, ClassNotFoundException {

        ArrayList<Contact> contacts = Contact.BINARY_MANAGER.loadList(Contact.SAVE_PATH);
        contacts.removeIf(contact -> contact.getId().equals(id));
        Contact.BINARY_MANAGER.saveList(Contact.SAVE_PATH, contacts);
        CreationContactController.setId(null);
        App.setRoot("contactList");
        return true;
    }

    // Methode save contact
    public boolean persistContact() throws ClassNotFoundException, IOException {
        // get all the information from the fields
        // declaration of variables for each information

        String lastName = lastNameTextField.getText();

        String firstName = firstNameTextField.getText();
        String gender = comboBoxGender.getPromptText();
        LocalDate birthDate = birthDayTextField.getValue();
        String pseudo = pseudoTextField.getText();
        String personalNumber = personalNumberTextField.getText();
        String professionalNumber = professionalTextField.getText();
        String mailAddress = mailAddressTextField.getText();
        String postalAddress = postalAddressTextField.getText();
        String git = gitTextField.getText();

        // Load existing contacts
        ArrayList<Contact> contacts = Contact.BINARY_MANAGER.loadList(Contact.SAVE_PATH);

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

        if (personalNumber.trim().isEmpty() ||
                !personalNumber.matches(
                        "^(\\+?\\d{1,3}[-.\\s]?)?\\(?\\d{1,4}\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$")) {
            personalPhoneLabel.getStyleClass().add("labelRequired");
            error = true;
        }

        if (!professionalNumber.trim().isEmpty() && !professionalNumber.matches(
                "^(\\+?\\d{1,3}[-.\\s]?)?\\(?\\d{1,4}\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$")) {
            proffesionalPhoneLabel.getStyleClass().add("labelRequired");
            error = true;
        }

        if (mailAddress.trim().isEmpty() &&
                !mailAddress.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
                    
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
            // If create
            if (id == null) {
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

                // Add the new contact to the list
                contacts.add(newContact);
                // Save the updated list of contacts
                Contact.BINARY_MANAGER.saveList(Contact.SAVE_PATH, contacts);
                CreationContactController.setId(newContact.getId());
                App.setRoot("creationContact");
            }
            // if edit
            else {
                Contact contactToEdit = Contact.findContactById(id);
                contactToEdit.setFirstName(firstName);
                contactToEdit.setLastName(lastName);
                contactToEdit.setBirthDate(birthDate);
                contactToEdit.setPseudo(pseudo);
                contactToEdit.setPrivateNumber(personalNumber);
                contactToEdit.setProfessionalNumber(professionalNumber);
                contactToEdit.setMailAdress(mailAddress);
                contactToEdit.setPostalAdress(postalAddress);
                contactToEdit.setGithub(git);
                Integer contactPos = Contact.findContactPosById(id);
                contacts.set(contactPos, contactToEdit);
                // Save the updated list of contacts
                Contact.BINARY_MANAGER.saveList(Contact.SAVE_PATH, contacts);
                CreationContactController.setId(id);
                App.setRoot("creationContact");
            }

            return true;
        }
        return false;
    }

    public boolean editContact() {
        lastNameTextField.setDisable(false);
        firstNameTextField.setDisable(false);
        comboBoxGender.setDisable(false);
        birthDayTextField.setDisable(false);
        pseudoTextField.setDisable(false);
        personalNumberTextField.setDisable(false);
        professionalTextField.setDisable(false);
        mailAddressTextField.setDisable(false);
        postalAddressTextField.setDisable(false);
        gitTextField.setDisable(false);

        this.buttonEdit.setDisable(true);
        this.buttonSave.setDisable(false);
        return true;
    }

    // Export one contact to JSON or VCARD from new contact
    private boolean saveOneContact(String type) throws ClassNotFoundException, IOException {
        Contact contact = new Contact(
                lastNameTextField.getText(),
                firstNameTextField.getText(),
                comboBoxGender.getSelectionModel().getSelectedItem(),
                birthDayTextField.getValue(),
                pseudoTextField.getText(),
                personalNumberTextField.getText(),
                professionalTextField.getText(),
                mailAddressTextField.getText(),
                postalAddressTextField.getText(),
                gitTextField.getText());
        return this.saveOneContact(contact, type);
    
    }

    // Export one contact to JSON or VCARD from contact object
    private boolean saveOneContact(Contact contact, String type) throws IOException, ClassNotFoundException {
        if (this.persistContact()) {
            String filePath = contact.getFirstName() + contact.getLastName();
            switch (type) {
                case "vcf":
                    filePath = filePath + "." + type;
                    Contact.V_CARD_SERIALIZER.save(filePath, contact);
                    break;
                case "json":
                    filePath = filePath + "." + type;
                    Contact.JSON_SERIALIAZER.save(filePath, contact);
                    break;
                default:
                    break;
            }
            System.out.println("Current contact saved as : " + filePath);
            return true;
        }
        return false;


        // Alert
       
        // action event
        
    }

    // Method to save all contacts as VCard
    @FXML
    private void saveAllContactsAsVCard(ActionEvent event) throws IOException {
        ArrayList<Contact> contacts = Contact.BINARY_MANAGER.loadList(Contact.SAVE_PATH);
        Contact.V_CARD_SERIALIZER.saveList(Contact.SAVE_PATH, contacts);
    }

}
