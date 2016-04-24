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
	/**Кнопка иры бота*/
	private JButton botButton;
	/**Кнопка игры пользователя*/
	private JButton gameButton;
	/**Кнопка выхода из игры*/
	private JButton exitButton;
	/**Кнопка выбора лёгкого уровня*/
	private JRadioButton easyLevelsButton;
	/**Кнопка выбора среднего уровня*/
	private JRadioButton mediumLevelsButton;
	/**Кнопка выбора сложного уровня*/
	private JRadioButton hardLevelsButton;
	/**Группа кнопок выбора уровня*/
	private ButtonGroup chooseLevelsGroup;
	/**Box всех элеметов окна*/
	private Box mainBox;
	/**Box элементов выбора уровня*/
	private Box chosenComplexityBox;
	
	/**Конструктор.
	 * Настройка окна.
	 * Создание всех необходимых элеметов.*/
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
	
	/**Создание группы кнопок выбора уровней сложности*/
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
	
	/**Создание кнопок*/
	private void createButtons(){
		botButton = new JButton(" Бот ");
		gameButton = new JButton("Играть");
		exitButton = new JButton("Выход");
		
		botButton.setFont(new Font(null, Font.BOLD,14));
		gameButton.setFont(new Font(null, Font.BOLD,14));
		exitButton.setFont(new Font(null, Font.BOLD,14));
		
		botButton.setBackground(Color.WHITE);
		gameButton.setBackground(Color.WHITE);
		exitButton.setBackground(Color.WHITE);		
		
		botButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				dispose();
				PlayGameWindow game = new PlayGameWindow(new Complexity(chooseLevel()));
				
				game.setVisible(true);
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
		
		botButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		gameButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		exitButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
	}
	
	/**Создание Box группы кнопок выбора уровня сложности*/
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
	
	/**Создание Box всех элементов, размещаемых в окне*/
	private void createMainBox(){
		mainBox = Box.createVerticalBox();
		mainBox.add(Box.createVerticalStrut(5));
		mainBox.add(botButton);
		mainBox.add(Box.createVerticalStrut(20));
		mainBox.add(chosenComplexityBox);
		mainBox.add(Box.createVerticalStrut(20));
		mainBox.add(gameButton);
		mainBox.add(Box.createVerticalStrut(20));
		mainBox.add(exitButton);
		mainBox.add(Box.createVerticalStrut(5));	
	}
	
	/**Получение выбранной сложности
	 * @return выбранная сложность*/
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
	
	/**Получение объекта this
	 * @return объект this*/
	public MenuMainWindow getThis(){
		return this;
	}
	
	/**<p>Класс, отображающий фон окна MenuMainWindow</p>*/
	class BgPanel extends JPanel{
		/**Отображение фона окна
		 * @param The Graphics class is the abstract base class for all graphics contexts that allow an application to draw onto components that are realized on various devices, as well as onto off-screen images.*/
	    public void paintComponent(Graphics g){
	        Image im = null;
	        try {
	            im = ImageIO.read(new File("MainMenu(n).jpg"));
	        } catch (IOException e) {}
	        g.drawImage(im, 0, 0, null); 
	    }
	}
	
}
