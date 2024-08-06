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

    @FXML
    public void initialize() throws ClassNotFoundException, IOException {

        ArrayList<Contact> contacts = App.deserializerMethod();
        int row = 1; // 1 instead of 0 because the search bar is in the first row
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
            gridContactList.add(delBtn, 4, row);

            Button jsonBtn = new Button("JSON 📄");
            jsonBtn.getStyleClass().add("roundedBtn");
            jsonBtn.getStyleClass().add("btn");
            jsonBtn.getStyleClass().add("jsonBtn");
            gridContactList.add(jsonBtn, 5, row);

            Button vcfBtn = new Button("VCF 📇");
            vcfBtn.getStyleClass().add("roundedBtn");
            vcfBtn.getStyleClass().add("btn");
            vcfBtn.getStyleClass().add("vcfBtn");
            vcfBtn.getProperties().put("id", contact.getId());
            vcfBtn.setOnAction(event -> {
            try {
            this.serializeVcfContact(contact.getId(), event);
            } catch (Exception e) {
            e.printStackTrace();
            }
            });
            gridContactList.add(vcfBtn, 6, row);

            row++;
        }

    }

    private void serializeVcfContact(String id, ActionEvent event) throws ClassNotFoundException, IOException {
        Contact contact = Contact.findContactById(id);
        ContactVCardSerializer serializer = new ContactVCardSerializer();
        serializer.save( contact.getFirstName()+contact.getLastName()+".vcf", contact);
       

    }

    @FXML
    private void exportAllVcfSelected(ActionEvent event) throws ClassNotFoundException, IOException {

    ArrayList<Contact> contactsSerializerList = new ArrayList<>();
        ArrayList<Contact> contacts = App.deserializerMethod();

        for (CheckBox checkBox : this.checkBoxes) {
            if (checkBox.isSelected()) {

                String id = (String) checkBox.getProperties().get("id");
                Contact contact = Contact.findContactById(id);
                contactsSerializerList.add(contact);
            }
        }
        ContactVCardSerializer serializer = new ContactVCardSerializer();
        serializer.saveList("contacts.vcf", contactsSerializerList);
        
   
    }

   

    public boolean delContact(String id, ActionEvent event) throws ClassNotFoundException, IOException {
        return this.delContactWithoutDelBtn(id, (Button) event.getSource());
    }

    // del contact from binary & from view
    public boolean delContactWithoutDelBtn(String id, Button delBtn) throws ClassNotFoundException, IOException {
        Integer rowId = GridPane.getRowIndex(delBtn);
        // del the row whit all cells
        // TODO : check if there is a way to remove space gap the deleted row
        // remove checkbox from the arrayList
        this.checkBoxes.removeIf(checkbox -> checkbox.getProperties().get("id").equals(id));
        gridContactList.getChildren().removeIf(node -> GridPane.getRowIndex(node) == rowId);
        Contact contactToDel = Contact.findContactById(id);
        // get the contact object to del
        ArrayList<Contact> contacts = App.deserializerMethod();

        ArrayList<Contact> newContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (id.compareTo(contact.getId()) != 0) {
                newContacts.add(contact);
            }
        }
        // persist datas in binary file
        App.serializerMethode(newContacts);
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

    public void exportAllJsonSelected() {

    }

    public void exportAllVcfSelected() {

    }
    ////////////////// |\\\\\\\\\\\\\\\\\
    ///////// GETTERS & SETTERS \\\\\\\\
    ////////////////// |\\\\\\\\\\\\\\\\\

    public TextField getSearch() {
        return this.search;
    }

    public void setSearch(TextField search) {
        this.search = search;
    }

    public VBox getCenterPane() {
        return this.centerPane;
    }

    public void setCenterPane(VBox centerPane) {
        this.centerPane = centerPane;
    }

    public HBox getDelExportBtns() {
        return this.delExportBtns;
    }

    public void setDelExportBtns(HBox delExportBtns) {
        this.delExportBtns = delExportBtns;
    }

    public GridPane getGridContactList() {
        return this.gridContactList;
    }

    public void setGridContactList(GridPane gridContactList) {
        this.gridContactList = gridContactList;
    }

    public Button getDelAllBtn() {
        return this.delAllBtn;
    }

    public void setDelAllBtn(Button delAllBtn) {
        this.delAllBtn = delAllBtn;
    }

    public Button getJsonAllBtn() {
        return this.jsonAllBtn;
    }

    public void setJsonAllBtn(Button jsonAllBtn) {
        this.jsonAllBtn = jsonAllBtn;
    }

    public Button getVcfAllBtn() {
        return this.vcfAllBtn;
    }

    public void setVcfAllBtn(Button vcfAllBtn) {
        this.vcfAllBtn = vcfAllBtn;
    }

    public ArrayList<CheckBox> getCheckBoxes() {
        return this.checkBoxes;
    }

    public void setCheckBoxes(ArrayList<CheckBox> checkBoxes) {
        this.checkBoxes = checkBoxes;
    }

    public ArrayList<String> getSelectedIds() {
        return this.selectedIds;
    }

    public void setSelectedIds(ArrayList<String> selectedIds) {
        this.selectedIds = selectedIds;
    }

    public ArrayList<Button> getDelBtnsArray() {
        return this.delBtnsArray;
    }

    public void setDelBtnsArray(ArrayList<Button> delBtnsArray) {
        this.delBtnsArray = delBtnsArray;
    }
}
