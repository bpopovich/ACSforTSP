package ims.acs.algorithm;

import ims.aco.visualize.DrawingPanel;

import java.util.*;

public class Colony implements Observer
{
    private GrafGradova graf;
    private Ant[] ants;
    private int antNum, antCounter, iterationsCount, iterationsMax;
    
    private DrawingPanel canvas;
    private double lastBest = Double.MAX_VALUE;
    
    private static final double alpha = 0.08;
    
    public Colony(GrafGradova graph, int nAnts, int nIterations, DrawingPanel canvas)
    {
    	this.canvas = canvas;
        graf = graph;
        antNum = nAnts;
        iterationsMax = nIterations;
    }
    
    public synchronized void start()
    {
        ants  = antFactory(graf, antNum);
        
        iterationsCount = 0;
        
        while(iterationsCount < iterationsMax)
        {
            iteration();
            	
            try
            {
                wait();
            }
            catch(InterruptedException ex)
            {
                ex.printStackTrace();
            }
            
            synchronized(graf)
            {
                evaporatePheromones();
            }
        }
    }
    
    private void iteration()
    {
        antCounter = 0;
        iterationsCount++;
        
        canvas.updateLines(this.getBestPathVector());
        
        double best = getBestPathValue();
        if (best < lastBest){
        	lastBest = best;
        	canvas.updateBestPath(String.valueOf(this.getBestPathValue()), iterationsCount);
        }
        
    	
        for(int i = 0; i < ants.length; i++)
        {
            ants[i].start();
        }
    }
    
    public GrafGradova getGraph()
    {
        return graf;
    }
    
    public int getAnts()
    {
        return ants.length;
    }
    
    public int getIterations()
    {
        return iterationsMax;
    }
    
    public int getIterationCounter()
    {
        return iterationsCount;
    }
    
    public synchronized void update(Observable ant, Object obj)
    {
        antCounter++;
        
        if(antCounter == ants.length)
        {
            notify();
        }
    }
    
    public double getBestPathValue()
    {
        return Ant.OverallbestPathValue;
    }
    
    public int[] getBestPath()
    {
        return Ant.getBestPath();
    }
    
    public Vector<Integer> getBestPathVector()
    {
        return Ant.overallBestPathVect;
    }
    
    public int getLastBestPathIteration()
    {
        return Ant.lastBestPathIterationNumber;
    }
    
    public boolean done()
    {
        return iterationsCount == iterationsMax;
    }
    
    private Ant[] antFactory(GrafGradova graph, int nAnts){
    	Random ran = new Random(System.currentTimeMillis());
        Ant.reset();
        Ant.setAntColony(this);
        Ant ant[] = new Ant[nAnts];
        
        for(int i = 0; i < nAnts; i++)
        {
            ant[i] = new Ant((int)(graph.nodes() * ran.nextDouble()), this);
        }
        
        return ant;
    }
    
    private void evaporatePheromones() {
    	double dEvaporation = 0;
        double dDeposition  = 0;
        
        for(int r = 0; r < graf.nodes(); r++)
        {
            for(int s = 0; s < graf.nodes(); s++)
            {
                if(r != s)
                {
                    double deltaTau = ((double)1 / Ant.OverallbestPathValue) * (double)Ant.bestPath[r][s];
                                    
                    dEvaporation = ((double)1 - alpha) * graf.tau(r,s);
                    dDeposition  = alpha * deltaTau;
                    
                    graf.updateTau(r, s, dEvaporation + dDeposition);
                }
            }
        }
    }
}
