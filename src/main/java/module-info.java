module leontin.memento.mementov01 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires java.sql;
    requires jbcrypt;

    opens leontin.memento.mementov01 to javafx.fxml;
    exports leontin.memento.mementov01;
}