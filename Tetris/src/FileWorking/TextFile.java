package FileWorking;
import java.io.*;
import java.util.*;

/**Класс, непосредственно работающий с файлом*/
public class TextFile{
	/**Буфер для чтения*/
	private BufferedReader bRead;
	private BufferedWriter bWrite;
	private String writeFileName;
	private String readLastFile;
	private File workingFile;
	String addingString;
	public TextFile(){
	}
	
	/**Создание/очистка файла для записи/чтения*/
	public void CreateFile(){
		findNameToWrite();
		workingFile = new File(writeFileName);
		
	}
	
	private void findNameToWrite(){
		String[]fList;      
		String dirname = "C:\\Users\\User\\Documents\\Eclips_workspace\\Tetris";
		File fl = new File(dirname);        
		fList = fl.list();
		
		int numberOfFile = 0;
		
		int i = 0;
		int tempNumber = 0;
		while(true){
			if( fList[i].charAt(0) == 'G' && fList[i].charAt(1) == 'a' && fList[i].charAt(2) == 'm' && 	fList[i].charAt(3) == 'e' && fList[i].length()>8){
					String temp = fList[i].substring(4, fList[i].length()-4);
					try{
						tempNumber = Integer.valueOf(temp);
						if(tempNumber > numberOfFile)
							numberOfFile = tempNumber;
					}catch(NumberFormatException e){
					}
			}
			i++;
			if(i == fList.length)
				break;
		}
		numberOfFile++;
		writeFileName = "Game"+numberOfFile+".txt";
	}
	
	private void findNameOfLastGame(){
		String[]fList;      
		String dirname = "C:\\Users\\User\\Documents\\Eclips_workspace\\Tetris";
		File fl = new File(dirname);
		        
		fList = fl.list();
		
		int numberOfFile = 0;
		int tempNumber = 0;
		int i = 0;
		while(true){
			if( fList[i].charAt(0) == 'G' && fList[i].charAt(1) == 'a' && fList[i].charAt(2) == 'm' && 	fList[i].charAt(3) == 'e' && fList[i].length()>8){
					String temp = fList[i].substring(4, fList[i].length()-4);
					try{
						tempNumber = Integer.valueOf(temp);
						if(tempNumber > numberOfFile)
							numberOfFile = tempNumber;
					}catch(NumberFormatException e){
					}
			}
			i++;
			if(i == fList.length)
				break;
		}
		readLastFile = "Game"+numberOfFile+".txt";
	}
	
	/**Добавление в файл
	 * @param addingString добавляемая строка*/
	public void addToFile(String addingString){
		try {
			bWrite = new BufferedWriter(new FileWriter(workingFile, true));
			bWrite.write(addingString);
			bWrite.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			bWrite.close();
		} catch (Exception e) {
		}
		try {
			bRead.close();
		} catch (Exception e) {
		}
			
	}
	
	/**Поиск начала для считывания*/
	public void searchBeginning(){
		findNameOfLastGame();
		try {
			bRead = new BufferedReader(new InputStreamReader(new FileInputStream(readLastFile), "UTF-8"));
			bRead.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**Чтение одной строки от определённого начала
	 * @return прочтённая строка*/
	public String readOneBoardFromBeg() {
		String line = null;
		try {
			line = bRead.readLine();			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}

}
