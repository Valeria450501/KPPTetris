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
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import Sort.JavaSort;
import Sort.ScalaSort;
import Sort.Statistics;

public class SortGameWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JRadioButton bestScoreGameButton;
	private JRadioButton worstScoreGameButton;
	private JRadioButton bestScoreTimesButton;
	private JRadioButton worstScoreTimesButton;
	private ButtonGroup chooseGamesGroup;
	private JLabel statisticLabelText;
	private JLabel statisticsResultText;
	private JButton backButton;
	private JButton replayButton;
	private JButton sortButton;
	private Box chosenGameBox;
	private Box mainBox;
	private ScalaSort test;
	private boolean isSort = false;
	private ArrayList<Object> listOfTimes;
	private int resultOfStatistics = 0;
	
	public SortGameWindow(){
		super("Воспроизвести игру");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		createLevelButtonGroup();
		createButtons();	     
		createBoxCooseComplexity();
		createMainBox();
		
		setContentPane(new BgPanel());

        Container cont = getContentPane();
        cont.add(mainBox);
		setSize(250,400);
		this.setLocationRelativeTo(null);
		setResizable(false);
        add(new BgPanel());
        this.setVisible( true );
	}
	
	/**Создание группы кнопок выбора уровней сложности*/
	private void createLevelButtonGroup(){
		bestScoreGameButton = new JRadioButton("Лучший счёт");
		worstScoreGameButton = new JRadioButton("Худший счёт");
		bestScoreTimesButton = new JRadioButton("Самая длинная");
		worstScoreTimesButton = new JRadioButton("Самая короткая");
		chooseGamesGroup = new ButtonGroup();
		
		bestScoreGameButton.setOpaque(false);
		worstScoreGameButton.setOpaque(false);
		bestScoreTimesButton.setOpaque(false);
		worstScoreTimesButton.setOpaque(false);

		bestScoreGameButton.setFont(new Font(null,Font.BOLD ,15));
		worstScoreGameButton.setFont(new Font(null,Font.BOLD ,15));
		bestScoreTimesButton.setFont(new Font(null,Font.BOLD ,15));
		worstScoreTimesButton.setFont(new Font(null,Font.BOLD ,15));
		
		bestScoreGameButton.setForeground(Color.BLACK);
		worstScoreGameButton.setForeground(Color.BLACK);
		bestScoreTimesButton.setForeground(Color.BLACK);
		worstScoreTimesButton.setForeground(Color.BLACK);
		
		chooseGamesGroup.add(bestScoreGameButton);
		chooseGamesGroup.add(worstScoreGameButton);
		chooseGamesGroup.add(bestScoreTimesButton);
		chooseGamesGroup.add(worstScoreTimesButton);
		worstScoreGameButton.setSelected(true);
	}
	
	/**Создание кнопок*/
	private void createButtons(){
		backButton = new JButton("Назад");
		replayButton = new JButton("Играть");
		sortButton = new JButton("Сортировать");
		
		backButton.setFont(new Font(null, Font.BOLD,14));
		replayButton.setFont(new Font(null, Font.BOLD,14));
		sortButton.setFont(new Font(null, Font.BOLD,14));
		
		backButton.setBackground(Color.WHITE);
		replayButton.setBackground(Color.WHITE);
		sortButton.setBackground(Color.WHITE);		
				
		backButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		replayButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		sortButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				dispose();
				ExtraMenuWindow game = new ExtraMenuWindow();
				game.setVisible(true);
				}
		});
		
		replayButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				if(isSort){
					dispose();
					if(chosenTypeMode() == 0){
						String bestScoreGame = test.getBestOfScore();
						ReplayGameWindow wind = new ReplayGameWindow(bestScoreGame);
						wind.setVisible(true);
					} else if(chosenTypeMode() == 1){
						String worstScoreGame = test.getWorstOfScore();
						ReplayGameWindow wind = new ReplayGameWindow(worstScoreGame);
						wind.setVisible(true);
					} else if(chosenTypeMode() == 2){
						String bestScoreTimesGame = test.getBestOfTimes();
						ReplayGameWindow wind = new ReplayGameWindow(bestScoreTimesGame);
						wind.setVisible(true);
					} else if(chosenTypeMode() == 3){
						String worstScoreTimesGame = test.getWorstOfTimes();
						ReplayGameWindow wind = new ReplayGameWindow(worstScoreTimesGame);
						wind.setVisible(true);
					}
				}
			}
		});
		
		sortButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				isSort = true;
				long time = System.currentTimeMillis();
				test = new ScalaSort();
				String path = new File(".").getAbsolutePath();
				path = path.substring(0, path.length()-2);
				test.sort(path);
				time -= System.currentTimeMillis();
				time *= (-1);
				System.out.println(time);
				listOfTimes = test.getListTimes();
				Statistics statistic = new Statistics();
				statistic.setArrayList(listOfTimes);
				resultOfStatistics = statistic.foundResult();
				statisticsResultText.setText("Средняя продолжительность = \n" + Integer.toString(resultOfStatistics));
				statisticsResultText.setVisible(true);
				statisticLabelText.setVisible(true);
				time = System.currentTimeMillis();
				JavaSort testN = new JavaSort();
				path = new File(".").getAbsolutePath();
				path = path.substring(0, path.length()-2);
				testN.sort(path);
				time -= System.currentTimeMillis();
				time *= (-1);
				System.out.println(time);
			}
		});
	}
	
	private void createBoxCooseComplexity(){
		Box chosenGame = Box.createVerticalBox();
		chosenGameBox = Box.createHorizontalBox();
		TitledBorder title = new TitledBorder("Игра:");
		title.setTitleColor(Color.BLACK);
		chosenGame.setBorder(title);
		chosenGame.add(Box.createVerticalStrut(1));
		chosenGame.add(bestScoreGameButton);
		chosenGame.add(worstScoreGameButton);
		chosenGame.add(bestScoreTimesButton);
		chosenGame.add(worstScoreTimesButton);
		chosenGame.add(Box.createVerticalStrut(1));
		chosenGame.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		chosenGameBox.add(Box.createHorizontalStrut(30));
		chosenGameBox.add(chosenGame);
		chosenGameBox.add(Box.createHorizontalStrut(30));
	}
	
	private void createMainBox(){
		statisticLabelText = new JLabel("Статистика игр:");
		statisticsResultText = new JLabel();
		statisticLabelText.setVisible(false);
		statisticsResultText.setVisible(false);
		statisticLabelText.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		statisticsResultText.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		statisticLabelText.setFont(new Font(null, Font.BOLD,14));
		statisticsResultText.setFont(new Font(null, Font.BOLD,14));
		statisticLabelText.setForeground(Color.BLACK);
		statisticsResultText.setForeground(Color.BLACK);
		mainBox = Box.createVerticalBox();
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(chosenGameBox);
		mainBox.add(Box.createVerticalStrut(20));
		mainBox.add(replayButton);
		mainBox.add(Box.createVerticalStrut(20));
		mainBox.add(sortButton);
		mainBox.add(Box.createVerticalStrut(20));
		mainBox.add(backButton);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(statisticLabelText);
		mainBox.add(statisticsResultText);
	}
	
	class BgPanel extends JPanel{
		private static final long serialVersionUID = 1L;

		/**Отображение фона окна
		 * @param The Graphics class is the abstract base class for all graphics contexts that allow an application to draw onto components that are realized on various devices, as well as onto off-screen images.*/
	    public void paintComponent(Graphics g){
	        Image im = null;
	        try {
	            im = ImageIO.read(new File("extra_menu(n).jpg"));
	        } catch (IOException e) {}
	        g.drawImage(im, 0, 0, null); 
	    }
	}
	
	private int chosenTypeMode(){
		int chosenLevel = 0;
		
		if(bestScoreGameButton.isSelected() == true)
			chosenLevel = 0;
		if(worstScoreGameButton.isSelected() == true)
			chosenLevel = 1;
		if(bestScoreTimesButton.isSelected() == true)
			chosenLevel = 2;
		if(worstScoreTimesButton.isSelected() == true)
			chosenLevel = 3;
		return chosenLevel;
	}
}
