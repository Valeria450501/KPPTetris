package TetrisLogic; 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import TetrisLogic.Shape.Tetrominoes;
import Windows.PlayGameWindow;
/**
 * <p>����� ���������� ������� ���� �� ������.
 * � ������ ���������� ������� �� �������.</p>
 * @see KeyboardHandler
 */
public class PainterBoardGame extends JPanel{
	/**������ ������� � ������� ����
	 * @see LogicGame */
	private LogicGame logic;
	
	/**�����������.
	 * ������������ ��������� ������������� ����.
	 * ����������� ���������� ������� �������.
	 * @see KeyboardHandler
	 * @param logic ������ ������� � ������� ����
	 * @param caused ����, � ������ ������������ ����
	 */
	public PainterBoardGame(LogicGame logic, PlayGameWindow caused){
		setFocusable(true);
		this.logic = logic;
		Dimension size = new Dimension(100,200); 
		this.addKeyListener(new KeyboardHandler(logic));
		this.setMinimumSize(size);
		this.setBackground(Color.white);
	}
	
	/**������������ ������ ��������� ������
	 * @return ������ ��������� ������*/
	private int squareWidth() { 
		return (int) getSize().getWidth() / logic.getComplexity().getBoardWidth();
	}
	
	/**������������ ������ ��������� ������
	 * @return ������ ��������� ������*/
    int squareHeight() { 
    	return (int) getSize().getHeight() / logic.getComplexity().getBoardHeight(); 
    }
	
    /**����������� �������� ������
     * @param g The Graphics class is the abstract base class for all graphics contexts that allow an application to draw onto components that are realized on various devices, as well as onto off-screen images. 
     * @param x ���������� X
     * @param y ���������� Y
     * @param shape ��� �������� ������*/
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
    
    /**����������� �������� ����
     * @param g The Graphics class is the abstract base class for all graphics contexts that allow an application to draw onto components that are realized on various devices, as well as onto off-screen images.*/
    public void paint(Graphics g) 
    { 
        super.paint(g);

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
    
 }
