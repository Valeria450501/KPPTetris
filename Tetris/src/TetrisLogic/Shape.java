package TetrisLogic;  //одна фигура

import java.util.Random;
import java.lang.Math;
    
public class Shape {
	enum Tetrominoes { 							//все возможный типы финур
		NoShape,	//X
		ZShape, 	//z
		SShape, 	//s
		LineShape, 	//line
	    TShape, 	//t
	    SquareShape,//square 
	    LShape, 	//L
	    MirroredLShape };//L how J
	private Tetrominoes currentShape;			//тип текущей фигуры
	private int currentCoords[][];				//координаты всех сегментов текущей фигуры
	private int[][][] coordsTable;				//таблица координат для каждой фигуры
	private final int COUNT_PARTS_SHAPE = 4;	//количество сегментов для одной фигуры
	private final int COUNT_COORDS = 2;			//координаты каждого семента финуры (x, y)
	
	public Shape() {
		currentCoords = new int[COUNT_PARTS_SHAPE][COUNT_COORDS];
		setShape(Tetrominoes.NoShape);
	}
	
	private void setX(int positionX, int x){
		currentCoords[positionX][0] = x;
	}
	
	private void setY(int positionY, int y){
		currentCoords[positionY][1] = y;
	}

	public int getX(int indexX){
		return currentCoords[indexX][0];
	}
	
	public int getY(int indexY){
		return currentCoords[indexY][1];
	}
	
	public int minX(){
		int min = currentCoords[0][0];
		for(int i=0; i<COUNT_PARTS_SHAPE; i++){
			min = Math.min(min, currentCoords[i][0]);
		}
		return min;
	}
	
	public int minY(){
		int min = currentCoords[0][1];
		for(int i=0; i<COUNT_PARTS_SHAPE; i++){
			min = Math.min(min, currentCoords[i][1]);
		}
		return min;
	}
	
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

	public void setRandomShape(){
		Random setRandomShape = new Random();
		Tetrominoes [] currentNewShape = Tetrominoes.values();
		setShape(currentNewShape[Math.abs(setRandomShape.nextInt())%7+1]);  
	}

	public int getCountPartsShape(){
		return COUNT_PARTS_SHAPE;
	}
	
	public int getCountCoords(){
		return COUNT_COORDS;
	}
	
	public Tetrominoes getShape(){
		return currentShape;
	}
		
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
