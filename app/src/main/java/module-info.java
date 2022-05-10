module Graphy.app.main {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires com.google.common;
    exports ch.zhaw.graphy;
    opens ch.zhaw.graphy;
}