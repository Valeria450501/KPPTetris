package TetrisLogic; 
/**
 * <p> ласс, хран€щий информацию о уровне сложности</p>
 */
public class Complexity {
	/**Ўирина игрового пол€*/
	private int boardWidth;
	/**¬ысото игрового пол€*/
	private int boardHeight;
	/**¬рем€ падени€ фигуры*/
	private int timeFalling;
	/** онструктор, заполн€ющий пол€ с информацией об игре, в зависимости от выбранной сложности
	 * @param chosenComplexity номер, соответствующий уровню сложности*/
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
	/**ѕолучить ширину игрового пол€, определЄнную по выбранной сложности
	 * @return ширина игорового пол€*/
	public int getBoardWidth(){
		return boardWidth;
	}
	/**ѕолучить высоту игрового пол€, определЄнную по выбранной сложности
	 * @return высота игорового пол€*/
	public int getBoardHeight(){
		return boardHeight;
	}
	/**ѕолучить врем€ падени€ фигуры, определЄнное по выбранной сложности
	 * @return врем€ падени€ фигуры*/
	public int getTimeFalling(){
		return timeFalling;
	}
}
