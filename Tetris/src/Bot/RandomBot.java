package Bot;

import java.util.Random;
import TetrisLogic.Complexity;
import TetrisLogic.LogicGame;
import TetrisLogic.Shape;
import TetrisLogic.Shape.Tetrominoes;
/**
 * <p>���, ������ �������� ������������ �� ��������� ������ ��������</p>
 */
public class RandomBot {
	/**������ ������� � ������� ����, � ������ ���������� ������, ������������� ������
	 * @see LogicGame */
	private LogicGame logic;
	/**������ ������ Random, ������������ ��������� ��������*/
	Random getTempWay;
	/**����� �����, ���������������� ������ �������� */
	int position;
	/**������ ������, ����������� �������� ��� �������
	 * @see Handler*/
	Handler emptyHandler;
	
	/**
	 * �����������.
	 * �������� ����� �� ����������� ���������� �������� ��� �������� �������
	 * @see getWay
	 * @param logic ������ ������, ����������� ������ ����
	 */
	public RandomBot(LogicGame logic){
		this.logic = logic;
		getWay();
	}
	
	/**����������� �������� ��� ������� � ��������� �� ����� ������ emptyHandler*/
	private void getWay(){
		getTempWay = new Random();
		position = getTempWay.nextInt(6);
		emptyHandler = new Handler(logic);
		
		switch(position){
		case 1:
			break;
		case 2:
			emptyHandler.moveDown();
			break;
		case 3:
			emptyHandler.moveLeft();
			break;
		case 4:
			emptyHandler.moveOneLineDown();
			break;
		case 5:
			emptyHandler.moveRight();
			break;
		case 6:
			emptyHandler.moveSpace();
			break;
		case 7:
			emptyHandler.moveUp();
			break;
		}	
}
}
