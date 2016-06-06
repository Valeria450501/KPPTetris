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
/**����� �������� � �� ���������� �����, � �� �� ���������������
 * */
public class ReplayLastGame implements ActionListener {
	/**���� ������*/
	private int score = 0;
	/**����, � ������� ������������ ������� ����
	 * @see ReplayGameWindow*/
	private ReplayGameWindow caused;
	/**������� �������� ������*/
	private Shape currentFallingShape;
	/**��������� �������� ������*/
	private Shape nextFallingShape;
	/**��������� ���������*/
	private Complexity chosenComplexity;
	/**���� ����*/
	private Tetrominoes boardTetrominoes[];
	/**���� ��������� ������*/
	private Tetrominoes boardNextShape[];
	/**������ ��� ����������� ���������� ����*/
	private Timer timer;
	/**�����, ������������ �� ������ ��������� ������*/
	private PaintNextShape drawNextShape;
	/**����� ������, ����������� �� ����������� �������� ����
	 * @see PainterBoardGame*/
	private PainterBoardGame paintBoard;
	/**������ ������, ���������� �� ������ ����� �������, ��������������� ����, � �������, ���������� � ������
	 * @see FileLogicReplace
	 * @see TextFile*/
	FileLogicReplace some;
	
	/**����������� ������
	 * @param caused ����, � ������� ������������ ����*/
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
	
	/**��������, ������� ���� �����������
	 * @param e ������ ������ ActionEvent
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
	
	/**�������� ������ ��������� ����
	 * @return ���� ����������� - true*/
	public boolean getStatusStopping(){
		return (!timer.isRunning());
	}
	
	/**�������� ������, ����������� ����������� ���� ����
	 * @return ������, ����������� ����������� ���� ����*/
	public PainterBoardGame getPainter(){
		return paintBoard;
	}
	
	/**�������� ������, ����������� ����������� ���� ��������� �������� ������
	 * @return ������, ����������� ����������� ���� ��������� �������� ������*/
	public PaintNextShape getDrawNextShape(){
		return drawNextShape;
	}
	/**�������� ����
	 * @return ����*/
	public int getScore(){
		return score;
	}
	
	/**����������� ��������������� ����*/
	public void setStop(){
		timer.stop();
	}

	/**�������� ��� ������� �������� ������
	 * @return ��� ������� �������� ������*/
	public Shape getCurrentFallingShape() {
		return currentFallingShape;
	}

	/**�������� ��� ������� �������� ������
	 * @param x ���������� x
	 * @param y ���������� y
	 * @return ��� ������� �������� ������*/
	public Tetrominoes takeTypeCurrentFallingShape(int x, int y){
		return boardTetrominoes[y*chosenComplexity.getBoardWidth()+x];
	}
	
	/**�������� ��� ������ �� �������� ����������� � ������� ����
	 * @param x ���������� x
	 * @param y ���������� y
	 * @return ��� ������*/
	public Tetrominoes takeTypeNextFallingShape(int x, int y) {
		return boardNextShape[y*nextFallingShape.getCountPartsShape()+x]; 
	}

	/**������� �������� ����*/
	private void clearBoardTetrominoes(){ 
		for (int i = 0; i < chosenComplexity.getBoardHeight() * chosenComplexity.getBoardWidth(); ++i)
            boardTetrominoes[i] = Tetrominoes.NoShape;
	}
	
	/**������� ���� ��������� �������� ������*/
	private void clearBoardNextShape(){ 
		for (int i = 0; i < nextFallingShape.getCountPartsShape() * nextFallingShape.getCountPartsShape(); ++i)
            boardNextShape[i] = Tetrominoes.NoShape;
	}
	
	/**�������� ��������� ���������
	 * @return ��������� ���������*/
	public Complexity getComplexity(){
		return this.chosenComplexity;
	}
	
	/**�������� ��� ��������� �������� ������
	 * @return ��� ��������� �������� ������*/
	public Shape getNextShape(){
		return nextFallingShape;
	}
	
	/**��������� ������ �������� ����
	 * @param newTetrominoes ����� ������� ����*/
	public void setBoard(Tetrominoes[] newTetrominoes){
		this.boardTetrominoes = newTetrominoes;		
	}
	
	/**��������� ������ �������� �����
	 * @param newScore ����� ������� ����*/
	public void setScore(int newScore){
		score = newScore;
	}
	
	/**�������� ����� close() ������� FileLogicReplace*/
	public void close(){
		some.close();
	}
}
