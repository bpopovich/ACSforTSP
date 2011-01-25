package ims.acs.main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class InputReader {

	private Scanner input;
	private String file;
	
	public InputReader(String txt){
		file = txt;
		
		 try {
				input = new Scanner(new File(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	public HashMap<Integer, City> read(){
		
		HashMap<Integer, City> gradovi = new HashMap<Integer, City>();
		int i = 0;
		
		while(input.hasNextLine()){
			String line = input.nextLine();
			String[] c = line.split(" ");
			gradovi.put(i, new City(Integer.parseInt(c[0]), Integer.parseInt(c[1]), c[2]));
			i++;
		}
		
		input.close();
		return gradovi;
	}
}
