module com.example.entregable_u2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires javafx.base;
    requires org.xerial.sqlitejdbc;

    opens com.example.entregable_u2 to javafx.fxml;
    opens com.example.entregable_u2.model to javafx.base, javafx.fxml;
    opens com.example.entregable_u2.controller to javafx.fxml;
    opens com.example.entregable_u2.views to javafx.fxml;

    exports com.example.entregable_u2;
    //añadimos los paquetes del proyecto

    exports com.example.entregable_u2.utils;
    exports com.example.entregable_u2.test;
    exports com.example.entregable_u2.model;
    exports com.example.entregable_u2.interfaces;
    exports com.example.entregable_u2.implementacion;
    exports com.example.entregable_u2.controller;


}
