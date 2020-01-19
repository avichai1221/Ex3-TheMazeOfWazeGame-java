package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dataStructure.EdgeData;

class EdgeDataTest {

	EdgeData e =new EdgeData(1,2,3);
	EdgeData y=new EdgeData();
	
	
	@Test
	void testEdgeData() {
		
	}

	

	@Test
	void testGetSrc() {
		assertEquals(e.getSrc(),1);
	}

	@Test
	void testGetDest() {
		assertEquals(e.getDest(),2);
	}

	@Test
	void testGetWeight() {
		assertEquals(e.getWeight(),3);
	}

	@Test
	void testGetInfo() {
		y.setInfo("cheak");
		assertEquals(y.getInfo(),"cheak");
		
	}

	

	@Test
	void testGetTag() {
		y.setTag(0);
		assertEquals(y.getTag(),0);
	}

	

}
