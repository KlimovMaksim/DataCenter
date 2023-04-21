import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.HashSet;

public class Main {
	public static void main(String[] args){
		long timer = System.currentTimeMillis();
		File file = new File("./resources/input2.txt");
        try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8))
        {
            int[] inputValues = new int[3];
        	String event;
        	String[] eventSplit;
            event = sc.nextLine();
            eventSplit = event.split(" ");
            for (int i = 0; i < 3; i++) {
            	inputValues[i] = Integer.parseInt(eventSplit[i]);
            }
            int eventCount = inputValues[2];
            DataCenter[] dcArray = new DataCenter[inputValues[0]];
            for (int i = 0; i < dcArray.length; i++) { dcArray[i] = new DataCenter(inputValues[1]); }
            
            for (int i = 0; i < eventCount; i++) {
            	event = sc.nextLine();
            	eventSplit = event.split(" ");
            	switch(eventSplit[0]) {
            	case ("DISABLE"):
            		dcArray[Integer.parseInt(eventSplit[1]) - 1].disable(Integer.parseInt(eventSplit[2]));
            		break;
            	case ("RESET"):
            		dcArray[Integer.parseInt(eventSplit[1]) - 1].reset();
            		break;
            	case ("GETMAX"):
            		getMax(dcArray);
            		break;
            	case ("GETMIN"):
            		getMin(dcArray);
            		break;
            	}
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println((System.currentTimeMillis() - timer) / 1000.0);
        
		
		
	}
	
	public static void getMax(DataCenter[] dataCentersArray) {
		int product;
		int maxResult = 0;
		int dcPosition = -1;
		for (int i = 0; i < dataCentersArray.length; i++) {
			product = dataCentersArray[i].getProduct();
			if (dcPosition == -1 && maxResult == product) {}
			if ((maxResult < product) || (dcPosition == -1 && maxResult == product)) { 
				maxResult = product; 
				dcPosition = i; 
			}
		}
		System.out.println(dcPosition + 1);
	}
	
	public static void getMin(DataCenter[] dataCentersArray) {
		int product;
		int minResult = 2_147_483_647;
		int dcPosition = -1;
		for (int i = 0; i < dataCentersArray.length; i++) {
			product = dataCentersArray[i].getProduct();
			if ((minResult > product) || (dcPosition == -1 && minResult == product)) { minResult = product; dcPosition = i; }
		}
		System.out.println(dcPosition + 1);
	}
}

class DataCenter {
	// число перезапусков i-го дата-центра
	private int resetCount;
	// число серверов в дата-центре
	private int serversCount;
	// множество выключенных серверо 
	HashSet<Integer> serversSet;
	
	DataCenter(int serversCount) { 
		this.serversCount = serversCount;
		serversSet = new HashSet<Integer>(serversCount); 
		resetCount = 0;	
	}
	
	public void reset() { 
		resetCount++;
		serversSet.clear();
	}
	
	public void disable(int serverPosition) { serversSet.add(serverPosition); }
	
	public int getProduct() { return resetCount * (serversCount - serversSet.size()); }
}

