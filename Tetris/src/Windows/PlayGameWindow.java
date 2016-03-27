package Windows;

import javax.imageio.ImageIO;
import javax.swing.*;
import TetrisLogic.Complexity;
import TetrisLogic.LogicGame;
import Windows.MenuMainWindow.BgPanel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class PlayGameWindow extends JFrame{
	private JLabel scoreLabel;
	private JLabel nameLabel;
	private JButton pauseButton;
	private JButton exitButton;
	private JLabel bestScoreLabel;

	private Box mainBox;
	private LogicGame tetris;
	public PlayGameWindow(Complexity level){
		super("Тетрис");
		tetris = new LogicGame(level,this);
		createMainBox();		
		setContentPane(new BgPanel());
		getContentPane().setLayout(new FlowLayout());
		Dimension size = new Dimension(300,550); ;
		tetris.getPainter().setPreferredSize(size);
		getContentPane().add(tetris.getPainter(), BorderLayout.WEST);
		getContentPane().add(mainBox, BorderLayout.EAST);
		tetris.start();
		this.getContentPane().setBackground(Color.white);
		setSize(400,600);
		setResizable(false);
		this.setLocationRelativeTo(null);
	}
	public void createMainBox(){
		scoreLabel = new JLabel("Счёт: "+tetris.getScore());
		pauseButton = new JButton("Пауза ");
		pauseButton.setPreferredSize(new Dimension(80,25));
		exitButton = new JButton("Выход");
		
		pauseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				if(tetris.getStartStatus())
					tetris.pause();
				else 
					tetris.start();
			}
		});
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				dispose();
				MenuMainWindow newMain = new MenuMainWindow();
				newMain.setVisible(true);
			}
		});
		exitButton.setPreferredSize(new Dimension(80,25));
		mainBox = Box.createVerticalBox();
		mainBox.add(Box.createVerticalStrut(5));
		mainBox.add(scoreLabel);
		mainBox.add(Box.createVerticalStrut(400));
		mainBox.add(pauseButton);
		mainBox.add(Box.createVerticalStrut(25));
		mainBox.add(exitButton);
		mainBox.add(Box.createVerticalStrut(50));
	}
	
	public void setScore(int newScore){
		scoreLabel.setText("Счёт: "+newScore);
	}
	class BgPanel extends JPanel{
	    public void paintComponent(Graphics g){
	        Image im = null;
	        try {
	            im = ImageIO.read(new File("p.jpg"));
	        } catch (IOException e) {}
	        g.drawImage(im, 0, 0, null); 
	    }
	}
	
	

}
