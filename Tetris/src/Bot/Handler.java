package Bot;

import TetrisLogic.LogicGame;

/**
 * <p>Обработчик действий, которые определяются в классе RandomBot.</p>
 * @see RandomBot
*/
public class Handler {
	/**Объект классса с логикой игры, в которм содержатся методы, передвигающие фигуру
	 * @see LogicGame */
	private LogicGame logic;
	/**<p>Конструктор</p>
	 * @param logic Kласс, финкции которго позволяют передвигать фигуру
	 * @see LogicGame */
	public Handler(LogicGame logic){
		this.logic = logic;
	}
	/**<p>Передвигаем фигуру вправо</p>*/
	public void moveRight(){
		logic.tryMove(logic.getCurrentFallingShape(), logic.getCurrentX() + 1, logic.getCurrentY());
	}
	/**<p>Передвигаем фигуру влево</p>*/
	public void moveLeft(){
		logic.tryMove(logic.getCurrentFallingShape(), logic.getCurrentX() - 1, logic.getCurrentY());
	}
	/**<p>Вращаем фигуру вправо</p>*/
	public void moveDown(){
		logic.tryMove(logic.getCurrentFallingShape().rotateRight(), logic.getCurrentX(), logic.getCurrentY());
	}
	/**<p>Вращаем фигуру влево</p> */
	public void moveUp(){
		logic.tryMove(logic.getCurrentFallingShape().rotateLeft(), logic.getCurrentX(), logic.getCurrentY());
	}
	/**<p>Фигура опускается на минимальную позицию</p>*/
	public void moveSpace(){
		logic.shapeFalling();
	}
	/**<p>Передвигаем фигуру на одну линию вниз</p>*/
	public void moveOneLineDown(){
		logic.oneLineDown();
	}
}
