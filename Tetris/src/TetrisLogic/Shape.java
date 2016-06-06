package TetrisLogic;  

import java.util.Random;
import java.lang.Math;

/**
 * <p>�����, ���������� ���������� � ��������� ������,� ������� �� ������������ �
 * �������� ����� �����</p>*/
public class Shape {
	/**��� ��������� ���� �����*/
	public enum Tetrominoes {
		NoShape,	//X
		ZShape, 	//z
		SShape, 	//s
		LineShape, 	//line
	    TShape, 	//t
	    SquareShape,//square 
	    LShape, 	//L
	    MirroredLShape };//L how J
	/**��� ������� ������*/
	private Tetrominoes currentShape;
	/**���������� ���� ��������� ������� ������*/
	private int currentCoords[][];
	/**������� ��������� ��� ������ ������*/
	private int[][][] coordsTable;
	/**���������� ��������� ��� ����� ������
	 * @value 4*/
	private final static int COUNT_PARTS_SHAPE = 4;	
	/**���������� ������� ������� ������ (x, y)
	 * @value 2*/
	private final static int COUNT_COORDS = 2;			
	
	/**�����������.*/
	public Shape() {
		currentCoords = new int[COUNT_PARTS_SHAPE][COUNT_COORDS];
		setShape(Tetrominoes.NoShape);
	}
	
	/**������� ���������� X
	 * @param positionX ������� ���������� X � ������� ���������
	 * @param x ����� �������� ���������� X*/
	private void setX(int positionX, int x){
		currentCoords[positionX][0] = x;
	}
	
	/**������� ���������� Y
	 * @param positionY ������� ���������� Y � ������� ���������
	 * @param y ����� �������� ���������� y*/
	private void setY(int positionY, int y){
		currentCoords[positionY][1] = y;
	}

	/**�������� �������� ���������� X
	 * @param indexX ������� ���������� X � ������� ���������
	 * @return �������� ���������� X*/
	public int getX(int indexX){
		return currentCoords[indexX][0];
	}
	
	/**�������� �������� ���������� Y
	 * @param indexY ������� ���������� Y � ������� ���������
	 * @return �������� ���������� Y*/
	public int getY(int indexY){
		return currentCoords[indexY][1];
	}
	
	/**������� ����������� ��������� ���������� X
	 * @return ����������� ��������� ���������� X*/
	public int minX(){
		int min = currentCoords[0][0];
		for(int i=0; i<COUNT_PARTS_SHAPE; i++){
			min = Math.min(min, currentCoords[i][0]);
		}
		return min;
	}
	
	/**������� ����������� ��������� ���������� Y
	 * @return ����������� ��������� ���������� Y*/
	public int minY(){
		int min = currentCoords[0][1];
		for(int i=0; i<COUNT_PARTS_SHAPE; i++){
			min = Math.min(min, currentCoords[i][1]);
		}
		return min;
	}
	
	/**��������� ����� ������.
	 * � ������������ � ����� ������ ��������������� ����� ����������
	 * @param currentNewShape ����� ������*/
	public void setShape(Tetrominoes currentNewShape) {
		  coordsTable = new int[][][] {
		     { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },	//X
		     { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } },	//z
		     { { 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 } },	//s
		     { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } },	//line
		     { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },	//t
		     { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },	//square
		     { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } },	//L
		     { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }	//L how J
		  };
	
		 for (int i = 0; i < COUNT_PARTS_SHAPE ; i++) {
		     for (int j = 0; j < COUNT_COORDS; ++j) 
		         currentCoords[i][j] = coordsTable[currentNewShape.ordinal()][i][j];
		 }
		 
		 currentShape = currentNewShape;
	}

	/**�������� ����� ��������� ������*/
	public void setRandomShape(){
		Random setRandomShape = new Random();
		Tetrominoes [] currentNewShape = Tetrominoes.values();
		setShape(currentNewShape[Math.abs(setRandomShape.nextInt())%7+1]);  
	}

	/**�������� ���������� ��������� ������
	 * @return ���������� ��������� ������*/
	public int getCountPartsShape(){
		return COUNT_PARTS_SHAPE;
	}
	
	/**�������� ���������� ����� ��������� (x,y)
	 * @return ���������� ����� ��������� (x,y)*/
	public int getCountCoords(){
		return COUNT_COORDS;
	}
	
	/**�������� ��� ������
	 * @return ��� ������*/
	public Tetrominoes getShape(){
		return currentShape;
	}
	
	/**�������� �����
	 * @return ��������� ������*/
	public Shape rotateLeft(){
		if (currentShape == Tetrominoes.SquareShape)
            return this;

        Shape result = new Shape();
        result.currentShape = currentShape;

        for (int i = 0; i < COUNT_PARTS_SHAPE; ++i) {
            result.setX(i, getY(i));
            result.setY(i, -getX(i));
        }
        return result;
	 }

	/**�������� ������
	 * @return ��������� ������*/
	public Shape rotateRight(){
		if (currentShape == Tetrominoes.SquareShape)
            return this;

        Shape result = new Shape();
        result.currentShape = currentShape;

        for (int i = 0; i < COUNT_PARTS_SHAPE; ++i) {
            result.setX(i, -getY(i));
            result.setY(i, getX(i));
        }
        return result;
	}
}
