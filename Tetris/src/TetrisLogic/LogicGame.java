package TetrisLogic; 

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.Timer;

import Bot.RandomBot;
import FileWorking.FileLogicReplace;
import TetrisLogic.Shape.Tetrominoes;
import Windows.PlayGameWindow;

public class LogicGame implements ActionListener{
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
	/**ServerSocket ��� ������� ����� �������� � ��������*/
	private static ServerSocket server;
	/**����� �������*/
	private Thread clientThread;
	/**������ ������ ��������� �� �������*/
	private BufferedReader bufRead;
	/**������, ������������ ��������� �������*/
	private PrintStream printStream;
	/**Socket ��� ������� ����� �������� � ��������*/
	private Socket socket;
	
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
        if (!isStarted){
            return;
        }

        isPaused = !isPaused;
        if (isPaused) {
            timer.stop();
        } else {
            timer.start();
        }
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
	
	/**��������, ������� ���������� ��������� ��� ������ ������ � ����*/
	private void firsTime(){
		fileToWrite = new FileLogicReplace(boardTetrominoes, getComplexity(), score);
		clientThread = new Thread(fileToWrite);
		clientThread.start();
		try	{
			server = new ServerSocket(25256); // ����� ������ 2525
		} catch(Exception e) {
			System.out.println("ERRSOCK+"+e); 
		}
			socket = null;
			try {
				socket = server.accept(); // �������� ���������� � ��������
			} catch(Exception e) {
				System.out.println("ACCEPTER"+e);
			}
			
			try	{
				bufRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				printStream = new PrintStream(socket.getOutputStream());
				printStream.println("CREATE_FILE"); 
				printStream.flush();
				while(!bufRead.readLine().equals("make"));
			} catch(Exception e) {
				System.out.println("PSERROR"+e);
			}
	}
	
	/**�������� ����� ������ � ������ � ���� ���� ����, ��������� � ������� �����*/
	private void newShape(){
		
		if(nextFallingShape.getShape() == Tetrominoes.NoShape){
			firsTime();
			fileToWrite.writeComplexity(chosenComplexity);
			nextFallingShape.setRandomShape();
			fileToWrite.setNextShape(nextFallingShape);
			fileToWrite.setCurrentShape(currentFallingShape);
			printStream.println("ADD_TO_FILE"); 
			printStream.flush();
			try {
				while(!bufRead.readLine().equals("make"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			currentFallingShape.setShape(nextFallingShape.getShape());
			nextFallingShape.setRandomShape();
			fileToWrite.setCurrentShape(currentFallingShape);
			fileToWrite.setNextShape(nextFallingShape);
			printStream.println("ADD_TO_FILE"); 
			printStream.flush();		
			try {
				while(!bufRead.readLine().equals("make"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			fileToWrite.setBoard(boardTetrominoes);
			fileToWrite.setNewScore(score);
			currentFallingShape.setShape(nextFallingShape.getShape());
			fileToWrite.setCurrentShape(currentFallingShape);
			nextFallingShape.setRandomShape();
			fileToWrite.setNextShape(nextFallingShape);
			try{
				printStream.println("ADD_TO_FILE"); 
				printStream.flush();
			} catch(Exception e) {
				System.out.println("PSERROR"+e);
			}		
			try {
				while(!bufRead.readLine().equals("make"));
			} catch (IOException e) {
				e.printStackTrace();
			}
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
	
	public void exitFromGame(){
		paintBoard.stopGame();
		drawNextShape.stopGame();
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
	
	/**��������, ������������ ��� ���������� ����*/
	public void close(){
		printStream.println("EXIT"); 
		printStream.flush();
		printStream.close();
		try {
			bufRead.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
