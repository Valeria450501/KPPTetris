package Windows;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import TetrisLogic.Complexity;
import Windows.MenuMainWindow.BgPanel;

public class ExtraMenuWindow extends JFrame {
	/**������ ��� ����*/
	private JButton botButton;
	/**������ ������ ��������������� ����*/
	private JButton playButton;
	/**������ ������ � ������� ����*/
	private JButton backButton;
	/**������ ������ ������ ������*/
	private JRadioButton easyLevelsButton;
	/**������ ������ �������� ������*/
	private JRadioButton mediumLevelsButton;
	/**������ ������ �������� ������*/
	private JRadioButton hardLevelsButton;
	/**������ ������ ������ ������*/
	private ButtonGroup chooseLevelsGroup;
	/**Box ���� �������� ����*/
	private Box mainBox;
	/**Box ��������� ������ ������*/
	private Box chosenComplexityBox;
	/**������ ������ ��������������� ����� ������ ����*/
	private JRadioButton badLastGameButton;
	/**������ ������ ��������������� ������ ����*/
	private JRadioButton bestLastGameButton;
	/**������ ������ ��������������� ������ ����*/
	private JRadioButton lastGameButton;
	/**������ ������ ������ ��������������� ����*/
	private ButtonGroup chooseGameButtonsGroup;
	/**Box ��������� ������ ��������������� ����*/
	private Box chosenLastGameBox;
	
	/**�����������.
	 * ��������� ����.
	 * �������� ���� ����������� ��������.*/
	public ExtraMenuWindow(){
		super("�������������� ����");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		createLevelButtonGroup();
		createButtons();	     
		createBoxCooseComplexity();
		createBoxCooseTypeLastGame();
		createMainBox();
		
		setContentPane(new BgPanel());

        Container cont = getContentPane();
        cont.add(mainBox);
		setSize(230,430);
		this.setLocationRelativeTo(null);
		setResizable(false);
        add(new BgPanel());
        this.setVisible( true );
	}
	
	/**�������� ������ ������ ������ ������� ��������� � ������ ������ ������������� ����*/
	private void createLevelButtonGroup(){
		easyLevelsButton = new JRadioButton("�����");
		mediumLevelsButton = new JRadioButton("���������");
		hardLevelsButton = new JRadioButton("������");
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
		
		badLastGameButton = new JRadioButton("������");
		bestLastGameButton = new JRadioButton("������");
		lastGameButton = new JRadioButton("���������");
		chooseGameButtonsGroup = new ButtonGroup();
		
		badLastGameButton.setOpaque(false);
		bestLastGameButton.setOpaque(false);
		lastGameButton.setOpaque(false);
		

		badLastGameButton.setFont(new Font(null,Font.BOLD ,15));
		bestLastGameButton.setFont(new Font(null,Font.BOLD ,15));
		lastGameButton.setFont(new Font(null,Font.BOLD ,15));
		
		badLastGameButton.setForeground(Color.BLACK);
		bestLastGameButton.setForeground(Color.BLACK);
		lastGameButton.setForeground(Color.BLACK);
		
		chooseGameButtonsGroup.add(badLastGameButton);
		chooseGameButtonsGroup.add(bestLastGameButton);
		chooseGameButtonsGroup.add(lastGameButton);
	
		lastGameButton.setSelected(true);
		
	}
	
	/**�������� ������*/
	private void createButtons(){
		botButton = new JButton(" ��� ");
		playButton = new JButton("������");
		backButton = new JButton("�����");
		
		botButton.setFont(new Font(null, Font.BOLD,14));
		playButton.setFont(new Font(null, Font.BOLD,14));
		backButton.setFont(new Font(null, Font.BOLD,14));
		
		botButton.setBackground(Color.WHITE);
		playButton.setBackground(Color.WHITE);
		backButton.setBackground(Color.WHITE);		
		
		botButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				dispose();
				PlayGameWindow game = new PlayGameWindow(new Complexity(chooseLevel()));
				game.playBot();
				game.setVisible(true);
				}
		});
		
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				dispose();
				MenuMainWindow newMain = new MenuMainWindow();
				newMain.setVisible(true);
				}
		});
		
		playButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				dispose();
				ReplayGameWindow some = new ReplayGameWindow();
				some.setVisible(true);
				}
		});
		
		botButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		playButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		backButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
	}
	
	/**�������� Box ������ ������ ������ ������ ���������*/
	private void createBoxCooseComplexity(){
		Box chosenComplexity = Box.createVerticalBox();
		chosenComplexityBox = Box.createHorizontalBox();
		TitledBorder title = new TitledBorder("���������:");
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
	
	/**�������� Box ������ ������ ������ ��������������� ����*/
	private void createBoxCooseTypeLastGame(){
		Box chosenGame = Box.createVerticalBox();
		chosenLastGameBox = Box.createHorizontalBox();
		TitledBorder title = new TitledBorder("��� ����:");
		title.setTitleColor(Color.BLACK);
		chosenGame.setBorder(title);
		chosenGame.add(Box.createVerticalStrut(1));
		chosenGame.add(badLastGameButton);
		chosenGame.add(bestLastGameButton);
		chosenGame.add(lastGameButton);
		chosenGame.add(Box.createVerticalStrut(1));
		chosenGame.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		chosenLastGameBox.add(Box.createHorizontalStrut(30));
		chosenLastGameBox.add(chosenGame);
		chosenLastGameBox.add(Box.createHorizontalStrut(30));
	}
	
	/**�������� Box ���� ���������, ����������� � ����*/
	private void createMainBox(){
		mainBox = Box.createHorizontalBox();
		mainBox.add(Box.createHorizontalStrut(25));
		Box tempBox = Box.createVerticalBox();
		tempBox.add(Box.createVerticalStrut(5));
		tempBox.add(chosenLastGameBox);
		tempBox.add(Box.createVerticalStrut(20));
		tempBox.add(playButton);
		tempBox.add(Box.createVerticalStrut(20));
		tempBox.add(chosenComplexityBox);
		tempBox.add(Box.createVerticalStrut(20));
		tempBox.add(botButton);
		tempBox.add(Box.createVerticalStrut(20));
		tempBox.add(backButton);
		tempBox.add(Box.createVerticalStrut(5));
		mainBox.add(tempBox);
		mainBox.add(Box.createHorizontalStrut(25));
	}
	
	/**��������� ��������� ���������
	 * @return ��������� ���������*/
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

	
	/**<p>�����, ������������ ��� ���� MenuMainWindow</p>*/
	class BgPanel extends JPanel{
		/**����������� ���� ����
		 * @param The Graphics class is the abstract base class for all graphics contexts that allow an application to draw onto components that are realized on various devices, as well as onto off-screen images.*/
	    public void paintComponent(Graphics g){
	        Image im = null;
	        try {
	            im = ImageIO.read(new File("extra_menu(n).jpg"));
	        } catch (IOException e) {}
	        g.drawImage(im, 0, 0, null); 
	    }
	}
}
