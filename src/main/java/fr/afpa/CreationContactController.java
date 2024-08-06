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
import javafx.scene.control.DatePicker;
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

    public void initialize() throws ClassNotFoundException, IOException {

        // ONLY DEBUG CODE

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

        // fill contact infos if it's an edit
        if (id != null) {
            // buttonExportVCard.setVisible(false);
            buttonEdit.setVisible(true);
            buttonDelete.setVisible(true);
            // get the contact obj thx to the id
            Contact contact = Contact.findContactById(id);

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
        ArrayList<Contact> contacts = App.deserializerMethod();

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

        if (!professionalNumber.trim().isEmpty() && !professionalNumber.matches(
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
                App.serializerMethode(contacts);
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
                App.serializerMethode(contacts);
            }

            App.setRoot("contactList");
            CreationContactController.setId(null);
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

    public VBox getCenterPane() {
        return this.centerPane;
    }

    public void setCenterPane(VBox centerPane) {
        this.centerPane = centerPane;
    }

    public HBox getModificationContactBox() {
        return this.modificationContactBox;
    }

    public void setModificationContactBox(HBox modificationContactBox) {
        this.modificationContactBox = modificationContactBox;
    }

    public VBox getContactInfosVBox() {
        return this.contactInfosVBox;
    }

    public void setContactInfosVBox(VBox contactInfosVBox) {
        this.contactInfosVBox = contactInfosVBox;
    }

    public GridPane getContactGridPane() {
        return this.contactGridPane;
    }

    public void setContactGridPane(GridPane contactGridPane) {
        this.contactGridPane = contactGridPane;
    }

    public GridPane getButtonGridPane() {
        return this.buttonGridPane;
    }

    public void setButtonGridPane(GridPane buttonGridPane) {
        this.buttonGridPane = buttonGridPane;
    }

    public Button getButtonExportJson() {
        return this.buttonExportJson;
    }

    public void setButtonExportJson(Button buttonExportJson) {
        this.buttonExportJson = buttonExportJson;
    }

    public Button getButtonExportVCard() {
        return this.buttonExportVCard;
    }

    public void setButtonExportVCard(Button buttonExportVCard) {
        this.buttonExportVCard = buttonExportVCard;
    }

    public Button getButtonSave() {
        return this.buttonSave;
    }

    public void setButtonSave(Button buttonSave) {
        this.buttonSave = buttonSave;
    }

    public Button getButtonEdit() {
        return this.buttonEdit;
    }

    public void setButtonEdit(Button buttonEdit) {
        this.buttonEdit = buttonEdit;
    }

    public Button getButtonDelete() {
        return this.buttonDelete;
    }

    public void setButtonDelete(Button buttonDelete) {
        this.buttonDelete = buttonDelete;
    }

    public Label getRequiredLabel() {
        return this.requiredLabel;
    }

    public void setRequiredLabel(Label requiredLabel) {
        this.requiredLabel = requiredLabel;
    }

    public TextField getLastNameTextField() {
        return this.lastNameTextField;
    }

    public void setLastNameTextField(TextField lastNameTextField) {
        this.lastNameTextField = lastNameTextField;
    }

    public TextField getFirstNameTextField() {
        return this.firstNameTextField;
    }

    public void setFirstNameTextField(TextField firstNameTextField) {
        this.firstNameTextField = firstNameTextField;
    }

    public DatePicker getBirthDayTextField() {
        return this.birthDayTextField;
    }

    public void setBirthDayTextField(DatePicker birthDayTextField) {
        this.birthDayTextField = birthDayTextField;
    }

    public TextField getPseudoTextField() {
        return this.pseudoTextField;
    }

    public void setPseudoTextField(TextField pseudoTextField) {
        this.pseudoTextField = pseudoTextField;
    }

    public TextField getPersonalNumberTextField() {
        return this.personalNumberTextField;
    }

    public void setPersonalNumberTextField(TextField personalNumberTextField) {
        this.personalNumberTextField = personalNumberTextField;
    }

    public TextField getProfessionalTextField() {
        return this.professionalTextField;
    }

    public void setProfessionalTextField(TextField professionalTextField) {
        this.professionalTextField = professionalTextField;
    }

    public TextField getMailAddressTextField() {
        return this.mailAddressTextField;
    }

    public void setMailAddressTextField(TextField mailAddressTextField) {
        this.mailAddressTextField = mailAddressTextField;
    }

    public TextField getPostalAddressTextField() {
        return this.postalAddressTextField;
    }

    public void setPostalAddressTextField(TextField postalAddressTextField) {
        this.postalAddressTextField = postalAddressTextField;
    }

    public TextField getGitTextField() {
        return this.gitTextField;
    }

    public void setGitTextField(TextField gitTextField) {
        this.gitTextField = gitTextField;
    }

    public Button getSaveButton() {
        return this.saveButton;
    }

    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }

    public static String getId() {
        return CreationContactController.id;
    }

    public static void setId(String id) {
        CreationContactController.id = id;
    }


    
    // Method to save the current contact as VCard
    @FXML
    private void saveOneContactAsVCard(ActionEvent event) throws IOException {
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
