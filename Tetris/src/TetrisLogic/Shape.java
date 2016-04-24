package TetrisLogic;  

import java.util.Random;
import java.lang.Math;

/**
 * <p> ласс, содержащий информации о возможных фигрух,о методах их передвижени€ и
 * создани€ новых вигур</p>*/
public class Shape {
	/**¬се возможные типы фигур*/
	public enum Tetrominoes {
		NoShape,	//X
		ZShape, 	//z
		SShape, 	//s
		LineShape, 	//line
	    TShape, 	//t
	    SquareShape,//square 
	    LShape, 	//L
	    MirroredLShape };//L how J
	/**“ип текущей фигуры*/
	private Tetrominoes currentShape;
	/** оординаты всех сегментов текущей фигуры*/
	private int currentCoords[][];
	/**“аблица координат дл€ каждой фигуры*/
	private int[][][] coordsTable;
	/** оличество сегментов дл€ одной фигуры
	 * @value 4*/
	private final static int COUNT_PARTS_SHAPE = 4;	
	/** оординаты каждого семента финуры (x, y)
	 * @value 2*/
	private final static int COUNT_COORDS = 2;			
	
	/** онструктор.*/
	public Shape() {
		currentCoords = new int[COUNT_PARTS_SHAPE][COUNT_COORDS];
		setShape(Tetrominoes.NoShape);
	}
	
	/**»змение координаты X
	 * @param positionX позици€ координаты X в массиве координат
	 * @param x новое значение координаты X*/
	private void setX(int positionX, int x){
		currentCoords[positionX][0] = x;
	}
	
	/**»змение координаты Y
	 * @param positionY позици€ координаты Y в массиве координат
	 * @param y новое значение координаты y*/
	private void setY(int positionY, int y){
		currentCoords[positionY][1] = y;
	}

	/**ѕолучить значение координаты X
	 * @param indexX позици€ координаты X в массиве координат
	 * @return значение координаты X*/
	public int getX(int indexX){
		return currentCoords[indexX][0];
	}
	
	/**ѕолучить значение координаты Y
	 * @param indexY позици€ координаты Y в массиве координат
	 * @return значение координаты Y*/
	public int getY(int indexY){
		return currentCoords[indexY][1];
	}
	
	/**ѕоучить минимальное заначение координаты X
	 * @return минимальное заначение координаты X*/
	public int minX(){
		int min = currentCoords[0][0];
		for(int i=0; i<COUNT_PARTS_SHAPE; i++){
			min = Math.min(min, currentCoords[i][0]);
		}
		return min;
	}
	
	/**ѕоучить минимальное заначение координаты Y
	 * @return минимальное заначение координаты Y*/
	public int minY(){
		int min = currentCoords[0][1];
		for(int i=0; i<COUNT_PARTS_SHAPE; i++){
			min = Math.min(min, currentCoords[i][1]);
		}
		return min;
	}
	
	/**”становка новой фигуры.
	 * ¬ соответствии с типом фигуры устанавливаютс€ новые координаты
	 * @param currentNewShape нова€ фигура*/
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

	/**—оздание новой случайной фигуры*/
	public void setRandomShape(){
		Random setRandomShape = new Random();
		Tetrominoes [] currentNewShape = Tetrominoes.values();
		setShape(currentNewShape[Math.abs(setRandomShape.nextInt())%7+1]);  
	}

	/**ѕолучить количество сегментов фигуры
	 * @return количество сегментов фигуры*/
	public int getCountPartsShape(){
		return COUNT_PARTS_SHAPE;
	}
	
	/**ѕолучить количество типов координат (x,y)
	 * @return количество типов координат (x,y)*/
	public int getCountCoords(){
		return COUNT_COORDS;
	}
	
	/**ѕолучить тип фигуры
	 * @return тип фигуры*/
	public Tetrominoes getShape(){
		return currentShape;
	}
	
	/**¬ращение влево
	 * @return повЄрнута€ фигура*/
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

	/**¬ращение вправо
	 * @return повЄрнута€ фигура*/
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
