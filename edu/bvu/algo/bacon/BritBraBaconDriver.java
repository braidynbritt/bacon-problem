package edu.bvu.algo.bacon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.bvu.algo.AdjListGraph;
import edu.bvu.algo.Graph;
import edu.bvu.algo.GraphTuple;

public class BritBraBaconDriver{
	
	private static List<String> actors = new ArrayList<String>();
	private static Map<String, String> actsToMovs = new HashMap<String, String>();
	private static Graph g = new AdjListGraph();
	private static String movs="";

/*	public void run(String dbFilePath) throws FileNotFoundException {
		File inputFile = new File(dbFilePath);
		@SuppressWarnings("resource")
		Scanner s = new Scanner(inputFile);
		String line;

		while(s.hasNext()) {
			line = s.nextLine();
			if(line.equals("")) {
				relationship();
			}
			else if(line.startsWith("M: ")) {
				String[] mov = line.split(": ");
				movs = mov[1];
				actors.clear();
			}
			else {
				if(!g.containsNode(line)) {
					g.addNode(line);
				}
				actors.add(line);
			}
		}
		relationship();
		program();
	}*/
	public void program() {
		Map<String, GraphTuple> fin = g.bfs("Kevin Bacon");
		System.out.println("Welcome to the Oracle of Bacon!");

		while(true) {
			InputStream userInput = System.in;
			@SuppressWarnings("resource")
			var in = new Scanner(userInput);
			System.out.print("Enter actor/actress name: ");
			String input = in.nextLine();
			System.out.println();
			if(input=="") {
				break;
			}
			if(fin.containsKey(input)) {
				if(input.equals("Kevin Bacon")) {
					System.out.println("Kevin Bacon has a Bacon number of 0. He IS Kevin Bacon!");
					System.out.println();
				}
				else if(fin.containsKey(input)){
					int actorNumber = fin.get(input).d;
					String actorMovie = fin.get(input).p;
					System.out.println(input + " has a Bacon number of " + actorNumber + ".");
					while(input != "Kevin Bacon") {
						System.out.println("   " + input + " was in \"" + actsToMovs.get(input+actorMovie) + 
								"\" with " + actorMovie + "." );
						input = fin.get(input).p;
						actorMovie = fin.get(input).p;
					//	System.out.println(fin);
					}
					//System.out.println(fin);
					System.out.println();
				}
			}
			if(!fin.containsKey(input)) {
				System.out.println(input + " has a Bacon number of infinity!");
				System.out.println();
			}

		}
		System.out.println("Thank you for using the Oracle of Bacon.");
		return;
	}
	
	public void run(String dbFilePath) throws FileNotFoundException {
		File inputFile = new File(dbFilePath);
		@SuppressWarnings("resource")
		Scanner s = new Scanner(inputFile);
		String line;

		while(s.hasNext()) {
			line = s.nextLine();
			if(line.equals("")) {
				relationship();
			}
			else if(line.startsWith("M: ")) {
				String[] mov = line.split(": ");
				movs = mov[1];
				actors.clear();
			}
			else {
				if(!g.containsNode(line)) {
					g.addNode(line);
				}
				actors.add(line);
			}
		}
		
		relationship();
		program();
	}
	
	public static void relationship() {
		int count=0;
		while(count<actors.size()) {
			String a = actors.get(count);
			int count2 =0;
			while(count2<actors.size()) {
				String b = actors.get(count2);	
				if(!a.equals(b)) {
					g.addEdge(a, b);
					actsToMovs.put(a+b, movs);
				}
				count2++;
			}
			count++;
		}
	
	}

	public static void main(String args[]) throws FileNotFoundException{
		BritBraBaconDriver driver = new BritBraBaconDriver();
		//driver.run("src/test.txt");
		driver.run("test.txt");
	}
}
