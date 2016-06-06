package FileWorking;
import java.io.*;

/**�����, ��������������� ���������� � ������*/
public class TextFile{
	/**����� ��� ������*/
	private BufferedReader bRead;
	private BufferedWriter bWrite;
	private String writeFileName;
	private String readLastFile;
	private File workingFile;
	String addingString;
	String playThisGame = null;
	public TextFile(){
	}
	
	public TextFile(String playThisGame){
		this.playThisGame = playThisGame; 
	}
	
	/**��������/������� ����� ��� ������/������*/
	public void CreateFile(){
		findNameToWrite();
		workingFile = new File(writeFileName);
		
	}
	
	private void findNameToWrite(){
		String[]fList;
		String path = new File(".").getAbsolutePath();
		path = path.substring(0, path.length()-2);
		File fl = new File(path);        
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
		String path = new File(".").getAbsolutePath();
		path = path.substring(0, path.length()-2);
		File fl = new File(path);
		        
		fList = fl.list();
		
		int numberOfFile = 0;
		int tempNumber = 0;
		int i = 0;
		while(true){
			if( fList[i].charAt(0) == 'G' && fList[i].charAt(1) == 'a' && fList[i].charAt(2) == 'm' && 	fList[i].charAt(3) == 'e' && fList[i].length()>8){
					String temp = fList[i].substring(4, fList[i].length()-4);
					try{
						tempNumber = Integer.valueOf(temp);
						if(tempNumber > numberOfFile){
							numberOfFile = tempNumber;
						}
					}catch(NumberFormatException e){
					}
			}
			i++;
			if(i == fList.length){
				break;
			}
		}
		readLastFile = "Game"+numberOfFile+".txt";
	}
	
	/**���������� � ����
	 * @param addingString ����������� ������*/
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
	
	/**����� ������ ��� ����������*/
	public void searchBeginning(){
		if(playThisGame == null){
			findNameOfLastGame();
		}
		else
			readLastFile = playThisGame;
		try {
			bRead = new BufferedReader(new InputStreamReader(new FileInputStream(readLastFile), "UTF-8"));
			bRead.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**������ ����� ������ �� ������������ ������
	 * @return ��������� ������*/
	public String readOneBoardFromBeg() {
		String line = null;
		try {
			line = bRead.readLine();			
		} catch (IOException e) {
		}
		return line;
	}

}
