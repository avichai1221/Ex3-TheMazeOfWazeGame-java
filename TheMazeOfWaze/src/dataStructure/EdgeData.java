package dataStructure;

import java.io.Serializable;
/**
 * directional edge(src,dest) in a (directional) weighted graph.
 * @author avichai and tal
 *
 */
public class EdgeData implements edge_data,Serializable  {

	private static final long serialVersionUID = 1L;
	private int src; 
	private int dst; 
	private double w;
	private String info;
	private int tag;
	
	
	/*
	 * Contractor with all parameters
	 */
	public EdgeData() {
		 this.src=0; 
		 this.dst=0; 
		 this.w=0; 
		 this.info=""; 
		 this.tag=0;
	}
	
	
	
	/*
	 * Contractor with source,destination and weight
	 */
	public EdgeData(int s, int d,double weight) {
		 this.src=s; 
		 this.dst=d;
		 this.w=weight;
	
	}
	
	/**
	 * The id of the source node of this edge.
	 * @return
	 */
	@Override
	public int getSrc() {
		
		return this.src;
	}
	/**
	 * The id of the destination node of this edge
	 * @return
	 */
	@Override
	public int getDest() {
		
		return this.dst;
	}
	/**
	 * @return the weight of this edge (positive value).
	 */
	@Override
	public double getWeight() {
		return this.w;
	}
	/**
	 * return the remark (meta data) associated with this edge.
	 * @return
	 */
	@Override
	public String getInfo() {
		return this.info;
	}
	/**
	 * Allows changing the remark (meta data) associated with this edge.
	 * @param s
	 */
	@Override
	public void setInfo(String s) {
		this.info=s;
		
	}
	/**
	 * Temporal data (aka color: e,g, white, gray, black) 
	 * which can be used be algorithms 
	 * @return
	 */
	@Override
	public int getTag() {
	return this.tag;
	}
	/** 
	 * Allow setting the "tag" value for temporal marking an edge - common 
	 * practice for marking by algorithms.
	 * @param t - the new value of the tag
	 */
	@Override
	public void setTag(int t) {
		this.tag=t;
		
	}
	/*
	 * string to string function
	 */
	public String toString() {
		 
		return src + "-----" + w + "----->" +dst;
	}
}
