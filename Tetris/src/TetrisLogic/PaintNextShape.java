package TetrisLogic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import Rplay.ReplayLastGame;
import TetrisLogic.Shape.Tetrominoes;

/**
 * <p>Класс отображает игровое поле на экране.</p>
 */
public class PaintNextShape extends JPanel implements Runnable{
	/**Статус обнавления содержимого экрана*/
	private boolean isStarted = true;
	/**Поток, в котором происходит обнавление интерфейса игрового поля*/
	Thread painterThread = new Thread(this);
	/**Следующая падающая фигура
	 * @see Shape*/
	private Shape nextShape;
	/**Объект классса с логикой игры
	 * @see LogicGame */
	private LogicGame tetris = null;
	/**Объект классса воспроизводящий последнюю игру
	 * @see ReplayLastGame */
	private ReplayLastGame lastGame = null;
	/**Конструктор.
	 * Производится настройка отображаемого поля.
	 * @see KeyboardHandler
	 * @param tetris объект классса с логикой игры
	 */
	public PaintNextShape(LogicGame tetris){
		setFocusable(true);
		this.nextShape = tetris.getNextShape();
		this.tetris = tetris;
		this.setBackground(Color.white);
		this.painterThread.start();
	}
	/**Конструктор.
	 * Производится настройка отображаемого поля.
	 * @param lastGame объект класса возобнавляющий последнюю игру
	 */
	public PaintNextShape(ReplayLastGame lastGame){
		setFocusable(true);
		this.nextShape = lastGame.getNextShape();
		this.lastGame = lastGame;
		this.setBackground(Color.white);
		this.painterThread.start();
	}
	
	/**Опледеляется ширина сегемента фигуры
	 * @return ширина сегемента фигуры*/
	private int squareWidth() { 
		return (int) getSize().getWidth() / (nextShape.getCountPartsShape()*2) + 1;
	}
	
	/**Опледеляется высота сегемента фигуры
	 * @return высота сегемента фигуры*/
	int squareHeight() { 
    	return (int) getSize().getHeight() / (nextShape.getCountPartsShape()*2) + 1; 
    }
	
	/**Отображение сегмента фигуры
     * @param graphics The Graphics class is the abstract base class for all graphics contexts that allow an application to draw onto components that are realized on various devices, as well as onto off-screen images. 
     * @param x координата X
     * @param y координата Y
     * @param shape тип рисуемой фигуры*/
    private void drawSquare(Graphics graphics, int x, int y, Tetrominoes shape){ 
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

        graphics.setColor(color);
        graphics.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

        graphics.setColor(color.brighter());
        graphics.drawLine(x, y + squareHeight() - 1, x, y);
        graphics.drawLine(x, y, x + squareWidth() - 1, y);

        graphics.setColor(color.darker());
        graphics.drawLine(x + 1, y + squareHeight() - 1,
                         x + squareWidth() - 1, y + squareHeight() - 1);
        graphics.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
                         x + squareWidth() - 1, y + 1);
    }
	
	/**Отображение игрового поля
     * @param graphics The Graphics class is the abstract base class for all graphics contexts that allow an application to draw onto components that are realized on various devices, as well as onto off-screen images.*/
	public void paint(Graphics graphics) 
    { 
		if(lastGame == null){
	        super.paint(graphics);
	
	        Dimension size = getSize();
	        int boardTop = (int) size.getHeight() - nextShape.getCountPartsShape() * squareHeight();
	
	
	        for (int i = 0; i < nextShape.getCountPartsShape(); ++i) {
	            for (int j = 0; j < nextShape.getCountPartsShape(); ++j) {
	                Tetrominoes shape = tetris.takeTypeNextFallingShape(j, nextShape.getCountPartsShape() - i - 1);
	                if (shape != Tetrominoes.NoShape)
	                    drawSquare(graphics, 0 + j * squareWidth(),
	                               boardTop + i * squareHeight(), shape);
	            }
	        }
	        
	        if (tetris.getNextShape().getShape() != Tetrominoes.NoShape) {
	            for (int i = 0; i < tetris.getNextShape().getCountPartsShape(); ++i) {
	                int x = 3 + tetris.getNextShape().getX(i);
	                int y = 3 + tetris.getNextShape().getY(i);
	                drawSquare(graphics, 0 + x * squareWidth(),
	                           boardTop + (nextShape.getCountPartsShape() - y - 1) * squareHeight(),
	                           nextShape.getShape());
	            }
	        }
		}
		else {
			super.paint(graphics);
			
	        Dimension size = getSize();
	        int boardTop = (int) size.getHeight() - nextShape.getCountPartsShape() * squareHeight();
	
	
	        for (int i = 0; i < nextShape.getCountPartsShape(); ++i) {
	            for (int j = 0; j < nextShape.getCountPartsShape(); ++j) {
	                Tetrominoes shape = lastGame.takeTypeNextFallingShape(j, nextShape.getCountPartsShape() - i - 1);
	                if (shape != Tetrominoes.NoShape)
	                    drawSquare(graphics, 0 + j * squareWidth(),
	                               boardTop + i * squareHeight(), shape);
	            }
	        }
	        
	        if (lastGame.getNextShape().getShape() != Tetrominoes.NoShape) {
	            for (int i = 0; i < lastGame.getNextShape().getCountPartsShape(); ++i) {
	                int x = 3 + lastGame.getNextShape().getX(i);
	                int y = 3 + lastGame.getNextShape().getY(i);
	                drawSquare(graphics, 0 + x * squareWidth(),
	                           boardTop + (nextShape.getCountPartsShape() - y - 1) * squareHeight(),
	                           lastGame.getNextShape().getShape());
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
		
		try {
			painterThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**Метод останавливает обнавления игрового поля*/
	public void stopGame(){
		isStarted = false;
	}
}
