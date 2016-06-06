package Windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Rplay.ReplayLastGame;

public class ReplayGameWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	/**Счёт игрока*/
	private JLabel scoreLabel;
	/**Кнопка выхода из игры*/
	private JButton exitButton;
	/**Содержащий все элементы окна*/
	private Box mainBox;
	/**Объект классса, отвечающий за логику воспроизведения игры
	 * @see ReplayLastGame */
	private ReplayLastGame tetris;
	
	/**Конструктор.
	 * Создание и размещение всех элементов, находящихся в окне.*/
	public ReplayGameWindow(){
		super("Тетрис");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		tetris = new ReplayLastGame(this);
		createMainBox();		
		setContentPane(new BgPanel());
		getContentPane().setLayout(new FlowLayout());
		Dimension size = new Dimension(300,550); ;
		tetris.getPainter().setPreferredSize(size);
		getContentPane().add(tetris.getPainter(), BorderLayout.WEST);

		getContentPane().add(mainBox, BorderLayout.EAST);
		tetris.getDrawNextShape().setPreferredSize(new Dimension(40,60));
		this.getContentPane().setBackground(Color.white);
		setSize(450,600);
		setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	public ReplayGameWindow(String playThissGame){
		super("Тетрис");
		tetris = new ReplayLastGame(this, playThissGame);
		createMainBox();		
		setContentPane(new BgPanel());
		getContentPane().setLayout(new FlowLayout());
		Dimension size = new Dimension(300,550); ;
		tetris.getPainter().setPreferredSize(size);
		getContentPane().add(tetris.getPainter(), BorderLayout.WEST);

		getContentPane().add(mainBox, BorderLayout.EAST);
		tetris.getDrawNextShape().setPreferredSize(new Dimension(40,60));
		this.getContentPane().setBackground(Color.white);
		setSize(450,600);
		setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	/**Создание Box, содержащего все элементы, размещающиеся в окне*/
	public void createMainBox(){
		scoreLabel = new JLabel("Счёт: "+tetris.getScore());
		scoreLabel.setForeground(Color.white);
		exitButton = new JButton("Выход");
		
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				dispose();
				MenuMainWindow newMain = new MenuMainWindow();
				newMain.setVisible(true);
				tetris.close();
			}
		});
		exitButton.setPreferredSize(new Dimension(80,25));
		Box oneBox = Box.createVerticalBox();
		oneBox.add(Box.createVerticalStrut(5));

		oneBox.add(Box.createVerticalStrut(5));
		oneBox.add(scoreLabel);
		oneBox.add(Box.createVerticalStrut(5));
		oneBox.add(tetris.getDrawNextShape());
		oneBox.add(Box.createVerticalStrut(300));
		oneBox.add(Box.createVerticalStrut(25));
		oneBox.add(exitButton);
		oneBox.add(Box.createVerticalStrut(50));
		
		mainBox = Box.createHorizontalBox();
		mainBox.add(Box.createHorizontalStrut(10));
		mainBox.add(oneBox);
		mainBox.add(Box.createHorizontalStrut(10));
	}
	
	/**Изменение scoreLabel, который должен обнавляться при каждом изменении score
	 * @param newScore новое значение, которое будет отображаться в scoreLabel*/
	public void setScore(int newScore){
		scoreLabel.setText("Счёт: "+newScore);
	}
	
	/**<p>Класс, отображающий фон окна PlayGameWindow</p>*/
	class BgPanel extends JPanel{
		private static final long serialVersionUID = 1L;

		/**Отображение фона окна
		 * @param The Graphics class is the abstract base class for all graphics contexts that allow an application to draw onto components that are realized on various devices, as well as onto off-screen images.*/
	    public void paintComponent(Graphics g){
	        Image im = null;
	        try {
	            im = ImageIO.read(new File("p.jpg"));
	        } catch (IOException e) {}
	        g.drawImage(im, 0, 0, null); 
	    }
	}
}
