package fr.afpa;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
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
    public void initialize() throws ClassNotFoundException, IOException {
        ArrayList<Contact> contacts = App.deserializerMethod();
        int row = 1; // 1 instead of 0 because the search bar is in the first row 
        for (Contact contact : contacts) {
            CheckBox checkBox = new CheckBox();
            checkBox.getStyleClass().add("mainGridCheckbox");
            checkBox.getStyleClass().add("btn");
            gridContactList.add(checkBox, 0, row);


            Label name = new Label(contact.getFirstName() + " "+ contact.getLastName().toUpperCase());
            name.getStyleClass().add("mainGridTextElement");
            name.getStyleClass().add("mainGridTextName");
            gridContactList.add(name, 1, row);

            
            Label email = new Label(contact.getMailAdress());
            email.getStyleClass().add("mainGridTextElement");
            gridContactList.add(email, 2, row);

            // Get the private phone number if there is one
            String phoneNumber = (contact.getPrivateNumber().length()> 9) ? contact.getPrivateNumber() : contact.getProfessionalNumber();
            Label phone = new Label(phoneNumber);
            phone.getStyleClass().add("mainGridTextElement");
            gridContactList.add(phone, 3, row);
            
            Button delBtn = new Button("Delete ❌");
            delBtn.getStyleClass().add("roundedBtn");
            delBtn.getStyleClass().add("btn");
            delBtn.getStyleClass().add("delBtn");
            delBtn.getProperties().put("id",contact.getId());
            delBtn.setOnAction(event -> {
                try {
                    this.delContact(contact.getId(), event);
                } catch (ClassNotFoundException | IOException e) {
                    System.out.println("TESSSSSSSSSSSSSST");
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
            gridContactList.add(vcfBtn, 6, row);

            
            row++;
        }
    }


    // del contact from binary & from view
    public boolean delContact(String id, ActionEvent event) throws ClassNotFoundException, IOException{
        Integer rowId = GridPane.getRowIndex((Button)event.getSource());
        // del the row whit all cells
        // TODO : check if there is a way to remove space gap the deleted row
        gridContactList.getChildren().removeIf(node -> GridPane.getRowIndex(node) == rowId);

        Contact contactToDel = Contact.findContactById(id);
        // get the contact object to del
        ArrayList<Contact> contacts = App.deserializerMethod();


        ArrayList<Contact> newContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            System.out.println(contact.getId() + " " + id);
            if (id.compareTo(contact.getId()) != 0){
                newContacts.add(contact);

            }
        }


        // persist datas in binary file
        App.serializerMethode(newContacts);
        return true;
    }
}
