module fr.afpa {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens fr.afpa to javafx.fxml;
    exports fr.afpa;
}
