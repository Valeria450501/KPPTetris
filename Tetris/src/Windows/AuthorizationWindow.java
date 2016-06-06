package Windows; // нужны базы данных для работы этого окошка
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class AuthorizationWindow extends JFrame {
	private JButton entryButton;
	private JButton checkInButton;
	private JLabel textName;
	private JLabel textPassword;
	private JPasswordField passwordField;
	private JTextField nameField;
	private Box mainBox;
	
	public AuthorizationWindow(MenuMainWindow menu){
		super("Авторизация");
		entryButton = new JButton("Вход");	//полностью будет работать только на 3 лаб. работе
		checkInButton = new JButton("Регистрация");	//будет работать только на 3 лаб. работе
		textName = new JLabel("Логин:");
		nameField = new JTextField(20);
		textPassword = new JLabel("Пароль:");
		passwordField = new JPasswordField(20);
		
		entryButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				//menu.dispose();
				//dispose();
			}
		});
				
		this.setBackground(Color.white);
		createMainBox();
		setContentPane(mainBox);
		setSize(300,200);
		setResizable(false);
		this.setLocationRelativeTo(null);
	}
	private void createMainBox(){
		
		mainBox = Box.createVerticalBox();
		Box boxButtons = Box.createHorizontalBox();
		Box boxText = Box.createVerticalBox();
		Box boxEnter = Box.createVerticalBox();
		Box boxAllEnter = Box.createHorizontalBox();
		
		passwordField.setHorizontalAlignment(JTextField.RIGHT);
		passwordField.setEchoChar('*');
		nameField.setCaretColor(Color.RED);
		nameField.setHorizontalAlignment(JTextField.RIGHT);
		
		boxText.add(textName);
		boxText.add(Box.createVerticalStrut(15));
		boxText.add(textPassword);
		boxButtons.add(entryButton);
		boxButtons.add(Box.createHorizontalStrut(20));
		boxButtons.add(checkInButton);
		boxEnter.add(Box.createVerticalStrut(20));
		boxEnter.add(nameField);
		boxEnter.add(Box.createVerticalStrut(15));
		boxEnter.add(passwordField);
		boxEnter.add(Box.createVerticalStrut(20));
		boxAllEnter.add(Box.createHorizontalStrut(20));
		boxAllEnter.add(boxText);
		boxAllEnter.add(Box.createHorizontalStrut(20));
		boxAllEnter.add(boxEnter);
		boxAllEnter.add(Box.createHorizontalStrut(20));
		
		mainBox.add(Box.createVerticalStrut(15));
		mainBox.add(boxAllEnter);
		mainBox.add(Box.createVerticalStrut(15));
		mainBox.add(boxButtons);
		mainBox.add(Box.createVerticalStrut(15));
	}
}
