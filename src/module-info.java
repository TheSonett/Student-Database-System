module DataEntry {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	opens application to javafx.base, javafx.graphics, javafx.fxml;
}
