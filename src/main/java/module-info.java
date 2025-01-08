module osaki.mylang {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens osaki.mylang to javafx.fxml;
    exports osaki.mylang;
}