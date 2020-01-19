package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.NodeData;
import dataStructure.graph;
import dataStructure.node_data;

public class Graph_AlgoTest {
	NodeData test1=new NodeData(2,null,2.0,null,2);
	NodeData test2=new NodeData(4,null,333.0,null,333);
	NodeData test3=new NodeData(2,null,2.0,null,2);
	NodeData test4=new NodeData(4,null,333.0,null,333);
	NodeData test5=new NodeData(5,null,333.0,null,333);
	NodeData testhelp=new NodeData(2,null,4.0,null,0);
	
	
	@Test
	public void testInitGraph()
	{
		
	
		DGraph a1 =new DGraph();
		a1.addNode(test1);
		a1.addNode(test2);
	    a1.connect(2, 54, 4);
	    Graph_Algo g=new Graph_Algo();
	    g.init(a1);
	    System.out.println(g.getE(2).toString());
		assertEquals(g.getV().toString(), "[2, 4]");
		assertEquals(g.getE(2).toString(), "[2-----4.0----->54]");
	    
	}

	@Test
	public void testIsConnected()
	{
		
		DGraph a1 =new DGraph();
		a1.addNode(test1);
		a1.addNode(test2);
	    a1.connect(2, 54, 4);
	    Graph_Algo g=new Graph_Algo();
	    g.init(a1);
	    
	    assertFalse(g.isConnected());
	    
	    
		DGraph a2 =new DGraph();
		a2.addNode(test3);
		a2.addNode(test4);
		a2.addNode(test5);
	    a2.connect(2, 5, 4);
	    a2.connect(5, 2, 4);
	    a2.connect(2, 4, 4);
	    a2.connect(4, 5, 4);
	    g.init(a2);
	    
	    assertTrue(g.isConnected());
	}

	@Test
	public void testShortestPathDist() {
		DGraph a3 =new DGraph();
		a3.addNode(test3);
		a3.addNode(test4);
		a3.addNode(test5);
	    a3.connect(2, 5, 13);
	    a3.connect(5, 2, 4);
	    a3.connect(2, 4, 2);
	    a3.connect(4, 5, 2);
	    Graph_Algo g=new Graph_Algo();
	    g.init(a3);
	   
		assertEquals((int)4,(int)g.shortestPathDist(2,5));

	}

	@Test
	public void testShortestPath() {
		DGraph a3 =new DGraph();
		a3.addNode(test3);
		a3.addNode(test4);
		a3.addNode(test5);
	    a3.connect(2, 5, 13);
	    a3.connect(5, 2, 4);
	    a3.connect(2, 4, 2);
	    a3.connect(4, 5, 2);
	    Graph_Algo g=new Graph_Algo();
	    g.init(a3);
	  g.shortestPath(5,2);
	  ArrayList<NodeData> a=new ArrayList <NodeData>();
	  a.add(test5);
	  a.add(testhelp);
	  assertTrue(a.toString().equals(g.shortestPath(5,2).toString()));
	}

	@Test
	public void testTSP() {
		NodeData test1=new NodeData(2,null,2.0,null,2);
		NodeData test2=new NodeData(4,null,3.0,null,3);
		NodeData test3=new NodeData(5,null,7.0,null,2);
		DGraph graftest =new DGraph();
		  Graph_Algo algotest=new Graph_Algo();
		  graftest.addNode(test1);
		  graftest.addNode(test2);
		  graftest.addNode(test3);
		  graftest.connect(2, 5, 1);
		  graftest.connect(2, 4, 8);
		  graftest.connect(4, 5, 5);
		  graftest.connect(5, 2, 2);
		  graftest.connect(4, 2, 1);
		  algotest.init(graftest); 
		 LinkedList<Integer> a=new LinkedList <Integer>();
		 List<node_data> actual = new LinkedList<node_data>();
		 NodeData actual1=new NodeData(4,null,0,"done",0);
		NodeData actual2=new NodeData(2,null,1,null,0);
		NodeData actual3=new NodeData(5,null,2,null,0);
		 a.add(4);
		 a.add(5);
		actual.add(actual1);
		actual.add(actual2);
		actual.add(actual3);
		 assertTrue(algotest.TSP(a).toString().equals(actual.toString()));
	}

	@Test
	public void testCopy() {
		NodeData test=new NodeData(5,null,1.0,null,1);
		NodeData test1=new NodeData(2,null,2.0,null,2);
		NodeData test2=new NodeData(4,null,333.0,null,333);
	
		DGraph a1 =new DGraph();
		a1.addNode(test);
		a1.addNode(test1);
		a1.addNode(test2);
	    a1.connect(2, 5, 4);
	    a1.connect(2, 4, 4);
	    a1.connect(5, 2, 4);
	    a1.connect(4, 5, 30);
	    Graph_Algo g1=new Graph_Algo();
	    g1.init(a1);
		graph copy=g1.copy();
		assertEquals(copy.getV().toString(),g1.getV().toString());
		
	}

}
