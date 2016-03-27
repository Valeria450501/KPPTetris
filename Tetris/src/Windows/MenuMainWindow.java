package Windows; 
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import TetrisLogic.Complexity;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MenuMainWindow extends JFrame {
	
	private JButton loginButton;
	private JButton gameButton;
	private JButton exitButton;
	private JRadioButton easyLevelsButton;
	private JRadioButton mediumLevelsButton; 
	private JRadioButton hardLevelsButton;
	private ButtonGroup chooseLevelsGroup;
	private Box mainBox;
	private Box chosenComplexityBox;
	
	public MenuMainWindow(){
		super("Меню");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		createLevelButtonGroup();
		createButtons();	     
		createBoxCooseComplexity();
		createMainBox();
		
		setContentPane(new BgPanel());

        Container cont = getContentPane();
        cont.add(mainBox);
		setSize(250,300);
		this.setLocationRelativeTo(null);
		setResizable(false);
        add(new BgPanel());
        this.setVisible( true );
	}
	
	private void createLevelButtonGroup(){
		easyLevelsButton = new JRadioButton("Легко");
		mediumLevelsButton = new JRadioButton("Нормально");
		hardLevelsButton = new JRadioButton("Тяжело");
		chooseLevelsGroup = new ButtonGroup();
		
		easyLevelsButton.setOpaque(false);
		mediumLevelsButton.setOpaque(false);
		hardLevelsButton.setOpaque(false);
		

		easyLevelsButton.setFont(new Font(null,Font.BOLD ,15));
		mediumLevelsButton.setFont(new Font(null,Font.BOLD ,15));
		hardLevelsButton.setFont(new Font(null,Font.BOLD ,15));
		
		easyLevelsButton.setForeground(Color.BLACK);
		mediumLevelsButton.setForeground(Color.BLACK);
		hardLevelsButton.setForeground(Color.BLACK);
		
		chooseLevelsGroup.add(easyLevelsButton);
		chooseLevelsGroup.add(mediumLevelsButton);
		chooseLevelsGroup.add(hardLevelsButton);
	
		mediumLevelsButton.setSelected(true);
		
	}
	private void createButtons(){
		loginButton = new JButton("Войти");
		gameButton = new JButton("Играть");
		exitButton = new JButton("Выход");
		
		loginButton.setFont(new Font(null, Font.BOLD,14));
		gameButton.setFont(new Font(null, Font.BOLD,14));
		exitButton.setFont(new Font(null, Font.BOLD,14));
		
		loginButton.setBackground(Color.WHITE);
		gameButton.setBackground(Color.WHITE);
		exitButton.setBackground(Color.WHITE);		
		
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				AuthorizationWindow AuthorizWindow = new AuthorizationWindow(getThis());
				AuthorizWindow.setVisible(true);
				}
		});
		
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				if (JOptionPane.showConfirmDialog(exitButton, "Вы уверены, что хотите выйти?") == JOptionPane.YES_OPTION) 
					System.exit(0);
				}
		});
		
		gameButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				dispose();
				PlayGameWindow game = new PlayGameWindow(new Complexity(chooseLevel()));
				game.setVisible(true);
				}
		});
		
		loginButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		gameButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		exitButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
	}
		
	private void createBoxCooseComplexity(){
		Box chosenComplexity = Box.createVerticalBox();
		chosenComplexityBox = Box.createHorizontalBox();
		TitledBorder title = new TitledBorder("Сложность:");
		title.setTitleColor(Color.BLACK);
		chosenComplexity.setBorder(title);
		chosenComplexity.add(Box.createVerticalStrut(1));
		chosenComplexity.add(easyLevelsButton);
		chosenComplexity.add(mediumLevelsButton);
		chosenComplexity.add(hardLevelsButton);
		chosenComplexity.add(Box.createVerticalStrut(1));
		chosenComplexity.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		chosenComplexityBox.add(Box.createHorizontalStrut(30));
		chosenComplexityBox.add(chosenComplexity);
		chosenComplexityBox.add(Box.createHorizontalStrut(30));
	}
	private void createMainBox(){
		mainBox = Box.createVerticalBox();
		mainBox.add(Box.createVerticalStrut(5));
		mainBox.add(loginButton);
		mainBox.add(Box.createVerticalStrut(20));
		mainBox.add(chosenComplexityBox);
		mainBox.add(Box.createVerticalStrut(20));
		mainBox.add(gameButton);
		mainBox.add(Box.createVerticalStrut(20));
		mainBox.add(exitButton);
		mainBox.add(Box.createVerticalStrut(5));	
	}
	
	public int chooseLevel(){
		int chosenLevel = 0;
		
		if(easyLevelsButton.isSelected() == true)
			chosenLevel = 0;
		if(mediumLevelsButton.isSelected() == true)
			chosenLevel = 1;
		if(hardLevelsButton.isSelected() == true)
			chosenLevel = 2;
		
		return chosenLevel;
	}
	public MenuMainWindow getThis(){
		return this;
	}
	class BgPanel extends JPanel{
	    public void paintComponent(Graphics g){
	        Image im = null;
	        try {
	            im = ImageIO.read(new File("MainMenu(n).jpg"));
	        } catch (IOException e) {}
	        g.drawImage(im, 0, 0, null); 
	    }
	}
	
}
