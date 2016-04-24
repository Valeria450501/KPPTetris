package TetrisLogic; 
/**
 * <p>�����, �������� ���������� � ������ ���������</p>
 */
public class Complexity {
	/**������ �������� ����*/
	private int boardWidth;
	/**������ �������� ����*/
	private int boardHeight;
	/**����� ������� ������*/
	private int timeFalling;
	/**�����������, ����������� ���� � ����������� �� ����, � ����������� �� ��������� ���������
	 * @param chosenComplexity �����, ��������������� ������ ���������*/
	public Complexity(int chosenComplexity){
		switch (chosenComplexity){
		case 0: //easy
			boardWidth = 10;
			boardHeight = 22;
			timeFalling = 600;			
			break;
		case 1:	//normal
			boardWidth = 15;
			boardHeight = 33;
			timeFalling = 400;			
			break;
		case 2:	//hard
			boardWidth = 20;
			boardHeight = 44;
			timeFalling = 150;			
			break;
		}
	}
	/**�������� ������ �������� ����, ����������� �� ��������� ���������
	 * @return ������ ��������� ����*/
	public int getBoardWidth(){
		return boardWidth;
	}
	/**�������� ������ �������� ����, ����������� �� ��������� ���������
	 * @return ������ ��������� ����*/
	public int getBoardHeight(){
		return boardHeight;
	}
	/**�������� ����� ������� ������, ����������� �� ��������� ���������
	 * @return ����� ������� ������*/
	public int getTimeFalling(){
		return timeFalling;
	}
}
