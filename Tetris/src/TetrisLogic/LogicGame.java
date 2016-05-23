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
	/**Выбранная сложность*/
	private Complexity chosenComplexity;
	/**Текущая падающая фигура*/
	private Shape currentFallingShape;
	/**Следующая падающая фигура*/
	private Shape nextFallingShape;
	/**Объкт класса, отвечающего за отображение игрового поля
	 * @see PainterBoardGame*/
	private PainterBoardGame paintBoard;
	/**Игровое поле*/
	private Tetrominoes[] boardTetrominoes;
	/**Поле с информацией о следующей падающей фигуре*/
	private Tetrominoes[] boardNextShape;
	/**Сигнализация статуса падения фигуры*/
	private boolean isFallingFinished = false;
	/**Сигнализация процесса игры. (Игра запущена)*/
	private boolean isStarted = false;
	/**Сигнализация процесса игры. (Игра остановлена)*/
	private boolean isPaused = false;
	/**Выбор игры с ботом*/
	private boolean isBot = false;
	/**Счёт игрока*/
	private int score;
	/**Текущая координата x*/
	private int currentX = 0;
	/**Текущая координата y*/
	private int currentY = 0;
	/**Таймер с периодом следующего хода*/
	private Timer timer;
	/**Объект окна, создающего этот объект
	 * @see PlayGameWindow*/
	private PlayGameWindow caused;
	/**Класс, отображающий на экране следующую фигуру*/
	private PaintNextShape drawNextShape;
	/**Класс, отвечающий за запись поей для последующего воспроизведения последней игры
	 * @see PaintNextShape*/
	private FileLogicReplace fileToWrite;
	/**ServerSocket для общения между клиентом и сервером*/
	private static ServerSocket server;
	/**Поток клиента*/
	private Thread clientThread;
	/**Объект чтения сообщений от клиента*/
	private BufferedReader bufRead;
	/**Объект, отправляющий сообщения клиенту*/
	private PrintStream printStream;
	/**Socket для общения между клиентом и сервером*/
	private Socket socket;
	
	/**
	 *Конструктор.
	 *Определяет падающую фигуру, создаёт игровое поле, запускает таймер.
	 *Вызывает перерисовывание полей(игрового и следйющей падающей фигуры)
	 *@param complexity выбранная сложность
	 *@param caused	объект окна, создающего этот объект  
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
	
	/**Выбор игры бота*/
	public void playBot(){
		isBot = true;
	}
	
	/**Определение типа текущей падающей фигуры
	 * @param x координата x текущей падающей фигуры
	 * @param y координата y текущей падающей фигуры
	 * @return тип текущей падающей фигуры
	 * */
	public Tetrominoes takeTypeCurrentFallingShape(int x, int y){
		return boardTetrominoes[y*chosenComplexity.getBoardWidth()+x];
	}
	
	/**Определение типа следующей падающей фигуры
	 * @param x координата x следующей падающей фигуры
	 * @param y координата y следйющей падающей фигуры
	 * @return тип следующей падающей фигуры
	 * */
	public Tetrominoes takeTypeNextFallingShape(int x, int y){
		return boardNextShape[y*nextFallingShape.getCountPartsShape()+x];
	}
	
	/**Запуск игрового процесса*/
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

	/**Остановка игрового процесса*/
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

	/**Метод либо передвигает падающую фигуру по игровому полю, либо создаёт новую падающую фигуру.
	 * При игре с ботом вызывает действие, производимое над фигурой.
	 * Вызывается при срабатывании таймера*/
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
	
	/**Действия, которые необходимо выполнить при первой записи в файл*/
	private void firsTime(){
		fileToWrite = new FileLogicReplace(boardTetrominoes, getComplexity(), score);
		clientThread = new Thread(fileToWrite);
		clientThread.start();
		try	{
			server = new ServerSocket(25256); // Номер сокета 2525
		} catch(Exception e) {
			System.out.println("ERRSOCK+"+e); 
		}
			socket = null;
			try {
				socket = server.accept(); // Ожидание соединения с клиентом
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
	
	/**Создание новой фигуры и запись в файл поля игры, следующей и текущей фигур*/
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

	/**Передвигаем фигуру на одну линию вниз*/
	public void oneLineDown(){	
		if(!tryMove(currentFallingShape, currentX, currentY - 1))
			shapeLand();
	}
	
	/**Передвигаем фигуру вниз на столько, на сколько это возможно*/
	public void shapeFalling(){ 
		int newY = currentY;
		while (newY > 0) {
            if (!tryMove(currentFallingShape, currentX, newY - 1))
                break;
            --newY;
        }
        shapeLand();
	}
	
	/**Попытка передвинуть текущую падающую фигуру по заданным координатам
	 * @param fallingShape фигура, которую передвигаем по заданным координатам
	 * @param newX новая координата x
	 * @param newY новая координата y
	 * @return удалось ли переместить фигуру
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
	
	/**"Преземление" фигуры.
	 * Вызывается, когда фигура больше не может двигаться вниз*/
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
	

	/**Очистка заполненных линий*/
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
	
	/**Очистка игрового поля*/
	private void clearBoardTetrominoes(){ 
		for (int i = 0; i < chosenComplexity.getBoardHeight() * chosenComplexity.getBoardWidth(); ++i)
            boardTetrominoes[i] = Tetrominoes.NoShape;
	}
	
	/**Очистка поля следующей падающей фигуры*/
	private void clearBoardNextShape(){ 
		for (int i = 0; i < nextFallingShape.getCountPartsShape() * nextFallingShape.getCountPartsShape(); ++i)
            boardNextShape[i] = Tetrominoes.NoShape;
	}
	
	/**Получить выбранную сложность
	 * @return выбранная сложность*/
	public Complexity getComplexity(){
		return chosenComplexity;
	}
	
	/**Получить падающую фигуру
	 * @return падающая фигура*/
	public Shape getCurrentFallingShape(){
		return currentFallingShape;
	}
	
	/**Получить статус запуска игры
	 * @return статус запуска игры*/
	public boolean getStartStatus(){
		return isStarted;
	}
	
	public void exitFromGame(){
		paintBoard.stopGame();
		drawNextShape.stopGame();
	}
	
	/**Получить статус остановки игры
	 * @return статус остановки игры*/
	public boolean getPauseStatus(){
		return isPaused;
	}
	
	/**Получить текущую X координату
	 * @return текущая X координата*/
	public int getCurrentX(){
		return currentX;
	}
	
	/**Получить текущую Y координату
	 * @return текущая Y координата*/
	public int getCurrentY(){
		return currentY;
	}
	
	/**Получить объект, реализующий отображение поля игры
	 * @return объект, реализующий отображение поля игры*/
	public PainterBoardGame getPainter(){
		return paintBoard;
	}
	
	/**Получить счёт игрока
	 * @return счёт игрока*/
	public int getScore(){
		return score;
	}
	
	/**Получить следующую падающую фигуру
	 * @return следующая падающая фигура*/
	public Shape getNextShape(){
		return nextFallingShape;
	}
	
	/**Получить объект, реализующий отображение поля следующей падающей фигуры
	 * @return объект, реализующий отображение поля следующей падающей фигуры*/
	public PaintNextShape getDrawNextShape(){
		return drawNextShape;
	}
	
	/**Действия, производимые при завершении игры*/
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
