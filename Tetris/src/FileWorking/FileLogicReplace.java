package FileWorking;

import TetrisLogic.Complexity;
import TetrisLogic.Complexity.typeComplexity;
import TetrisLogic.Shape;
import TetrisLogic.Shape.Tetrominoes;

public class FileLogicReplace {
	/**Игровое поле, которое записывают в файл, лиюо из него считывают*/
	private Tetrominoes[] boardTetrominoes;
	/**счёт игрока*/
	private int score;
	/**Выбранная сложность*/
	private Complexity complexity;
	/**Следующая фигура*/
	private Shape nextShape;
	/**Текущая фигура*/
	private Shape currentShape;
	/**Класс, работающий с файлом*/
	private TextFile file;
	/**Статус остановки игры*/
	private boolean isStoped = false;
	
	/**Конструктор класса для записи игры
	 * @param boardTetrominoes игровое поле
	 * @param compexity сложность
	 * @param score счёт*/
	public FileLogicReplace(Tetrominoes[] boardTetrominoes, Complexity compexity, int score){
		this.boardTetrominoes = boardTetrominoes;
		this.complexity = compexity;
		boardTetrominoes = new Tetrominoes[complexity.getBoardHeight()*complexity.getBoardWidth()];
		file = new TextFile();
		this.score = score;
	}
	
	/**Конструкот класса для воспроизведения игры*/
	public FileLogicReplace(){
		nextShape = new Shape();
		currentShape = new Shape();
		file = new TextFile();
	}
	
	/**Прочитать следующее игровое действие*/
	public void readOneAction() {
		String readString = file.readOneBoardFromBeg();
		try{
			if(readString != null && !readString.equals("NewGame")){
				decrypt(readString);
				score = Integer.valueOf(file.readOneBoardFromBeg()); 
				String next = file.readOneBoardFromBeg();
				nextShape.setShape(this.getTypeShapeTetrominoes(next.charAt(0)));
				currentShape.setShape(this.getTypeShapeTetrominoes(next.charAt(1)));
			}
			else
				isStoped = !isStoped;
		}catch(Exception e){
			System.out.println(readString);
		}
	}
	
	/**Добавление типа сложности
	 * @param toWrite записываемая в файл сложность*/
	public void writeComplexity(Complexity toWrite){
		switch(toWrite.getTypeComplexity()){
		case Easy:
			file.addToFile("Easy"+"\n");
			break;
		case Normal:
			file.addToFile("Normal"+"\n");
			break;
		case Hard:
			file.addToFile("Hard"+"\n");
			break;
		}
	}
	
	/**Считывание сложности из файла
	 * @return сложность*/
	public Complexity getComplexity(){
		Complexity temp = null;
		String line = file.readOneBoardFromBeg();
		switch(line){
		case "Easy":
			temp = new Complexity(0);
			break;
		case "Normal":
			temp = new Complexity(1);
			break;
		case "Hard":
			temp = new Complexity(2);
			break;
		}
		this.complexity = temp;
		boardTetrominoes = new Tetrominoes[complexity.getBoardHeight()*complexity.getBoardWidth()];
		return temp;
	}

	/** Преобразование фигуры в символ, обозначающий её тип
	 * @return символ, который означает тип фигуры
	 * @param oneTetrominoes тип фигуры*/
	private String getTypeShapeChar(Tetrominoes oneTetrominoes){
		String type = null;
		switch(oneTetrominoes.toString()){
		case "NoShape":
			type = "N";
			break;
		case "ZShape":
			type = "Z";
			break;
		case "SShape":
			type = "S";
			break;
		case "LineShape":
			type = "I";
			break;
		case "TShape":
			type = "T";
			break;
		case "SquareShape":
			type = "Q";
			break;
		case "LShape":
			type = "L";
			break;
		case "MirroredLShape":
			type = "M";
			break;
		}
		return type;
	}
	
	/** Преобразование символа, обозначающего тип, в фигуру
	 * @return тип фигуры
	 * @param oneChar символ, который означает тип фигуры*/
	private Tetrominoes getTypeShapeTetrominoes(char oneChar){
		Tetrominoes type = null;
		switch(oneChar){
		case 'N':
			 type = Tetrominoes.NoShape;
			break;
		case 'Z':
			type = Tetrominoes.ZShape;
			break;
		case 'S':
			type = Tetrominoes.SShape;
			break;
		case 'I':
			type = Tetrominoes.LineShape;
			break;
		case 'T':
			type = Tetrominoes.TShape;
			break;
		case 'Q':
			type = Tetrominoes.SquareShape;
			break;
		case 'L':
			type = Tetrominoes.LShape;
			break;
		case 'M':
			type = Tetrominoes.MirroredLShape;
			break;
		}
		return type;
	}
	
	/**Добавиьт игровое действие в файл*/
	public void addToFile(){
		for(int j=0; j < complexity.getBoardHeight(); j++)
			for(int i=0; i < complexity.getBoardWidth(); i++)
				file.addToFile(this.getTypeShapeChar(this.boardTetrominoes[j*complexity.getBoardWidth()+i]));
		file.addToFile("\n");
		this.addTofile(Integer.toString(score));
		file.addToFile(this.getTypeShapeChar(nextShape.getShape()));
		file.addToFile(this.getTypeShapeChar(currentShape.getShape()));
		file.addToFile("\n");
	}
	
	/**Добавить заданную строку в файл
	 * @param someString строка для записи*/
	public void addTofile(String someString){
		file.addToFile(someString);
		file.addToFile("\n");
	}
	/**Расшифровка игрового действия
	 * @param some строка с полем игры*/
	private void decrypt(String some){
		int tempBoardHeight = complexity.getBoardHeight();
		int tempBoardWidth = complexity.getBoardWidth();
		for(int j=0; j < tempBoardHeight; j++)
			for(int i=0; i < tempBoardWidth; i++)
				boardTetrominoes[i+j*tempBoardWidth] = this.getTypeShapeTetrominoes(some.charAt(i+j*tempBoardWidth));
		
	}
	
	/** Установка другой следующей фигуры
	 * @param nextShape другая следующая фигура*/
	public void setNextShape(Shape nextShape){
		this.nextShape = nextShape;
	}
	
	/** Установка другой текущей фигуры
	 * @param currentShape другая текущая фигура*/
	public void setCurrentShape(Shape currentShape){
		this.currentShape = currentShape;
	}
	
	/**Создание файла*/
	public void createFile(){
		file.CreateFile();
	}
	
	/**Получить игровое поле
	 * @return игровое поле*/
	public Tetrominoes[] getBoard(){
		return boardTetrominoes;
	}
	
	/**Установка нового счёта
	 * @param newScore новый счёт*/
	public void setNewScore(int newScore){
		score = newScore;
	}
	
	/**Получить тип следующей фигуры
	 * @return тип следующей фигуры*/
	public Shape getLastNextShape(){
		return nextShape;
	}
	/**Получить последний счёт игры
	 * @return score последний счёт игры*/
	public int getScore(){
		return score;
	}

	/**Получение типа текущей падающей финуры
	 * @return текущая падающая фигура*/
	public Shape getNewCurrentShape(){
		return currentShape;
	}

	/**Настройка необходимых параметров для воспроизведения игры*/
	public void letsGoReplay(){
		file.searchBeginning();
	}
	
	/**Получить статус остановки игры
	 * @return игра остановлена - true*/
	public boolean getStatusStoped(){
		return isStoped;
	}
	
	}
