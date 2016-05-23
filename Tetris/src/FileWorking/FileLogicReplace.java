package FileWorking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import TetrisLogic.Complexity;
import TetrisLogic.Shape;
import TetrisLogic.Shape.Tetrominoes;

public class FileLogicReplace implements Runnable {
	/**������� ����, ������� ���������� � ����, ���� �� ���� ���������*/
	private Tetrominoes[] boardTetrominoes;
	/**���� ������*/
	private int score;
	/**��������� ���������*/
	private Complexity complexity;
	/**��������� ������*/
	private Shape nextShape;
	/**������� ������*/
	private Shape currentShape;
	/**�����, ���������� � ������*/
	private TextFile file;
	/**������ ��������� ����*/
	private boolean isStoped = false;
	/**����������� ������ ��� ������ ����
	 * @param boardTetrominoes ������� ����
	 * @param compexity ���������
	 * @param score ����*/
	public FileLogicReplace(Tetrominoes[] boardTetrominoes, Complexity compexity, int score){
		this.boardTetrominoes = boardTetrominoes;
		this.complexity = compexity;
		boardTetrominoes = new Tetrominoes[complexity.getBoardHeight()*complexity.getBoardWidth()];
		file = new TextFile();
		this.score = score;
	}
	
	/**���������� ������ ��� ��������������� ����*/
	public FileLogicReplace(){
		nextShape = new Shape();
		currentShape = new Shape();
		file = new TextFile();
	}
	
	/**��������� ��������� ������� ��������*/
	public void readOneAction() {
		String readString = file.readOneBoardFromBeg();
		try{
			if(readString != null && !readString.equals("NewGame")){
				decrypt(readString);
				score = Integer.valueOf(file.readOneBoardFromBeg()); 
				String next = file.readOneBoardFromBeg();
				nextShape.setShape(this.getTypeShapeTetrominoes(next.charAt(0)));
				currentShape.setShape(this.getTypeShapeTetrominoes(next.charAt(1)));
			}
			else
				isStoped = !isStoped;
		}catch(Exception e){
			System.out.println(readString);
		}
	}
	
	/**���������� ���� ���������
	 * @param toWrite ������������ � ���� ���������*/
	public void writeComplexity(Complexity toWrite){
		switch(toWrite.getTypeComplexity()){
		case Easy:
			file.addToFile("Easy"+"\n");
			break;
		case Normal:
			file.addToFile("Normal"+"\n");
			break;
		case Hard:
			file.addToFile("Hard"+"\n");
			break;
		}
	}
	
	/**���������� ��������� �� �����
	 * @return ���������*/
	public Complexity getComplexity(){
		Complexity temp = null;
		String line = file.readOneBoardFromBeg();
		switch(line){
		case "Easy":
			temp = new Complexity(0);
			break;
		case "Normal":
			temp = new Complexity(1);
			break;
		case "Hard":
			temp = new Complexity(2);
			break;
		}
		this.complexity = temp;
		boardTetrominoes = new Tetrominoes[complexity.getBoardHeight()*complexity.getBoardWidth()];
		return temp;
	}

	/** �������������� ������ � ������, ������������ � ���
	 * @return ������, ������� �������� ��� ������
	 * @param oneTetrominoes ��� ������*/
	private String getTypeShapeChar(Tetrominoes oneTetrominoes){
		String type = null;
		switch(oneTetrominoes.toString()){
		case "NoShape":
			type = "N";
			break;
		case "ZShape":
			type = "Z";
			break;
		case "SShape":
			type = "S";
			break;
		case "LineShape":
			type = "I";
			break;
		case "TShape":
			type = "T";
			break;
		case "SquareShape":
			type = "Q";
			break;
		case "LShape":
			type = "L";
			break;
		case "MirroredLShape":
			type = "M";
			break;
		}
		return type;
	}
	
	/** �������������� �������, ������������� ���, � ������
	 * @return ��� ������
	 * @param oneChar ������, ������� �������� ��� ������*/
	private Tetrominoes getTypeShapeTetrominoes(char oneChar){
		Tetrominoes type = null;
		switch(oneChar){
		case 'N':
			 type = Tetrominoes.NoShape;
			break;
		case 'Z':
			type = Tetrominoes.ZShape;
			break;
		case 'S':
			type = Tetrominoes.SShape;
			break;
		case 'I':
			type = Tetrominoes.LineShape;
			break;
		case 'T':
			type = Tetrominoes.TShape;
			break;
		case 'Q':
			type = Tetrominoes.SquareShape;
			break;
		case 'L':
			type = Tetrominoes.LShape;
			break;
		case 'M':
			type = Tetrominoes.MirroredLShape;
			break;
		}
		return type;
	}
	
	/**�������� ������� �������� � ����*/
	public void addToFile(){
		
		for(int j=0; j < complexity.getBoardHeight(); j++)
			for(int i=0; i < complexity.getBoardWidth(); i++){
				file.addToFile(this.getTypeShapeChar(this.boardTetrominoes[j*complexity.getBoardWidth()+i]));
			}
		file.addToFile("\n");
		this.addTofile(Integer.toString(score));
		file.addToFile(this.getTypeShapeChar(nextShape.getShape()));
		file.addToFile(this.getTypeShapeChar(currentShape.getShape()));
		file.addToFile("\n");
	}
	
	/**�������� �������� ������ � ����
	 * @param someString ������ ��� ������*/
	public void addTofile(String someString){
		file.addToFile(someString);
		file.addToFile("\n");
	}
	/**����������� �������� ��������
	 * @param some ������ � ����� ����*/
	private void decrypt(String some){
		int tempBoardHeight = complexity.getBoardHeight();
		int tempBoardWidth = complexity.getBoardWidth();
		for(int j=0; j < tempBoardHeight; j++)
			for(int i=0; i < tempBoardWidth; i++)
				boardTetrominoes[i+j*tempBoardWidth] = this.getTypeShapeTetrominoes(some.charAt(i+j*tempBoardWidth));
		
	}
	
	/** ��������� ������ ��������� ������
	 * @param nextShape ������ ��������� ������*/
	public void setNextShape(Shape nextShape){
		this.nextShape = nextShape;
	}
	
	/** ��������� ������ ������� ������
	 * @param currentShape ������ ������� ������*/
	public void setCurrentShape(Shape currentShape){
		this.currentShape = currentShape;
	}
	
	/**�������� �����*/
	public void createFile(){
		file.CreateFile();
	}
	
	/**�������� ������� ����
	 * @return ������� ����*/
	public Tetrominoes[] getBoard(){
		return boardTetrominoes;
	}
	
	/**��������� ������ �����
	 * @param newScore ����� ����*/
	public void setNewScore(int newScore){
		score = newScore;
	}
	
	/**�������� ��� ��������� ������
	 * @return ��� ��������� ������*/
	public Shape getLastNextShape(){
		return nextShape;
	}
	/**�������� ��������� ���� ����
	 * @return score ��������� ���� ����*/
	public int getScore(){
		return score;
	}

	/**��������� ���� ������� �������� ������
	 * @return ������� �������� ������*/
	public Shape getNewCurrentShape(){
		return currentShape;
	}

	/**��������� ����������� ���������� ��� ��������������� ����*/
	public void letsGoReplay(){
		file.searchBeginning();
	}
	
	/**�������� ������ ��������� ����
	 * @return ���� ����������� - true*/
	public boolean getStatusStoped(){
		return isStoped;
	}
	
	public void setBoard(Tetrominoes [] newBoard){
		this.boardTetrominoes = newBoard;
	}
	
	public void close(){
		file.close();
	}

	@Override
	public void run() {
		Socket socket1 = null; 
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			socket1 = new Socket("127.0.0.1",25256);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(true){
			try {
				
		        pw = new PrintWriter(socket1.getOutputStream(), true);
				br = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				switch (br.readLine()){
					case "CREATE_FILE":
						this.createFile();
						this.addTofile("NewGame");
						pw.println("make");
						pw.flush();
						break;
					case "ADD_TO_FILE":
						this.addToFile();
						pw.println("make");
						pw.flush();
						break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
