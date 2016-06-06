package Bot;

import java.util.Random;

import TetrisLogic.LogicGame;
/**
 * <p>Бот, работа которого основывается на случайном выборе действия</p>
 */
public class RandomBot {
	/**Объект классса с логикой игры, в которм содержатся методы, передвигающие фигуру
	 * @see LogicGame */
	private LogicGame logic;
	/**Объект класса Random, определяющий случайное действие*/
	Random getTempWay;
	/**Выбор числа, соответствующего номеру действия */
	int position;
	/**Объект класса, содержащего действия над фигурой
	 * @see Handler*/
	Handler emptyHandler;
	
	/**
	 * Конструктор.
	 * Вызывает метод по определению дальнейших действий над падающей фигурой
	 * @see getWay
	 * @param logic Объект класса, содержащего логику игры
	 */
	public RandomBot(LogicGame logic){
		this.logic = logic;
		getWay();
	}
	
	/**Определение действия над фигурой и выполняет их через объект emptyHandler*/
	private void getWay(){
		getTempWay = new Random();
		position = getTempWay.nextInt(7);
		emptyHandler = new Handler(logic);
		
		switch(position){
		case 0:
			break;
		case 1:
			emptyHandler.moveDown();
			break;
		case 2:
			emptyHandler.moveLeft();
			break;
		case 3:
			emptyHandler.moveOneLineDown();
			break;
		case 4:
			emptyHandler.moveRight();
			break;
		case 5:
			emptyHandler.moveSpace();
			break;
		case 6:
			emptyHandler.moveUp();
			break;
		default:
			break;
		}	
	}
	public void notExeption(){}
}
