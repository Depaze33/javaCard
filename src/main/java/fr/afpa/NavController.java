package fr.afpa;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.Node;


public class NavController {
    @FXML
    private VBox leftPane;
    
    @FXML
    private Button navContactListBtn;
    
    @FXML
    private Button navAddBtn;

    // redirect to the right view
    @FXML void redirectTo(ActionEvent event) throws IOException{
        // Get the id of the button that has been clicked
        Node source = (Node) event.getSource();
        String eventId = source.getId();

        // redirect to the right view depending of the btn id
        switch (eventId) {
            case "navContactListBtn":
                CreationContactController.setId(null);
                App.setRoot("contactList");
                break;
            case "navAddBtn":
                CreationContactController.setId(null);
                App.setRoot("creationContact");
                break;
            default:
                break;
        } 
    }
}
