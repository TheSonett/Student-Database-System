/*
 * This project is created By Joy Saha on 12/01/2022
 *  	@Author: TheSonett
 */

/*
 * TODO:
 * 1. Implement empty text field warning message dialog box
 * 2. Implement creating new table in database option system
 * 3. Implement existing table deletion from database
 * 4. Implement press ENTER to add data into database & also ENTER for login button **
 * 5. Designing UI System using CSS
 * 6. Implement multiple data updating of database **
 * 7. Maintaining the code
 */

package application;

import javax.swing.JFrame;

public class Main {
    
	public static LoginPage frame;
	
    public static void main(String[] args) {
	     frame = new LoginPage();
	     
	     frame.setTitle("Login Page");
	     frame.setVisible(true);
	     frame.setBounds(420,120,400,450);
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     frame.setResizable(false);
	}
}
