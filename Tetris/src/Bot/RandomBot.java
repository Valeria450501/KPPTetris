package Bot;

import java.util.Random;

import TetrisLogic.LogicGame;
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
