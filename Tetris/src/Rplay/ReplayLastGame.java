package Rplay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import FileWorking.FileLogicReplace;
import TetrisLogic.Complexity;
import TetrisLogic.PaintNextShape;
import TetrisLogic.PainterBoardGame;
import TetrisLogic.Shape;
import TetrisLogic.Shape.Tetrominoes;
import Windows.ReplayGameWindow;
/**Класс отвечает и за сохранение логов, и за их воспроизведение
 * */
public class ReplayLastGame implements ActionListener {
	/**Счёт игрока*/
	private int score = 0;
	/**Окно, в котором отображается игровое поле
	 * @see ReplayGameWindow*/
	private ReplayGameWindow caused;
	/**Текущая падающая фигура*/
	private Shape currentFallingShape;
	/**Следующая падающая фигура*/
	private Shape nextFallingShape;
	/**Выбранная сложность*/
	private Complexity chosenComplexity;
	/**Поле игры*/
	private Tetrominoes boardTetrominoes[];
	/**Поле следующей фигуры*/
	private Tetrominoes boardNextShape[];
	/**Таймер для определения следующего хода*/
	private Timer timer;
	/**Класс, отображающий на экране следующую фигуру*/
	private PaintNextShape drawNextShape;
	/**Объкт класса, отвечающего за отображение игрового поля
	 * @see PainterBoardGame*/
	private PainterBoardGame paintBoard;
	/**Объект класса, отвечающий за работу между классом, воспроизводящим игру, и классом, работающим с файлом
	 * @see FileLogicReplace
	 * @see TextFile*/
	FileLogicReplace some;
	
	/**Конструктор класса
	 * @param caused окно, в котором отображается игра*/
	public ReplayLastGame (ReplayGameWindow caused){
		this.caused = caused;
		currentFallingShape = new Shape();
		nextFallingShape = new Shape();
		some = new FileLogicReplace();
		some.letsGoReplay();
		chosenComplexity = some.getComplexity();
		int boardTetrominoesSize = chosenComplexity.getBoardWidth()*chosenComplexity.getBoardHeight();
		int boardNextShapeSize = nextFallingShape.getCountPartsShape()*nextFallingShape.getCountPartsShape();
		boardTetrominoes = new Tetrominoes[boardTetrominoesSize];
		boardNextShape = new Tetrominoes[boardNextShapeSize];
		timer = new Timer(1000,this);
		
		timer.start();
		paintBoard = new PainterBoardGame(this);
		paintBoard.setVisible(true);;
		drawNextShape = new PaintNextShape(this);
		drawNextShape.setVisible(true);
		clearBoardTetrominoes();
		clearBoardNextShape();
	}
	
	public ReplayLastGame (ReplayGameWindow caused, String playThisGame){
		this.caused = caused;
		currentFallingShape = new Shape();
		nextFallingShape = new Shape();
		some = new FileLogicReplace(playThisGame);
		some.letsGoReplay();
		chosenComplexity = some.getComplexity();
		int boardTetrominoesSize = chosenComplexity.getBoardWidth()*chosenComplexity.getBoardHeight();
		int boardNextShapeSize = nextFallingShape.getCountPartsShape()*nextFallingShape.getCountPartsShape();
		boardTetrominoes = new Tetrominoes[boardTetrominoesSize];
		boardNextShape = new Tetrominoes[boardNextShapeSize];
		timer = new Timer(1000,this);
		
		timer.start();
		paintBoard = new PainterBoardGame(this);
		paintBoard.setVisible(true);;
		drawNextShape = new PaintNextShape(this);
		drawNextShape.setVisible(true);
		clearBoardTetrominoes();
		clearBoardNextShape();
	}
	
	/**Действия, которые буду повторяться
	 * @param e объект класса ActionEvent
	 * @see ActionEvent*/
	public void actionPerformed(ActionEvent e) {	
		some.readOneAction();
		if(some.getStatusStoped()) 
			timer.stop();
		else {
			setBoard(some.getBoard());
			setScore(some.getScore());
	
			currentFallingShape = some.getNewCurrentShape();
			caused.setScore(score);
			nextFallingShape = some.getLastNextShape();
			this.paintBoard.repaint();
			this.drawNextShape.repaint();
			
		}
    }
	
	/**Получить статус остановки игры
	 * @return игра остановлена - true*/
	public boolean getStatusStopping(){
		return (!timer.isRunning());
	}
	
	/**Получить объект, реализующий отображение поля игры
	 * @return объект, реализующий отображение поля игры*/
	public PainterBoardGame getPainter(){
		return paintBoard;
	}
	
	/**Получить объект, реализующий отображение поля следующей падающей фигуры
	 * @return объект, реализующий отображение поля следующей падающей фигуры*/
	public PaintNextShape getDrawNextShape(){
		return drawNextShape;
	}
	/**Получить счёт
	 * @return счёт*/
	public int getScore(){
		return score;
	}
	
	/**Остановитиь воспроизведение игры*/
	public void setStop(){
		timer.stop();
	}

	/**Получить тип текущей падающей фигуры
	 * @return тип текущей падающей фигуры*/
	public Shape getCurrentFallingShape() {
		return currentFallingShape;
	}

	/**Получить тип текущей падающей фигуры
	 * @param x координата x
	 * @param y координата y
	 * @return тип текущей падающей фигуры*/
	public Tetrominoes takeTypeCurrentFallingShape(int x, int y){
		return boardTetrominoes[y*chosenComplexity.getBoardWidth()+x];
	}
	
	/**Получить тип фигуры по заданным координатам в игровом поле
	 * @param x координата x
	 * @param y координата y
	 * @return тип фигуры*/
	public Tetrominoes takeTypeNextFallingShape(int x, int y) {
		return boardNextShape[y*nextFallingShape.getCountPartsShape()+x]; 
	}

	/**Очистка игрового поля*/
	private void clearBoardTetrominoes(){ 
		for (int i = 0; i < chosenComplexity.getBoardHeight() * chosenComplexity.getBoardWidth(); ++i)
            boardTetrominoes[i] = Tetrominoes.NoShape;
	}
	
	/**Очистка поля следующей падающей фигуры*/
	private void clearBoardNextShape(){ 
		for (int i = 0; i < nextFallingShape.getCountPartsShape() * nextFallingShape.getCountPartsShape(); ++i)
            boardNextShape[i] = Tetrominoes.NoShape;
	}
	
	/**Получить выбранную сложность
	 * @return выбранная сложность*/
	public Complexity getComplexity(){
		return this.chosenComplexity;
	}
	
	/**Получить тип следующей падающей фигуры
	 * @return тип следующей падающей фигуры*/
	public Shape getNextShape(){
		return nextFallingShape;
	}
	
	/**Установка нового игрового поля
	 * @param newTetrominoes новое игровое поле*/
	public void setBoard(Tetrominoes[] newTetrominoes){
		this.boardTetrominoes = newTetrominoes;		
	}
	
	/**Установка нового игрового счёта
	 * @param newScore новый игровой счёт*/
	public void setScore(int newScore){
		score = newScore;
	}
	
	/**Вызываем метод close() объекта FileLogicReplace*/
	public void close(){
		some.close();
	}
}
