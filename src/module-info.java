module DataEntry {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.desktop;
	opens application to javafx.base, javafx.graphics, javafx.fxml;
}
