package Sort;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class JavaSort {
	private String bestOfScore;
	private String bestOfTimes;
	private String worstOfScore;
	private String worstOfTimes;
	ArrayList<Struct> fromScore;
	ArrayList<Struct> fromTimes;
	class Struct{
		String fileName;
		int fileScore;
		int fileTimes;
		
		public Struct(String name, int score, int times) {
			fileName = name;
			fileScore = score;
			fileTimes = times;			
		}
		
		public int getScore(){
			return fileScore;
		}
		public int getTimes(){
			return fileTimes;
		}
		public String getName(){
			return fileName;
		}
		public void setScore(int newScore){
			fileScore = newScore;
		}
		public void setTimes(int newTimes){
			fileTimes = newTimes;
		}
		public void setName(String newName){
			fileName = newName;
		}
	}
	
	public String getBestScore(){
		return bestOfScore;
	}
	
	public String getBestTimes(){
		return bestOfTimes;
	}
	
	public String getWorstScore(){
		return worstOfScore;
	}
	
	public String getWorstTimes(){
		return worstOfTimes;
	}
	
	public void sort(String dir){
		ArrayList<Struct> some = createList(getListOfLogs(dir));
		fromScore = sortScore(some);
		fromTimes = sortTimes(some);
	}
	
	public ArrayList<File> getListOfLogs(String dir){
		File source = new File(dir);
		File[] list = source.listFiles(); 
		ArrayList<File> newList = new ArrayList<File>();
		for(int i=0; i<list.length; i++){
			if(list[i].toString().contains("Game")){
				if(list[i].toString().contains(".txt")){
					newList.add(list[i]);
				}
			}
		}
		return newList;
	}

	
	private ArrayList<Struct> createList(ArrayList<File> fileList){
		ArrayList<Struct> ms = new ArrayList<Struct>();
		
		for(int i=0; i<fileList.size(); i++){
			int countTimes = 0;
			String firstString = null;
			String currentLine = null;
			Scanner in = null;
			try {
				in = new Scanner(fileList.get(i));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			if(in.nextLine().contains("NewGame")){
				while(in.hasNext()){
					countTimes++;
					firstString = currentLine;
					currentLine = in.nextLine();
				}
			}
			in.close();
			int kaka = (countTimes-1)/3;
			Struct one = new Struct (fileList.get(i).toString(), Integer.valueOf(firstString),kaka);
			ms.add(one);
		}
		return ms;
	}
		
	private ArrayList<Struct> sortScore(ArrayList<Struct> ms){
		for (int i = ms.size() - 1; i >= 0; i--) { 
			for (int j = 0; j < i; j++) { 
				if (ms.get(j).getScore() > ms.get(j+1).getScore()) { 
					Struct t = ms.get(j); 
					ms.get(j).setScore(ms.get(j+1).getScore()); 
					ms.get(j).setTimes(ms.get(j+1).getTimes()); 
					ms.get(j).setName(ms.get(j+1).getName()); 
					
					ms.get(j+1).setScore(t.getScore()); 
					ms.get(j+1).setTimes(t.getTimes()); 
					ms.get(j+1).setName(t.getName());
				} 
			}
		}
		return ms;
	}
	
	private ArrayList<Struct> sortTimes(ArrayList<Struct> ms){
		for (int i = ms.size() - 1; i >= 0; i--) { 
			for (int j = 0; j < i; j++) { 
				if (ms.get(j).getTimes() > ms.get(j+1).getTimes()) { 
					Struct t = ms.get(j); 
					ms.get(j).setScore(ms.get(j+1).getScore()); 
					ms.get(j).setTimes(ms.get(j+1).getTimes()); 
					ms.get(j).setName(ms.get(j+1).getName()); 
					
					ms.get(j+1).setScore(t.getScore()); 
					ms.get(j+1).setTimes(t.getTimes()); 
					ms.get(j+1).setName(t.getName());
				} 
			}
		}
		return ms;
	}
}
