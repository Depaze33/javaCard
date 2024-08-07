package fr.afpa;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ContactListController {

    @FXML
    private TextField search;

    @FXML
    private VBox centerPane;

    @FXML
    private HBox delExportBtns;

    @FXML
    private GridPane gridContactList;

    @FXML
    private Button delAllBtn;

    @FXML
    private Button jsonAllBtn;

    @FXML
    private Button vcfAllBtn;

    ArrayList<CheckBox> checkBoxes = new ArrayList<>();

    ArrayList<String> selectedIds = new ArrayList<>();

    ArrayList<Button> delBtnsArray = new ArrayList<>();

    ArrayList<Contact> contactsToShow = new ArrayList<>();

    @FXML
    public void exportAllJsonSelected() throws ClassNotFoundException, IOException {
        ArrayList<Contact> contactsSerializerList = new ArrayList<>();

        for (CheckBox checkBox : this.checkBoxes) {
            if (checkBox.isSelected()) {

                String id = (String) checkBox.getProperties().get("id");
                Contact contact = Contact.findContactById(id);
                contactsSerializerList.add(contact);
            }
        }
        ContactJsonSerialiazer serializer = new ContactJsonSerialiazer();
        serializer.saveList("contacts.json", contactsSerializerList);
        System.out.println("contacts exported to \"contacts.json\"");
    }

    @FXML
    public void exportAllVcfSelected() throws ClassNotFoundException, IOException {
        ArrayList<Contact> contactsSerializerList = new ArrayList<>();

        for (CheckBox checkBox : this.checkBoxes) {
            if (checkBox.isSelected()) {

                String id = (String) checkBox.getProperties().get("id");
                Contact contact = Contact.findContactById(id);
                contactsSerializerList.add(contact);
            }
        }
        ContactVCardSerializer serializer = new ContactVCardSerializer();
        serializer.saveList("contacts.vcf", contactsSerializerList);
        System.out.println("contacts exported to \"contacts.vcf\"");
    }

    @FXML
    public void initialize() throws ClassNotFoundException, IOException {

        
        this.diplaySearchResult(App.deserializerMethod());

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                searchContacts(newValue);
            } catch (ClassNotFoundException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

    }



   

    public boolean delContact(String id, ActionEvent event) throws ClassNotFoundException, IOException {
        return this.delContactWithoutDelBtn(id, (Button) event.getSource());
    }

    // del contact from binary & from view
    public boolean delContactWithoutDelBtn(String id, Button delBtn) throws ClassNotFoundException, IOException {
        Integer rowId = GridPane.getRowIndex(delBtn);
        // del the row whit all cells
        // remove checkbox from the arrayList
        this.checkBoxes.removeIf(checkbox -> checkbox.getProperties().get("id").equals(id));
        gridContactList.getChildren().removeIf(node -> GridPane.getRowIndex(node) == rowId);
        // get the contact object to del
        ArrayList<Contact> contacts = App.deserializerMethod();

        contacts.removeIf(c -> c.getId().equals(id));

        App.serializerMethode(contacts);
        this.updateCheckBoxes();
        return true;
    }

    public boolean updateCheckBoxes() {
        this.selectedIds = new ArrayList<>();
        // iterate on checkboxes to get the selected ids
        for (CheckBox checkBox : this.checkBoxes) {
            if (checkBox.isSelected()) {
                this.selectedIds.add(checkBox.getProperties().get("id").toString());
            }
        }

        // show btn in case there is at least 1 contact selected
        if (this.selectedIds.size() > 0) {
            this.delAllBtn.setDisable(false);
            this.jsonAllBtn.setDisable(false);
            this.vcfAllBtn.setDisable(false);
        } else {
            this.delAllBtn.setDisable(true);
            this.jsonAllBtn.setDisable(true);
            this.vcfAllBtn.setDisable(true);
        }

        return true;
    }

    // delete all selcted contact from the list + persist
    public void deleteAllSelected() throws ClassNotFoundException, IOException {
        // iterate on all del buttons
        for (Button delBtn : this.delBtnsArray) {
            // If there is at least 1 id selected & if the delete btn has the right contact
            // id
            if (!this.selectedIds.isEmpty() && this.selectedIds.contains(delBtn.getProperties().get("id").toString())) {
                // delete contact
                this.delContactWithoutDelBtn(delBtn.getProperties().get("id").toString(), delBtn);
            }
        }
    }

    public boolean redirectToEdit(String id) throws IOException {
        CreationContactController.setId(id);
        App.setRoot("CreationContact");

        return true;
    }


    // find all contacts (with firstname & lastname) that match with the search string  
    public boolean searchContacts(String newValue) throws ClassNotFoundException, IOException{
        // split words with whitespace
        String[] words = newValue.split(" ");
        ArrayList<Contact> contacts = App.deserializerMethod();
        this.contactsToShow.clear();
        // for each contact for each word in the search string
        for (Contact contact : contacts) {
            for (Integer i = 0; i < words.length; i++) {
                String word = words[i];
                word = word.toLowerCase();
                // find contacts that's match search string
                if ((contact.getFirstName().toLowerCase().contains(word) || contact.getLastName().toLowerCase().contains(word)) && !this.contactsToShow.contains(contact)){
                    // if there is some keywords, add only contacts that match all of them
                    // If it's the first word add all contacts that match the request
                    if (i==0 || (i > 0 && this.contactsToShow.contains(contact))){
                        this.contactsToShow.add(contact);
                    }                    
                }
                // remove contact if it doesnt match all keywords from the request
                else if (i > 0 && !(contact.getFirstName().toLowerCase().contains(word) || contact.getLastName().toLowerCase().contains(word))){
                    this.contactsToShow.remove(contact);
                }
            }
        }
        this.gridContactList.getChildren().clear();
        this.gridContactList.add(search, 0, 0);
        contactsToShow = (search.getText().length() < 3 || search.getText().isBlank()) ? App.deserializerMethod() : contactsToShow;
        this.diplaySearchResult(this.contactsToShow);
        return true;
    }

    public boolean diplaySearchResult(ArrayList<Contact> contacts){
        Integer row = 1; // 1 instead of 0 because the search bar is in the first row
        for (Contact contact : contacts) {
            CheckBox checkBox = new CheckBox();
            checkBox.getStyleClass().add("mainGridCheckbox");
            checkBox.getStyleClass().add("btn");
            checkBox.getStyleClass().add("checkBoxContact");
            checkBox.getProperties().put("id", contact.getId());
            checkBox.setOnAction(event -> this.updateCheckBoxes());
            gridContactList.add(checkBox, 0, row);

            this.checkBoxes.add(checkBox);

            Label name = new Label(contact.getFirstName() + " " + contact.getLastName().toUpperCase());
            name.getStyleClass().add("mainGridTextElement");
            name.getStyleClass().add("mainGridTextName");
            name.setOnMouseClicked(event -> {
                try {
                    this.redirectToEdit(contact.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            gridContactList.add(name, 1, row);

            Label email = new Label(contact.getMailAdress());
            email.getStyleClass().add("mainGridTextElement");
            gridContactList.add(email, 2, row);

            // Get the private phone number if there is one
            String phoneNumber = (contact.getPrivateNumber().length() > 9) ? contact.getPrivateNumber()
                    : contact.getProfessionalNumber();
            Label phone = new Label(phoneNumber);
            phone.getStyleClass().add("mainGridTextElement");
            gridContactList.add(phone, 3, row);

            Button delBtn = new Button("Delete ❌");
            delBtn.getStyleClass().add("roundedBtn");
            delBtn.getStyleClass().add("btn");
            delBtn.getProperties().put("id", contact.getId());
            delBtn.getStyleClass().add("delBtn");
            delBtnsArray.add(delBtn);
            delBtn.setOnAction(event -> {
                try {
                    this.delContact(contact.getId(), event);
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            });
            delBtn.setMinWidth(90);
            gridContactList.add(delBtn, 4, row);

            Button jsonBtn = new Button("JSON 📄");
            jsonBtn.getStyleClass().add("roundedBtn");
            jsonBtn.getStyleClass().add("btn");
            jsonBtn.getStyleClass().add("jsonBtn");
            jsonBtn.setMinWidth(80);
            gridContactList.add(jsonBtn, 5, row);

            Button vcfBtn = new Button("VCF 📇");
            vcfBtn.getStyleClass().add("roundedBtn");
            vcfBtn.getStyleClass().add("btn");
            vcfBtn.getStyleClass().add("vcfBtn");
            vcfBtn.getProperties().put("id", contact.getId());
            vcfBtn.setMinWidth(70);
            gridContactList.add(vcfBtn, 6, row);

            row++;
        }
        return true;
    }

    

    public ArrayList<String> getSelectedIds() {
        return this.selectedIds;
    }

    public void setSelectedIds(ArrayList<String> selectedIds) {
        this.selectedIds = selectedIds;
    }

}
