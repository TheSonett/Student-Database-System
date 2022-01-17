package application;


import javax.swing.*;
import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
 
@SuppressWarnings("serial")
public class LoginPage extends JFrame implements ActionListener {
 
	public static Connection connection;
	public static Statement statement;
	public static String sql;
	public static ResultSet rs;
	
    Container container = getContentPane();
    JLabel userLabel = new JLabel("Enter username");
    JLabel passwordLabel = new JLabel("Enter password");
    
    JTextField userTextField = new JTextField();
    JTextField passwordField = new JTextField();
    
    JButton loginButton = new JButton("Login");
 
 
    LoginPage() {
 
    	loginButton.addActionListener(this);
    	   	
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
    }
    
   public void setLayoutManager() {
       container.setLayout(null);
   }
   
   public void setLocationAndSize() {
       userLabel.setBounds(50,150,100,30);
       passwordLabel.setBounds(50,220,100,30);
       userTextField.setBounds(150,150,150,30);
       passwordField.setBounds(150,220,150,30);
       loginButton.setBounds(100,300,100,30);
   }
   
   public void addComponentsToContainer() {
       container.add(userLabel);
       container.add(passwordLabel);
       container.add(userTextField);
       container.add(passwordField);
       container.add(loginButton);
   }
 
 
    @Override
    public void actionPerformed(ActionEvent event) {
    	if(event.getSource() == loginButton) {
    		System.out.println("Username: " + userTextField.getText());
    		System.out.println("Password: " + passwordField.getText());
    		
    		String username = userTextField.getText();
    		String password = passwordField.getText(); 
    		
    		try {
    			Class.forName("oracle.jdbc.driver.OracleDriver");
    			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", username, password);
    			
    			statement = connection.createStatement();
    			sql = "select * from joy";   
    			
    			rs = statement.executeQuery(sql);
    			
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    		// Destroy the login window
    		Main.frame.dispose();
    		
    		// Open or start the database window
    		DatabaseWindow.launchWindow();
    	}
    }
}
