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

    public static String id = null;

    
    @FXML
    public void initialize() throws ClassNotFoundException, IOException {
        //  fill contact infos if it's an edit
        if (id != null){
            // get the contact obj thx to the id
            Contact contact = Contact.findContactById(id);

            // fill each field & disable them
            lastNameTextField.setText(contact.getFirstName());
            lastNameTextField.setDisable(true);
            firstNameTextField.setText(contact.getLastName());
            firstNameTextField.setDisable(true);
            genderMenuButton.setText(contact.getGender());
            genderMenuButton.setDisable(true);
            birthDayTextField.setText(contact.getBirthDate().toString());
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

    public boolean editContact(){
        lastNameTextField.setDisable(false);
        firstNameTextField.setDisable(false);
        genderMenuButton.setDisable(false);
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

        // Load existing contacts
        ArrayList<Contact> contacts = App.deserializerMethod();

        // If create
        if (id == null){
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

        

            // Add the new contact to the list
            contacts.add(newContact);
            // Save the updated list of contacts
            App.serializerMethode(contacts);
        }
        // if edit
        else{
            Contact contactToEdit = Contact.findContactById(id);
            contactToEdit.setFirstName(firstName);
            contactToEdit.setLastName(lastName);
            contactToEdit.setBirthDate(birthDate);
            contactToEdit.setPseudo(pseudo);
            contactToEdit.setPrivateNumber(personnalNumber);
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

    public MenuButton getGenderMenuButton() {
        return this.genderMenuButton;
    }

    public void setGenderMenuButton(MenuButton genderMenuButton) {
        this.genderMenuButton = genderMenuButton;
    }

    public TextField getBirthDayTextField() {
        return this.birthDayTextField;
    }

    public void setBirthDayTextField(TextField birthDayTextField) {
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
}