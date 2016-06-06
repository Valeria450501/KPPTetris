package Bot;

import TetrisLogic.LogicGame;

/**
 * <p>���������� ��������, ������� ������������ � ������ RandomBot.</p>
 * @see RandomBot
*/
public class Handler {
	/**������ ������� � ������� ����, � ������ ���������� ������, ������������� ������
	 * @see LogicGame */
	private LogicGame logic;
	/**<p>�����������</p>
	 * @param logic K����, ������� ������� ��������� ����������� ������
	 * @see LogicGame */
	public Handler(LogicGame logic){
		this.logic = logic;
	}
	/**<p>����������� ������ ������</p>*/
	public void moveRight(){
		logic.tryMove(logic.getCurrentFallingShape(), logic.getCurrentX() + 1, logic.getCurrentY());
	}
	/**<p>����������� ������ �����</p>*/
	public void moveLeft(){
		logic.tryMove(logic.getCurrentFallingShape(), logic.getCurrentX() - 1, logic.getCurrentY());
	}
	/**<p>������� ������ ������</p>*/
	public void moveDown(){
		logic.tryMove(logic.getCurrentFallingShape().rotateRight(), logic.getCurrentX(), logic.getCurrentY());
	}
	/**<p>������� ������ �����</p> */
	public void moveUp(){
		logic.tryMove(logic.getCurrentFallingShape().rotateLeft(), logic.getCurrentX(), logic.getCurrentY());
	}
	/**<p>������ ���������� �� ����������� �������</p>*/
	public void moveSpace(){
		logic.shapeFalling();
	}
	/**<p>����������� ������ �� ���� ����� ����</p>*/
	public void moveOneLineDown(){
		logic.oneLineDown();
	}
}
