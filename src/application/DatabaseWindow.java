package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DatabaseWindow extends Application {
	private static Stage window;
    private static Scene scene;
    private TableView<Table> student;
    
    private TextField idInput, nameInput, secInput, marksInput;
    private TextField idUpdate, nameUpdate, secUpdate, marksUpdate;
    
    
    public static Connection connection = LoginPage.connection;
    public static Statement statement = LoginPage.statement;
    public static String sql = LoginPage.sql;
    public static String sql2, sql3, sql4;
    public static ResultSet rs = LoginPage.rs;
    
    
    public static void launchWindow() {
    	 launch();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
    
        window = stage;
        window.setTitle("Student Database System");
        window.setResizable(false);

        // Data Columns
        TableColumn<Table, Integer> idCol = new TableColumn<>("ID");
        idCol.setMinWidth(200);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                
        TableColumn<Table, String> nameCol = new TableColumn<>("NAME");
        nameCol.setMinWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Table, Double> marksCol = new TableColumn<>("MARKS");
        marksCol.setMinWidth(200);
        marksCol.setCellValueFactory(new PropertyValueFactory<>("marks"));
        
        TableColumn<Table, String> secCol= new TableColumn<>("SECTION");
        secCol.setMinWidth(200);
        secCol.setCellValueFactory(new PropertyValueFactory<>("section"));        

        // id input
        idInput = new TextField();
        idInput.setPromptText("user id");
        idInput.setMinWidth(100);

        // name input
        nameInput = new TextField();
        nameInput.setPromptText("user name");

        // marks input
        marksInput = new TextField();
        marksInput.setPromptText("enter marks");
        
        // section input
        secInput = new TextField();
        secInput.setPromptText("enter section");
        
        
        // Update data fields
        idUpdate = new TextField();
        idUpdate.setPromptText("update id");
        idUpdate.setMinWidth(100);

        nameUpdate = new TextField();
        nameUpdate.setPromptText("update name");

        marksUpdate = new TextField();
        marksUpdate.setPromptText("update marks");
        
        secUpdate = new TextField();
        secUpdate.setPromptText("update section");
        

        // Buttons
        Button addButton = new Button("add");
        Button deleteButton = new Button("delete");      
        Button updateButton = new Button("update");
        Button loadButton = new Button("load");
        Button refreshButton = new Button("refresh");
       
        

        // Insert data when we press add button - insert data to database
        addButton.setOnAction(actionEvent -> {
        	Table table = new Table();
        
            table.setId(Integer.parseInt(idInput.getText()));
            table.setName(nameInput.getText());
            table.setMarks(Double.parseDouble(marksInput.getText()));
            table.setSection(secInput.getText());
            
            try {
            	sql2="insert into joy values(" + Integer.parseInt(idInput.getText()) + ",'" + 
            				nameInput.getText() + "'," + Double.parseDouble(marksInput.getText()) + ",'" + secInput.getText() + "')";
				statement.executeUpdate(sql2);
				connection.commit();
				
				student.refresh();
				
				System.out.println("Record Inserted");
			} catch (SQLException e) {
				e.printStackTrace();
			}
            
            student.getItems().add(table);

            idInput.clear();
            nameInput.clear();
            secInput.clear();
            marksInput.clear();
        });

        
        // Delete data from database when we select & press delete button
        deleteButton.setOnAction(actionEvent -> {
            ObservableList<Table> selectedItems, allItems;
            
            allItems = student.getItems();
            selectedItems = student.getSelectionModel().getSelectedItems();
         
            for(Table items: selectedItems) {
            	int id = (int)items.getId();
            	try {            	
            		sql3 = "delete from joy where ID = " + id + "";
            		statement.executeUpdate(sql3);
            		connection.commit();
            		
            		System.out.println("Record Deleted");
            	} catch (SQLException ex) {
            		System.out.println("Exception!!!");
            		ex.printStackTrace();
            	}
            }
               
            allItems.removeAll(selectedItems);
        });

        // load selected data from database when user press load button
        loadButton.setOnAction(actionEvent -> {
        	 ObservableList<Table> selectedItems;
             selectedItems = student.getSelectionModel().getSelectedItems();
             
             for(Table items: selectedItems) {
            	 int id = items.getId();
            	 String name = items.getName();
            	 double marks = items.getMarks();
            	 String section = items.getSection();
            	 
            	 idUpdate.setText("" + id);
            	 nameUpdate.setText(name);
            	 marksUpdate.setText("" + marks);
            	 secUpdate.setText(section);
            	 break;
             }
        });
        
        // Update data to database
        updateButton.setOnAction(actionEvent -> {
        	int id = Integer.parseInt(idUpdate.getText());
        	String name = nameUpdate.getText();
        	double marks = Double.parseDouble(marksUpdate.getText());
       	 	String section = secUpdate.getText();
        	
        	try {
        		sql4 = "update joy set NAME = '"+ name +"'"+ " where ID = " + id;
        		//sql4 = "update joy set MARKS = "+ marks + " where ID = " + id;
        		//sql4 = "update joy set MARKS = "+ section + " where ID = " + id;
        		//sql4 = "update joy set NAME= '"+ name +"', MARKS= "+ marks + ", SECTION= " + section + " where ID= " + id;
        		statement.executeUpdate(sql4);
        		
        		System.out.println("Record Updated");
        		
        	}
        	catch (SQLException e) {
        		
        		e.printStackTrace();
        	}
        	
            idUpdate.clear();
            nameUpdate.clear();
            secUpdate.clear();
            marksUpdate.clear();
        });
        
        // refresh the page when user press refresh button
        refreshButton.setOnAction(actionEvent -> {
        	idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        	nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        	marksCol.setCellValueFactory(new PropertyValueFactory<>("marks"));
        	secCol.setCellValueFactory(new PropertyValueFactory<>("section"));
			
			try { 
				rs = statement.executeQuery(sql);
				System.out.println("Page Refreshed!!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	
        	ObservableList<Table> tempList = getStudents();
        	student.setItems(tempList);
        	
        	
        	idInput.clear();
            nameInput.clear();
            secInput.clear();
            marksInput.clear();
        	
        	idUpdate.clear();
            nameUpdate.clear();
            secUpdate.clear();
            marksUpdate.clear();
        });
        
        
        
        // table
        student = new TableView<>();
        student.setItems(getStudents());
        student.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        student.getColumns().addAll(idCol, nameCol, marksCol, secCol);

        
        // HBox Layout
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(idInput, nameInput, marksInput, secInput, addButton, deleteButton);
        
        // HBox2 Layout
        HBox hBox2 = new HBox();
        hBox2.setPadding(new Insets(10, 10, 10, 10));
        hBox2.setSpacing(10);
        hBox2.getChildren().addAll(idUpdate, nameUpdate, marksUpdate, secUpdate, updateButton, loadButton, refreshButton);
        

        // VBox Layout
        VBox vBox = new VBox();
        vBox.getChildren().addAll(student, hBox, hBox2);
        
        // Scene
        scene = new Scene(vBox, 820, 450);
        window.setScene(scene);
        window.show();
    }

    // Get All the students - fetch data from database
    public ObservableList<Table> getStudents() {
        ObservableList<Table> students = FXCollections.observableArrayList();
        
        try {
			while(rs.next()){
				try {
					students.add(new Table((int)rs.getInt("ID"), (String)rs.getString("NAME"), (double)rs.getDouble("MARKS"), rs.getString("SECTION")));
				} catch (SQLException e) {
					e.printStackTrace();
				}        
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        return students;
    }
}
