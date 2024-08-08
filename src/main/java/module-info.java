module fr.afpa {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires javafx.base;

    opens fr.afpa to javafx.fxml;

    exports fr.afpa;
}
