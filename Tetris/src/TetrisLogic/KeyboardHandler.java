package TetrisLogic; 

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import TetrisLogic.Shape.Tetrominoes;

/**<p>Класс, обрабатывающий нажатия кнопок клавиатуры</p> */
class KeyboardHandler extends KeyAdapter{
	/**Объект классса с логикой игры, в которм содержатся методы, передвигающие фигуру
	 * @see LogicGame */
	private LogicGame logic;
	
	/**Конструктор.
	 * Вызывает метод по определению дальнейших действий над падающей фигурой
	 * @param logic Объект класса, содержащего логику игры	 */
	public KeyboardHandler(LogicGame logic){
		this.logic = logic;
	}
	
	/**Срабатывает при нажатии кнпок клавиатуры и выполняет действия, определяемые нажатой клавишей*/
	public void keyPressed(KeyEvent e) {

         if (!logic.getStartStatus() || logic.getCurrentFallingShape().getShape() == Tetrominoes.NoShape) {  
             return;
         }

         int keycode = e.getKeyCode();

         if (keycode == 'p' || keycode == 'P') {
             logic.pause();
             return;
         }

         if (logic.getPauseStatus())
             return;

         switch (keycode) {
         case KeyEvent.VK_LEFT:
             logic.tryMove(logic.getCurrentFallingShape(), logic.getCurrentX() - 1, logic.getCurrentY());
             break;
         case KeyEvent.VK_RIGHT:
             logic.tryMove(logic.getCurrentFallingShape(), logic.getCurrentX() + 1, logic.getCurrentY());
             break;
         case KeyEvent.VK_DOWN:
             logic.tryMove(logic.getCurrentFallingShape().rotateRight(), logic.getCurrentX(), logic.getCurrentY());
             break;
         case KeyEvent.VK_UP:
             logic.tryMove(logic.getCurrentFallingShape().rotateLeft(), logic.getCurrentX(), logic.getCurrentY());
             break;
         case KeyEvent.VK_SPACE:
             logic.shapeFalling();
             break;
         case 'd':
             logic.oneLineDown();
             break;
         case 'D':
             logic.oneLineDown();
             break;
         }

    }
 
}
