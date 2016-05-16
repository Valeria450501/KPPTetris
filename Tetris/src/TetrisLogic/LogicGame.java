package TetrisLogic; 

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

import Bot.RandomBot;
import FileWorking.FileLogicReplace;
import FileWorking.TextFile;
import TetrisLogic.Shape.Tetrominoes;
import Windows.MenuMainWindow;
import Windows.PlayGameWindow;

public class LogicGame implements ActionListener {
	/**��������� ���������*/
	private Complexity chosenComplexity;
	/**������� �������� ������*/
	private Shape currentFallingShape;
	/**��������� �������� ������*/
	private Shape nextFallingShape;
	/**����� ������, ����������� �� ����������� �������� ����
	 * @see PainterBoardGame*/
	private PainterBoardGame paintBoard;
	/**������� ����*/
	private Tetrominoes[] boardTetrominoes;
	/**���� � ����������� � ��������� �������� ������*/
	private Tetrominoes[] boardNextShape;
	/**������������ ������� ������� ������*/
	private boolean isFallingFinished = false;
	/**������������ �������� ����. (���� ��������)*/
	private boolean isStarted = false;
	/**������������ �������� ����. (���� �����������)*/
	private boolean isPaused = false;
	/**����� ���� � �����*/
	private boolean isBot = false;
	/**���� ������*/
	private int score;
	/**������� ���������� x*/
	private int currentX = 0;
	/**������� ���������� y*/
	private int currentY = 0;
	/**������ � �������� ���������� ����*/
	private Timer timer;
	/**������ ����, ���������� ���� ������
	 * @see PlayGameWindow*/
	private PlayGameWindow caused;
	/**�����, ������������ �� ������ ��������� ������*/
	private PaintNextShape drawNextShape;
	/**�����, ���������� �� ������ ���� ��� ������������ ��������������� ��������� ����
	 * @see PaintNextShape*/
	private FileLogicReplace fileToWrite;
	
	/**
	 *�����������.
	 *���������� �������� ������, ������ ������� ����, ��������� ������.
	 *�������� ��������������� �����(�������� � ��������� �������� ������)
	 *@param complexity ��������� ���������
	 *@param caused	������ ����, ���������� ���� ������  
	 */
	public LogicGame(Complexity complexity, PlayGameWindow caused) {
		this.caused = caused;
		chosenComplexity = complexity;
		currentFallingShape = new Shape();
		nextFallingShape = new Shape();
		
		boardTetrominoes = new Tetrominoes[chosenComplexity.getBoardWidth()*chosenComplexity.getBoardHeight()];
		boardNextShape = new Tetrominoes[nextFallingShape.getCountPartsShape()*nextFallingShape.getCountPartsShape()];
		timer = new Timer(chosenComplexity.getTimeFalling(),this);
		timer.start();
		paintBoard = new PainterBoardGame(this,caused);
		paintBoard.setVisible(true);;
		drawNextShape = new PaintNextShape(this);
		drawNextShape.setVisible(true);
		clearBoardTetrominoes();
		clearBoardNextShape();
	}
	
	/**����� ���� ����*/
	public void playBot(){
		isBot = true;
	}
	
	/**����������� ���� ������� �������� ������
	 * @param x ���������� x ������� �������� ������
	 * @param y ���������� y ������� �������� ������
	 * @return ��� ������� �������� ������
	 * */
	public Tetrominoes takeTypeCurrentFallingShape(int x, int y){
		return boardTetrominoes[y*chosenComplexity.getBoardWidth()+x];
	}
	
	/**����������� ���� ��������� �������� ������
	 * @param x ���������� x ��������� �������� ������
	 * @param y ���������� y ��������� �������� ������
	 * @return ��� ��������� �������� ������
	 * */
	public Tetrominoes takeTypeNextFallingShape(int x, int y){
		return boardNextShape[y*nextFallingShape.getCountPartsShape()+x];
	}
	
	/**������ �������� ��������*/
	public void start() { 
        if (isPaused)
            return;

        isStarted = true;
        isFallingFinished = false;
        score = 0;
        clearBoardTetrominoes();
        clearBoardNextShape();

        newShape();
        timer.start();
    }

	/**��������� �������� ��������*/
	public void pause(){ 
        if (!isStarted)
            return;

        isPaused = !isPaused;
        if (isPaused) {
            timer.stop();
        } else {
            timer.start();
        }
        paintBoard.repaint();
        drawNextShape.repaint();
    }

	/**����� ���� ����������� �������� ������ �� �������� ����, ���� ������ ����� �������� ������.
	 * ��� ���� � ����� �������� ��������, ������������ ��� �������.
	 * ���������� ��� ������������ �������*/
	public void actionPerformed(ActionEvent e) {	
        if (isFallingFinished) {
            isFallingFinished = false;
            newShape();
        } else {
        	if(isBot == true){
        		RandomBot bot = new RandomBot(this);
        	}
            oneLineDown();
        }
    }
	
	/**�������� ����� ������ � ������ � ���� ���� ����, ��������� � ������� �����*/
	private void newShape(){
		fileToWrite = new FileLogicReplace(boardTetrominoes, getComplexity(), score);
		
		if(nextFallingShape.getShape() == Tetrominoes.NoShape){
			fileToWrite.createFile();
			fileToWrite.addTofile("NewGame");
			fileToWrite.writeComplexity(chosenComplexity);
			nextFallingShape.setRandomShape();
			fileToWrite.setNextShape(nextFallingShape);
			fileToWrite.setCurrentShape(currentFallingShape);
			fileToWrite.addToFile();
			currentFallingShape.setShape(nextFallingShape.getShape());
			nextFallingShape.setRandomShape();
			fileToWrite.setCurrentShape(currentFallingShape);
			fileToWrite.setNextShape(nextFallingShape);
			fileToWrite.addToFile();
		}
		else {
			currentFallingShape.setShape(nextFallingShape.getShape());
			fileToWrite.setCurrentShape(currentFallingShape);
			nextFallingShape.setRandomShape();
			fileToWrite.setNextShape(nextFallingShape);
			fileToWrite.addToFile();
		}		
		
		currentX = chosenComplexity.getBoardWidth()/2;
		currentY = chosenComplexity.getBoardHeight() - 1 + currentFallingShape.minY();
				
		if (!tryMove(currentFallingShape, currentX, currentY)) {
            currentFallingShape.setShape(Tetrominoes.NoShape);
            nextFallingShape.setShape(Tetrominoes.NoShape);
            timer.stop();
            isStarted = false;
        }
	}

	/**����������� ������ �� ���� ����� ����*/
	public void oneLineDown(){	
		if(!tryMove(currentFallingShape, currentX, currentY - 1))
			shapeLand();
	}
	
	/**����������� ������ ���� �� �������, �� ������� ��� ��������*/
	public void shapeFalling(){ 
		int newY = currentY;
		while (newY > 0) {
            if (!tryMove(currentFallingShape, currentX, newY - 1))
                break;
            --newY;
        }
        shapeLand();
	}
	
	/**������� ����������� ������� �������� ������ �� �������� �����������
	 * @param fallingShape ������, ������� ����������� �� �������� �����������
	 * @param newX ����� ���������� x
	 * @param newY ����� ���������� y
	 * @return ������� �� ����������� ������
	 * */
	public boolean tryMove(Shape fallingShape, int newX, int newY){ 
		for (int i = 0; i < fallingShape.getCountPartsShape(); ++i) {
            int x = newX + fallingShape.getX(i);
            int y = newY - fallingShape.getY(i);
            if (x < 0 || x >= chosenComplexity.getBoardWidth() || y < 0 || y >= chosenComplexity.getBoardHeight())
                return false;
            if(takeTypeCurrentFallingShape(x,y)!=Tetrominoes.NoShape)
            	return false;
        }

        currentFallingShape = fallingShape;
        currentX = newX;
        currentY = newY;
        paintBoard.repaint();
        drawNextShape.repaint();
        return true;
	}
	
	/**"�����������" ������.
	 * ����������, ����� ������ ������ �� ����� ��������� ����*/
	private void shapeLand(){ 
		for (int i = 0; i < currentFallingShape.getCountPartsShape(); ++i) {
            int x = currentX + currentFallingShape.getX(i);
            int y = currentY - currentFallingShape.getY(i);
            boardTetrominoes[(y * chosenComplexity.getBoardWidth()) + x] = currentFallingShape.getShape();
        }

        cleanFullLines();

        if (!isFallingFinished)
            newShape();
	}

	/**������� ����������� �����*/
	private void cleanFullLines(){ 
		int numberFullLines = 0;
		
		for (int i = chosenComplexity.getBoardHeight() - 1; i >= 0; --i) {
            boolean lineIsFull = true;

            for (int j = 0; j < chosenComplexity.getBoardWidth(); ++j) {
                if (takeTypeCurrentFallingShape(j, i) == Tetrominoes.NoShape) {
                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {
                ++numberFullLines;
                for (int k = i; k < chosenComplexity.getBoardHeight() - 1; ++k) {
                    for (int j = 0; j < chosenComplexity.getBoardWidth(); ++j)
                         boardTetrominoes[(k * chosenComplexity.getBoardWidth()) + j] = takeTypeCurrentFallingShape(j, k + 1);
                }
            }
		}
        
        if (numberFullLines > 0) {
            score += numberFullLines;
            isFallingFinished = true;
            currentFallingShape.setShape(Tetrominoes.NoShape);
            paintBoard.repaint();
            drawNextShape.repaint();
            caused.setScore(score);
        }
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
		return chosenComplexity;
	}
	
	/**�������� �������� ������
	 * @return �������� ������*/
	public Shape getCurrentFallingShape(){
		return currentFallingShape;
	}
	
	/**�������� ������ ������� ����
	 * @return ������ ������� ����*/
	public boolean getStartStatus(){
		return isStarted;
	}
	
	/**�������� ������ ��������� ����
	 * @return ������ ��������� ����*/
	public boolean getPauseStatus(){
		return isPaused;
	}
	
	/**�������� ������� X ����������
	 * @return ������� X ����������*/
	public int getCurrentX(){
		return currentX;
	}
	
	/**�������� ������� Y ����������
	 * @return ������� Y ����������*/
	public int getCurrentY(){
		return currentY;
	}
	
	/**�������� ������, ����������� ����������� ���� ����
	 * @return ������, ����������� ����������� ���� ����*/
	public PainterBoardGame getPainter(){
		return paintBoard;
	}
	
	/**�������� ���� ������
	 * @return ���� ������*/
	public int getScore(){
		return score;
	}
	
	/**�������� ��������� �������� ������
	 * @return ��������� �������� ������*/
	public Shape getNextShape(){
		return nextFallingShape;
	}
	
	/**�������� ������, ����������� ����������� ���� ��������� �������� ������
	 * @return ������, ����������� ����������� ���� ��������� �������� ������*/
	public PaintNextShape getDrawNextShape(){
		return drawNextShape;
	}	
}
