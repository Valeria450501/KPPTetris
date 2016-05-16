package FileWorking;
import java.io.*;
import java.util.*;

/**Класс, непосредственно работающий с файлом*/
public class TextFile{
	/**Буфер для чтения*/
	private BufferedReader bread;

	/**Создание/очистка файла для записи/чтения*/
	public void CreateFile(){
		File newFile = new File("GameProcess.txt");
		try {
	        FileWriter fstream1 = new FileWriter("GameProcess.txt");// конструктор с одним параметром - для перезаписи
	        BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
	        out1.write(""); // очищаем, перезаписав поверх пустую строку
	        out1.close(); // закрываем
	        } catch (Exception e) 
	            {System.err.println("Error in file cleaning: " + e.getMessage());}
		try{
			newFile.delete();
			newFile.createNewFile();
		} catch(IOException ex){
			System.out.println(ex.getMessage());;
		}
	}

	/**Добавление в файл
	 * @param addingString добавляемая строка*/
	public void addToFile(String addingString){
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			File file = new File("GameProcess.txt");
			fw = new FileWriter(file, true);
			bw = new BufferedWriter(fw);
			bw.write(addingString);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (Exception e) {
			}
			try {
				fw.close();
			} catch (Exception e) {
			}
		}
	}
	
	/**Поиск начала для считывания*/
	public void searchBeginning(){
		try {
			bread = new BufferedReader(new InputStreamReader(new FileInputStream("GameProcess.txt"), "UTF-8"));
			bread.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**Чтение одной строки от определённого начала
	 * @return прочтённая строка*/
	public String readOneBoardFromBeg() {
		String line = null;
		try {
			line = bread.readLine();			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}
	
	
}
