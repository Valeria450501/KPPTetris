package TetrisLogic; //(+)

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import TetrisLogic.Shape.Tetrominoes;
import Windows.MenuMainWindow;
import Windows.PlayGameWindow;

public class LogicGame implements ActionListener {
	private Complexity chosenComplexity;
	private Shape currentFallingShape;
	private Shape nextFallingShape;
	private Painter paintBoard;
	private Tetrominoes[] boardTetrominoes;
	private Tetrominoes[] boardNextShape;
	private boolean isFallingFinished = false;
	private boolean isStarted = false;
	private boolean isPaused = false;
	private int score;
	private int currentX = 0;
	private int currentY = 0;
	private Timer timer;
	private PlayGameWindow caused;
	
	public LogicGame(Complexity complexity, PlayGameWindow caused) {
		this.caused = caused;
		chosenComplexity = complexity;
		currentFallingShape = new Shape();
		nextFallingShape = new Shape();
		timer = new Timer(chosenComplexity.getTimeFalling(),this);
		timer.start();
		boardTetrominoes = new Tetrominoes[chosenComplexity.getBoardWidth()*chosenComplexity.getBoardHeight()];
		boardNextShape = new Tetrominoes[nextFallingShape.getCountPartsShape()*nextFallingShape.getCountPartsShape()];
		paintBoard = new Painter(this,caused);
		paintBoard.setVisible(true);;
		clearBoardTetrominoes();
		clearBoardNextShape();
	}
	
	public Tetrominoes takeTypeCurrentFallingShape(int x, int y){
		return boardTetrominoes[y*chosenComplexity.getBoardWidth()+x];
	}
	
	public Tetrominoes takeTypeNextFallingShape(int x, int y){
		return boardNextShape[y*nextFallingShape.getCountPartsShape()+x];
	}
	
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
    }
		
	public void actionPerformed(ActionEvent e) {	//или передвинутся на строку вниз, или не падать и прекрипиться к тем, кто уже есть(новая фигура)
        if (isFallingFinished) {
            isFallingFinished = false;
            newShape();
        } else {
            oneLineDown();
        }
    }
	
	private void newShape(){ 
		if(currentFallingShape != nextFallingShape) {
			nextFallingShape.setRandomShape();
			currentFallingShape = nextFallingShape;
		}
		else{
			currentFallingShape = nextFallingShape;
			nextFallingShape.setRandomShape();
		}
		for (int i = 0; i < nextFallingShape.getCountPartsShape(); ++i) {
            int x = nextFallingShape.getX(i);
            int y = nextFallingShape.getY(i);
        }
		
		currentX = chosenComplexity.getBoardWidth()/2;
		currentY = chosenComplexity.getBoardHeight() - 1 + currentFallingShape.minY();
		
		if (!tryMove(currentFallingShape, currentX, currentY)) {
            currentFallingShape.setShape(Tetrominoes.NoShape);
            timer.stop();
            isStarted = false;
        }
	}
	
	public void oneLineDown(){	
		if(!tryMove(currentFallingShape, currentX, currentY - 1))
			shapeLand();
	}

	public void shapeFalling(){ 
		int newY = currentY;
		while (newY > 0) {
            if (!tryMove(currentFallingShape, currentX, newY - 1))
                break;
            --newY;
        }
        shapeLand();
	}
	
	public boolean tryMove(Shape fallingShape, int newX, int newY){ //(+)
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
        return true;
	}
	
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
            caused.setScore(score);
        }
	}
	
	private void clearBoardTetrominoes(){ 
		for (int i = 0; i < chosenComplexity.getBoardHeight() * chosenComplexity.getBoardWidth(); ++i)
            boardTetrominoes[i] = Tetrominoes.NoShape;
	}
	
	private void clearBoardNextShape(){ 
		for (int i = 0; i < nextFallingShape.getCountPartsShape() * nextFallingShape.getCountPartsShape(); ++i)
            boardNextShape[i] = Tetrominoes.NoShape;
	}
		
	public Complexity getComplexity(){
		return chosenComplexity;
	}
	
	public Shape getCurrentFallingShape(){
		return currentFallingShape;
	}
	
	public boolean getStartStatus(){
		return isStarted;
	}
	
	public boolean getPauseStatus(){
		return isPaused;
	}
	
	public int getCurrentX(){
		return currentX;
	}
	
	public int getCurrentY(){
		return currentY;
	}
	
	public Painter getPainter(){
		return paintBoard;
	}
	
	public int getScore(){
		return score;
	}
	public Shape getNextShape(){
		return nextFallingShape;
	}
}
