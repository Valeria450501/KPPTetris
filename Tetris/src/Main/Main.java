package Main; 

import javax.swing.*;

import Windows.MenuMainWindow;


/**����� Main, � ������� ���������� ������ ����*/
public class Main extends JFrame {
	
	/**�����������.
	 * ������ ������ ������ MenuMainWindow
	 * @see MenuMainWindow
	 * @param args ������ �� ����� � �����������*/
	public static void main(String []args) {
		MenuMainWindow menu = new MenuMainWindow();
		menu.setVisible(true);
	}

}
