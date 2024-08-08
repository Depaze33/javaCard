package fr.afpa;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ContactListController {

    @FXML
    private TextField search;

    @FXML
    private VBox centerPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane anchorPane;

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

    @FXML
    private CheckBox checkBoxAll;

    ArrayList<CheckBox> checkBoxes = new ArrayList<>();

    ArrayList<String> selectedIds = new ArrayList<>();

    ArrayList<Button> delBtnsArray = new ArrayList<>();

    ArrayList<Contact> contactsToShow = new ArrayList<>();

    @FXML
    public void initialize() throws ClassNotFoundException, IOException {
        double scrollPaneWidth = this.scrollPane.getWidth();
        this.gridContactList.setMaxWidth(scrollPaneWidth-30);
        
        // responsive width adaption for the gridPane
        this.scrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.resizeGrid((double) newVal-30);
       });    
       
        this.gridContactList.getColumnConstraints().get(1).setMinWidth(100);
        this.gridContactList.getColumnConstraints().get(2).setMinWidth(100);
        this.gridContactList.getColumnConstraints().get(3).setMinWidth(100  );
        this.gridContactList.getColumnConstraints().get(3).setHgrow(Priority.SOMETIMES);

        this.diplaySearchResult(Contact.BINARY_MANAGER.loadList(Contact.SAVE_PATH));

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                searchContacts(newValue);
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        });

    }

    @FXML
    public void exportAllJsonSelected() throws ClassNotFoundException, IOException {
        exportAllSelected("json");
    }

    @FXML
    public void exportAllVcfSelected() throws ClassNotFoundException, IOException {
        exportAllSelected("vcf");

    }

    // export the contacts in the argument type
    public void exportAllSelected(String type) throws ClassNotFoundException, IOException {
        // get the right serializer
        var serializer = (type.equals("json")) ? Contact.JSON_SERIALIAZER : Contact.V_CARD_SERIALIZER;
        ArrayList<Contact> contactsSerializerList = new ArrayList<>();


        // get the selected contacts
        for (CheckBox checkBox : this.checkBoxes) {
            if (checkBox.isSelected()) {
                String id = (String) checkBox.getProperties().get("id");
                Contact contact = Contact.findContactById(id);
                contactsSerializerList.add(contact);
            }
        }

        // serialize
        serializer.saveList("contacts."+type, contactsSerializerList);
        System.out.println("contacts exported to \"Contacts."+type+"\"");
    }

    public void exportOneJson(ActionEvent event) throws ClassNotFoundException, IOException {
        this.exportOne("json", ((Button) event.getSource()).getProperties().get("id").toString());
    }

    public void exportOneVcf(ActionEvent event) throws ClassNotFoundException, IOException {
        this.exportOne("vcf", ((Button) event.getSource()).getProperties().get("id").toString());
    }

        // export the contacts in the argument type
        public void exportOne(String type, String id) throws ClassNotFoundException, IOException {
            Contact contactToExport = Contact.findContactById(id);
            // get the right serializer
            var serializer = (type.equals("json")) ? Contact.JSON_SERIALIAZER : Contact.V_CARD_SERIALIZER;
            String filePath = contactToExport.getFirstName()+contactToExport.getLastName()+"."+type;
    
           
    
            // serialize
            serializer.save(filePath, contactToExport);
            System.out.println("contact exported to \""+filePath+"\"");
        }

    // grid fits the scrollPane, MAX 900px
    public void resizeGrid(double maxWidth){
        maxWidth = (maxWidth < 900) ? maxWidth : 900; 
        this.gridContactList.setMaxWidth(maxWidth);
        this.gridContactList.setPrefWidth(maxWidth);
    }

    // method that switch all checkboxes select status 
    @FXML public void checkUncheckAllCheckBoxes(ActionEvent event){
        Boolean isSelected = false;
        // If the global checkbox is checked,  
        if(((CheckBox) event.getSource()).isSelected()){
            isSelected = true;
        }
        // checl or uncheck all checkboxes
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setSelected(isSelected);
        }
        this.updateCheckBoxes();
    } 
   

    // delete one contact (call delContacts with an arraylist that contains 1 contact)
    public boolean delContact(String id) throws ClassNotFoundException, IOException {
        ArrayList<String> contactsIds = new ArrayList<>();
        contactsIds.add(id);
        return this.delContacts(contactsIds);
    }

    // del contact from binary & from view
    public boolean delContacts(ArrayList<String> contactsIds) throws ClassNotFoundException, IOException {
        
        // get the contact object to del
        ArrayList<Contact> contacts = Contact.BINARY_MANAGER.loadList(Contact.SAVE_PATH);
        contacts.removeIf(contact -> contactsIds.contains(contact.getId()));
        this.checkBoxes.removeIf(checkbox -> contactsIds.contains(checkbox.getProperties().get("id")));


        this.selectedIds = new ArrayList<>();
        // update list view
        this.gridContactList.getChildren().clear();
        this.gridContactList.add(search, 0, 0);
        this.diplaySearchResult(contacts);
        Contact.BINARY_MANAGER.saveList(Contact.SAVE_PATH, contacts);
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

        if (this.selectedIds.size() == this.checkBoxes.size()){
            this.checkBoxAll.setSelected(true);
        }else{
            this.checkBoxAll.setSelected(false);
        }

        // show btn in case there is at least 1 contact selected
        Boolean isDisable = (this.selectedIds.size() > 0) ? false : true;
        this.delAllBtn.setDisable(isDisable);
        this.jsonAllBtn.setDisable(isDisable);
        this.vcfAllBtn.setDisable(isDisable);

        return true;
    }

    // delete all selcted contact from the list + persist
    @FXML
public void deleteAllSelected() throws ClassNotFoundException, IOException {
    // Create and configure the alert
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Deletion confirmation");
    alert.setHeaderText("Are you sure you want to delete all selected contacts?");
    alert.setContentText("This action is irreversible.");

    // Show the alert and wait for the user's response
    alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
            // If the user confirms, proceed with deletion
            ArrayList<String> contactsToDel = new ArrayList<>();
            for (Button delBtn : delBtnsArray) {
                if (!this.selectedIds.isEmpty() && this.selectedIds.contains(delBtn.getProperties().get("id").toString())) {
                    contactsToDel.add(delBtn.getProperties().get("id").toString());
                }
            }
            try {
                this.delContacts(contactsToDel);
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
    });
}

    // redirect with the id
    public boolean redirectToEdit(String id) throws IOException {
        CreationContactController.setId(id);
        App.setRoot("CreationContact");

        return true;
    }


    // find all contacts (with firstname & lastname) that match with the search string  
    public boolean searchContacts(String newValue) throws ClassNotFoundException, IOException{
        // split words with whitespace
        String[] words = newValue.split(" ");
        ArrayList<Contact> contacts = Contact.BINARY_MANAGER.loadList(Contact.SAVE_PATH);
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
                // remove contact if it doesnt match the last keyword from the request
                else if (i > 0 && !(contact.getFirstName().toLowerCase().contains(word) || contact.getLastName().toLowerCase().contains(word))){
                    this.contactsToShow.remove(contact);
                }
            }
        }
        this.gridContactList.getChildren().clear();
        this.gridContactList.add(search, 0, 0);
        contactsToShow = (search.getText().length() < 3 || search.getText().isBlank()) ? Contact.BINARY_MANAGER.loadList(Contact.SAVE_PATH) : contactsToShow;
        this.diplaySearchResult(this.contactsToShow);
        return true;
    }

    // display contacts in the grid
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
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Deletion confirmation");
                alert.setHeaderText("Are you sure you want to delete this contact?");
                alert.setContentText("This action is irreversible.");
            
                // Show the alert and wait for the user's response
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            String id = delBtn.getProperties().get("id").toString(); // Correct reference to ID
                            this.delContact(id); // Use the correct method name and pass the ID
                        } catch (ClassNotFoundException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            });
            
            delBtn.setMinWidth(90);
            gridContactList.add(delBtn, 4, row);

            Button jsonBtn = new Button("JSON 📄");
            jsonBtn.getStyleClass().add("roundedBtn");
            jsonBtn.getStyleClass().add("btn");
            jsonBtn.getStyleClass().add("jsonBtn");
            jsonBtn.getProperties().put("id", contact.getId());

            jsonBtn.setMinWidth(80);
            jsonBtn.setOnAction(event -> {
                try {
                    this.exportOneJson(event);
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            });
            gridContactList.add(jsonBtn, 5, row);

            Button vcfBtn = new Button("VCF 📇");
            vcfBtn.getStyleClass().add("roundedBtn");
            vcfBtn.getStyleClass().add("btn");
            vcfBtn.getStyleClass().add("vcfBtn");
            vcfBtn.getProperties().put("id", contact.getId());
            vcfBtn.setMinWidth(70);
            vcfBtn.setOnAction(event -> {
                try {
                    this.exportOneVcf(event);
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            });

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
