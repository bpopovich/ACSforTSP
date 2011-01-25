package ims.acs.main;

import ims.aco.visualize.MainWindow;
import ims.acs.algorithm.*;

import java.util.*;
import java.awt.EventQueue;

public class Test
{

	private HashMap<Integer, City> gradovi;
	private MainWindow frame;

	public static void main(String[] args)
	{
		new Test(args);
	}

	public Test(String[] args) {
		if(args.length < 4)
		{
			return;
		}

		int AntNumber = 0;
		int iterationsNumber = 0;
		int nodesNumber;

		for (int i = 0; i < args.length; i+=2)
		{
			if(args[i].equals("-a"))
			{
				AntNumber = Integer.parseInt(args[i + 1]);
				System.out.println("Ants: " + AntNumber);
			}
			else if(args[i].equals("-i"))
			{
				iterationsNumber = Integer.parseInt(args[i + 1]);
				System.out.println("Iterations: " + iterationsNumber );
			}
		}

		if(AntNumber == 0 ||  iterationsNumber == 0)
		{
			System.out.println("One of the parameters is wrong");
			return;
		}

		InputReader reader = new InputReader("kroejsa.txt");
		gradovi = reader.read();
		nodesNumber = gradovi.size();

		launchGUI();

		double d[][] = new double[nodesNumber][nodesNumber];

		for(int i = 0; i < nodesNumber; i++)
			for(int j = i + 1; j < nodesNumber; j++)
			{	
				d[i][j] = gradovi.get(i).distance(gradovi.get(j));
				d[j][i] = d[i][j];
			}

		GrafGradova graph = new GrafGradova(nodesNumber, d);

		while(frame == null){}
		
		Colony antColony = new Colony(graph, AntNumber, iterationsNumber, frame.getDrawingPanel());
		antColony.start();
		System.out.println(antColony.getBestPathValue() + "," + antColony.getLastBestPathIteration());
		frame.getDrawingPanel().updateLines(antColony.getBestPathVector());
		frame.getDrawingPanel().updateBestPathRoute(antColony.getBestPathVector());

	}

	private void launchGUI() {
		EventQueue.invokeLater(new Runnable() {


			public void run() {
				try {
					frame = new MainWindow(gradovi);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
