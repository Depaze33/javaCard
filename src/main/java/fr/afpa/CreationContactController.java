package fr.afpa;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class CreationContactController<Hbox> {
    @FXML
    private VBox centerPane;

    @FXML
    private Hbox modificationContactBox;

    @FXML
    private VBox contactInfosVBox;
    @FXML
    private GridPane contactGridPane;

@FXML 
private GridPane buttonGridPane;

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
    private TextField adressTextField;
    @FXML
    private TextField personalTextField;

    @FXML
    private TextField proffesionnalTextField;

    @FXML
    private TextField mailAdressTextField;

    @FXML
    private TextField postalAdressTextField;

    @FXML
    private TextField gitTextField;



   



    public void initialize() throws ClassNotFoundException, IOException {

    }

}
