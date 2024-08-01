package fr.afpa;

import javafx.fxml.FXML;
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
    public void initialize() {
    }
}
