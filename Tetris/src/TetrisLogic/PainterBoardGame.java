package TetrisLogic; 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import Rplay.ReplayLastGame;
import TetrisLogic.Shape.Tetrominoes;
import Windows.PlayGameWindow;
/**
 * <p>Класс отображает игровое поле на экране.
 * И создаёт обработчик нажатия на клавиши.</p>
 * @see KeyboardHandler
 */
public class PainterBoardGame extends JPanel implements Runnable{
	/**Объект классса с логикой игры
	 * @see LogicGame */
	private LogicGame logic;
	/**Объект класса ReplaceLastGame воспроизводящий последнюю игру*/
	private ReplayLastGame lastGame = null;
	/**Координата X для отображения текущей падающей фигуры*/
	int currentX;
	/**Координата Y для отображения текущей падающей фигуры*/
	int currentY;
	/**Статус обнавления содержимого экрана*/
	private boolean isStarted = true;
	/**Поток, в котором происходит обнавление интерфейса игрового поля*/
	private Thread painterThread;
	/**Конструктор.
	 * Производится настройка отображаемого поля.
	 * Добавляется обработчик нажатых клавишь.
	 * @see KeyboardHandler
	 * @param logic объект классса с логикой игры
	 * @param caused окно, в ктором отображается игра
	 */
	public PainterBoardGame(LogicGame logic, PlayGameWindow caused){
		painterThread  = new Thread(this);
		setFocusable(true);
		this.logic = logic;
		Dimension size = new Dimension(100,200); 
		this.addKeyListener(new KeyboardHandler(logic));
		this.setMinimumSize(size);
		this.setBackground(Color.white);
		this.painterThread.start();
	}
	/**Конструктор.
	 * Производится настройка отображаемого поля.
	 * @param lastGame объект классса воспроизводящий последнюю игру
	 */
	public PainterBoardGame(ReplayLastGame lastGame){
		painterThread  = new Thread(this);
		currentX = lastGame.getComplexity().getBoardWidth()/2;
		currentY = lastGame.getComplexity().getBoardHeight() - 1 + lastGame.getCurrentFallingShape().minY();
		setFocusable(true);
		this.lastGame = lastGame;
		Dimension size = new Dimension(100,200); 
		this.setMinimumSize(size);
		this.setBackground(Color.white);
		this.painterThread.start();
	}
	
	/**Опледеляется ширина сегемента фигуры
	 * @return ширина сегемента фигуры*/
	private int squareWidth() { 
		if(lastGame == null)
			return (int) getSize().getWidth() / logic.getComplexity().getBoardWidth();
		else 
			return (int) getSize().getWidth() / lastGame.getComplexity().getBoardWidth();
	}
	
	/**Опледеляется высота сегемента фигуры
	 * @return высота сегемента фигуры*/
    int squareHeight() { 
    	if(lastGame == null)
    		return (int) getSize().getHeight() / logic.getComplexity().getBoardHeight();
    	else 
    		return (int) getSize().getHeight() / lastGame.getComplexity().getBoardHeight();
    }
	
    /**Отображение сегмента фигуры
     * @param g The Graphics class is the abstract base class for all graphics contexts that allow an application to draw onto components that are realized on various devices, as well as onto off-screen images. 
     * @param x координата X
     * @param y координата Y
     * @param shape тип рисуемой фигуры*/
    private void drawSquare(Graphics g, int x, int y, Tetrominoes shape){ 
        Color colors[] = { 
        		new Color(0, 0, 0),
        		new Color(204, 102, 102), 
        		new Color(102, 204, 102), 
        		new Color(102, 102, 204), 
        		new Color(204, 204, 102), 
        		new Color(204, 102, 204), 
        		new Color(102, 204, 204), 
        		new Color(218, 170, 0)	  
        };


        Color color = colors[shape.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                         x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
                         x + squareWidth() - 1, y + 1);
    }
    
    /**Отображение игрового поля
     * @param g The Graphics class is the abstract base class for all graphics contexts that allow an application to draw onto components that are realized on various devices, as well as onto off-screen images.*/
    public void paint(Graphics g) 
    { 
        super.paint(g);
        
        if(lastGame == null){
	        Dimension size = getSize();
	        int boardTop = (int) size.getHeight() - logic.getComplexity().getBoardHeight() * squareHeight();
	
	
	        for (int i = 0; i < logic.getComplexity().getBoardHeight(); ++i) {
	            for (int j = 0; j < logic.getComplexity().getBoardWidth(); ++j) {
	                Tetrominoes shape = logic.takeTypeCurrentFallingShape(j, logic.getComplexity().getBoardHeight() - i - 1);
	                if (shape != Tetrominoes.NoShape)
	                    drawSquare(g, 0 + j * squareWidth(),
	                               boardTop + i * squareHeight(), shape);
	            }
	        }
	        
	        if (logic.getCurrentFallingShape().getShape() != Tetrominoes.NoShape) {
	            for (int i = 0; i < logic.getCurrentFallingShape().getCountPartsShape(); ++i) {
	                int x = logic.getCurrentX() + logic.getCurrentFallingShape().getX(i);
	                int y = logic.getCurrentY() - logic.getCurrentFallingShape().getY(i);
	                drawSquare(g, 0 + x * squareWidth(),
	                           boardTop + (logic.getComplexity().getBoardHeight() - y - 1) * squareHeight(),
	                           logic.getCurrentFallingShape().getShape());
	            }
	        }
        }
        else {
        	Dimension size = getSize();
	        int boardTop = (int) size.getHeight() - lastGame.getComplexity().getBoardHeight() * squareHeight();
	
	
	        for (int i = 0; i < lastGame.getComplexity().getBoardHeight(); ++i) {
	            for (int j = 0; j < lastGame.getComplexity().getBoardWidth(); ++j) {
	                Tetrominoes shape = lastGame.takeTypeCurrentFallingShape(j, lastGame.getComplexity().getBoardHeight() - i - 1);
	                if (shape != Tetrominoes.NoShape)
	                    drawSquare(g, 0 + j * squareWidth(),
	                               boardTop + i * squareHeight(), shape);
	            }
	        }
	        
	       
	        if (lastGame.getCurrentFallingShape().getShape() != Tetrominoes.NoShape) {
	            for (int i = 0; i < lastGame.getCurrentFallingShape().getCountPartsShape(); ++i) {
	                int x = currentX + lastGame.getCurrentFallingShape().getX(i);
	                int y = currentY - lastGame.getCurrentFallingShape().getY(i);
	                drawSquare(g, 0 + x * squareWidth(),
	                           boardTop + (lastGame.getComplexity().getBoardHeight() - y - 1) * squareHeight(),
	                           lastGame.getCurrentFallingShape().getShape());
	            }
	        }
	        
        }
    }
    
    /**Метод, который вызывает перерисовку игрового поля через заданный промежуток времени*/
	@Override
	public void run() {
		while(isStarted){
			this.repaint();
			try {
				painterThread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**Метод останавливает обнавления игрового поля*/
	public void stopGame(){
		isStarted = false;
	}
 }
