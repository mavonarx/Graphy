module Graphy.app.main {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires com.google.common;
    requires java.datatransfer;
    requires java.desktop;
    exports ch.zhaw.graphy;
    opens ch.zhaw.graphy;
}