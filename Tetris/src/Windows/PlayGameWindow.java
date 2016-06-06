package Windows;

import javax.imageio.ImageIO;
import javax.swing.*;
import TetrisLogic.Complexity;
import TetrisLogic.LogicGame;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class PlayGameWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	/**���� ������*/
	private JLabel scoreLabel;
	/**������ ������ �� ����*/
	private JButton exitButton;
	/**���������� ��� �������� ����*/
	private Box mainBox;
	/**������ ������� � ������� ����, � ������ ���������� ������, ������������� ������
	 * @see LogicGame */
	private LogicGame tetris;
	
	/**�����������.
	 * �������� � ���������� ���� ���������, ����������� � ����.
	 * @param level ��������� ���������*/
	public PlayGameWindow(Complexity level){
		super("������");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		tetris = new LogicGame(level,this);;
		createMainBox();		
		setContentPane(new BgPanel());
		getContentPane().setLayout(new FlowLayout());
		Dimension size = new Dimension(300,550); ;
		tetris.getPainter().setPreferredSize(size);
		getContentPane().add(tetris.getPainter(), BorderLayout.WEST);

		getContentPane().add(mainBox, BorderLayout.EAST);
		tetris.getDrawNextShape().setPreferredSize(new Dimension(40,60));		
		tetris.start();
		this.getContentPane().setBackground(Color.white);
		setSize(450,600);
		setResizable(false);
		this.setLocationRelativeTo(null);
	}
	/**����� ���*/
	public void playBot(){
		tetris.playBot();
	}
	
	/**�������� Box, ����������� ��� ��������, ������������� � ����*/
	public void createMainBox(){
		scoreLabel = new JLabel("����: "+tetris.getScore());
		scoreLabel.setForeground(Color.white);
		exitButton = new JButton("���� ");
		
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				dispose();
				tetris.pause();
				tetris.exitFromGame();
				tetris.close();
				MenuMainWindow newMain = new MenuMainWindow();
				newMain.setVisible(true);
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
	
	/**��������� scoreLabel, ������� ������ ����������� ��� ������ ��������� score
	 * @param newScore ����� ��������, ������� ����� ������������ � scoreLabel*/
	public void setScore(int newScore){
		scoreLabel.setText("����: "+newScore);
	}
	
	/**<p>�����, ������������ ��� ���� PlayGameWindow</p>*/
	class BgPanel extends JPanel{
		private static final long serialVersionUID = 1L;

		/**����������� ���� ����
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
