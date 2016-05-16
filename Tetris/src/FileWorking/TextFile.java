package FileWorking;
import java.io.*;
import java.util.*;

/**�����, ��������������� ���������� � ������*/
public class TextFile{
	/**����� ��� ������*/
	private BufferedReader bread;

	/**��������/������� ����� ��� ������/������*/
	public void CreateFile(){
		File newFile = new File("GameProcess.txt");
		try {
	        FileWriter fstream1 = new FileWriter("GameProcess.txt");// ����������� � ����� ���������� - ��� ����������
	        BufferedWriter out1 = new BufferedWriter(fstream1); //  ������ ���������������� �����
	        out1.write(""); // �������, ����������� ������ ������ ������
	        out1.close(); // ���������
	        } catch (Exception e) 
	            {System.err.println("Error in file cleaning: " + e.getMessage());}
		try{
			newFile.delete();
			newFile.createNewFile();
		} catch(IOException ex){
			System.out.println(ex.getMessage());;
		}
	}

	/**���������� � ����
	 * @param addingString ����������� ������*/
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
	
	/**����� ������ ��� ����������*/
	public void searchBeginning(){
		try {
			bread = new BufferedReader(new InputStreamReader(new FileInputStream("GameProcess.txt"), "UTF-8"));
			bread.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**������ ����� ������ �� ������������ ������
	 * @return ��������� ������*/
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
