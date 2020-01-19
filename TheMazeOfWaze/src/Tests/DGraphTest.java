package Tests;

import static org.junit.Assert.*;


import org.junit.Test;

import dataStructure.DGraph;
import dataStructure.NodeData;

public class DGraphTest {
	
	@Test
	public void testGetNodeAndAddNode()
	{
		NodeData test=new NodeData(5,null,1.0,"red",1);
		DGraph DG =new DGraph();
		DG.addNode(test);
		assertEquals(DG.getNode(5).toString(), "5");
	}

	@Test
	public void testGetEdgeAndtestConnect()
	{
		
		NodeData test=new NodeData(2,null,4.0,"red",1);
		NodeData test2=new NodeData(5,null,1.0,"red",1);
		DGraph DG =new DGraph();
		DG.addNode(test);
		DG.addNode(test2);
		DG.connect(2, 5, 4);
		assertEquals(DG.getEdge(2,5).toString(),"2-----4.0----->5");
	}

	@Test
	public void testGetVAndtestGetE()
	{
		NodeData test=new NodeData(5,null,1.0,null,1);
		NodeData test1=new NodeData(2,null,2.0,null,2);
		NodeData test2=new NodeData(4,null,333.0,null,333);
	
		DGraph a1 =new DGraph();
		a1.addNode(test);
		a1.addNode(test1);
	    a1.connect(2, 54, 4);
	    a1.connect(2, 51, 4);
		assertEquals(a1.getE(2).toString(),"[2-----4.0----->51, 2-----4.0----->54]");
		assertEquals(a1.getV().toString(),"[2, 5]");

	}


	@Test
	public void testRemoveNodeAndtestRemoveEdge()
	{
		NodeData test1=new NodeData(2,null,2.0,null,2);
		NodeData test2=new NodeData(4,null,333.0,null,333);
		DGraph a1 =new DGraph();
		a1.addNode(test1);
		a1.addNode(test2);
		assertEquals(a1.getV().toString(),"[2, 4]");
		a1.removeNode(4);
		
		assertEquals(a1.getV().toString(),"[2]");
		a1.connect(2, 51, 4);
		a1.connect(2, 50, 4);
		assertEquals(a1.getE(2).toString(),"[2-----4.0----->50, 2-----4.0----->51]");
		a1.removeEdge(2,50);
		assertEquals(a1.getE(2).toString(),"[2-----4.0----->51]");

	}
	

	@Test
	public void testNodeSizeAndtestEdgeSize() 
	{
		NodeData test=new NodeData(5,null,1.0,null,1);
		NodeData test1=new NodeData(2,null,2.0,null,2);
		NodeData test2=new NodeData(4,null,333.0,null,333);
	
		DGraph a1 =new DGraph();
		DGraph a2 =new DGraph();
		a1.addNode(test);
		a1.addNode(test1);
		a1.addNode(test2);
	    a1.connect(2, 54, 4);
	    a1.connect(2, 51, 4);
	    a1.connect(5, 51, 4);
	    a2.addNode(test);
		a2.addNode(test1);
	    a2.connect(2, 54, 4);
	    a1.removeEdge(2, 54);
	    assertEquals(a1.nodeSize(),3);
	    assertEquals(a1.edgeSize(),2);
	    assertEquals(a2.nodeSize(),2);
	    assertEquals(a2.edgeSize(),1);
	}


	@Test
	public void testGetMC()
	{
		NodeData test=new NodeData(5,null,1.0,null,1);
		NodeData test1=new NodeData(2,null,2.0,null,2);
		NodeData test2=new NodeData(4,null,333.0,null,333);
		
		DGraph a1 =new DGraph();
		a1.addNode(test);
		a1.addNode(test1);
		a1.addNode(test2);
	    a1.connect(2, 54, 4);

	    a1.removeEdge(2, 54);
	    assertEquals(a1.getMC(),5);
	}



}
