package softwarerendering.geometry;

import softwarerendering.maths.Vector;

public class Edge {
	
	private Vector p1, p2;
	
	public Edge(Vector p1, Vector p2)
	{
		this.p1 = p1; this.p2 = p2;
	}
	
	public Vector[] getPoints() { return new Vector[] {p1, p2}; }
}
