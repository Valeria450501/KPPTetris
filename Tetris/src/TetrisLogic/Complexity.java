package TetrisLogic; 
/**
 * <p>Класс, хранящий информацию о уровне сложности</p>
 */
public class Complexity {
	/**Ширина игрового поля*/
	private int boardWidth;
	/**Высото игрового поля*/
	private int boardHeight;
	/**Время падения фигуры*/
	private int timeFalling;
	private TypeComplexity choosenTypeComplexity;
	/**Тип выбранной сложности*/
	public enum TypeComplexity{
		EASY,
		NORMAL,
		HARD
	}
	/**Конструктор, заполняющий поля с информацией об игре, в зависимости от выбранной сложности
	 * @param chosenComplexity номер, соответствующий уровню сложности*/
	public Complexity(int chosenComplexity){
		switch (chosenComplexity){
		case 0: //easy
			boardWidth = 10;
			boardHeight = 22;
			timeFalling = 600;	
			choosenTypeComplexity = TypeComplexity.EASY;
			break;
		case 1:	//normal
			boardWidth = 15;
			boardHeight = 33;
			timeFalling = 400;
			choosenTypeComplexity = TypeComplexity.NORMAL;
			break;
		case 2:	//hard
			boardWidth = 20;
			boardHeight = 44;
			timeFalling = 150;			
			choosenTypeComplexity = TypeComplexity.HARD;
			break;
		}
	}
	/**Получить ширину игрового поля, определённую по выбранной сложности
	 * @return ширина игорового поля*/
	public int getBoardWidth(){
		return boardWidth;
	}
	/**Получить высоту игрового поля, определённую по выбранной сложности
	 * @return высота игорового поля*/
	public int getBoardHeight(){
		return boardHeight;
	}
	/**Получить время падения фигуры, определённое по выбранной сложности
	 * @return время падения фигуры*/
	public int getTimeFalling(){
		return timeFalling;
	}
	
	/**Получить тип выбранной сложностиъ
	 * @return choosenTypeComplexity тип выбранной сложностиъ*/
	public TypeComplexity getTypeComplexity(){
		return choosenTypeComplexity;
	}
}
