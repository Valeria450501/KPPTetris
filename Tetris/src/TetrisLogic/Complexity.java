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
	private typeComplexity choosenTypeComplexity;
	/**Тип выбранной сложности*/
	public enum typeComplexity{
		Easy,
		Normal,
		Hard
	}
	/**Конструктор, заполняющий поля с информацией об игре, в зависимости от выбранной сложности
	 * @param chosenComplexity номер, соответствующий уровню сложности*/
	public Complexity(int chosenComplexity){
		switch (chosenComplexity){
		case 0: //easy
			boardWidth = 10;
			boardHeight = 22;
			timeFalling = 600;	
			choosenTypeComplexity = typeComplexity.Easy;
			break;
		case 1:	//normal
			boardWidth = 15;
			boardHeight = 33;
			timeFalling = 400;
			choosenTypeComplexity = typeComplexity.Normal;
			break;
		case 2:	//hard
			boardWidth = 20;
			boardHeight = 44;
			timeFalling = 150;			
			choosenTypeComplexity = typeComplexity.Hard;
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
	public typeComplexity getTypeComplexity(){
		return choosenTypeComplexity;
	}
}
