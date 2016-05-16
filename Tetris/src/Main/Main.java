package Main; 

import javax.swing.*;

import FileWorking.TextFile;
import Windows.MenuMainWindow;

/**Класс Main, с кторого начинается запуск игры*/
public class Main extends JFrame {
	/**Конструктор.
	 * Создаёт объект класса MenuMainWindow
	 * @see MenuMainWindow
	 * @param args массив из строк с аргументами*/
	public static void main(String []args) {
		MenuMainWindow menu = new MenuMainWindow(); 
		menu.setVisible(true);
		
	}

}
