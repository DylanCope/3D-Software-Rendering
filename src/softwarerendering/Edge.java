package softwarerendering;

import softwarerendering.maths.Vector;

public class Edge {
	
	private float xStep, x;
	private int yStart, yEnd;

	public Edge(Vector source, Vector target)
	{	
		Vector minYVert = source.getY() > target.getY() ? target : source;
		Vector maxYVert = source.getY() > target.getY() ? source : target;
		
		yStart = (int) Math.ceil(minYVert.getY());
		yEnd = (int) Math.ceil(maxYVert.getY());
		
		float yDist = maxYVert.getY() - minYVert.getY();
		float xDist = maxYVert.getX() - minYVert.getX();
		float yPrestep = yStart - minYVert.getY();
		
		xStep = (float) xDist / (float) yDist;
		x = minYVert.getX() + yPrestep * xStep;
	}
	
	public void step()
	{
		x += xStep;
	}
	
	public float getXStep() { return xStep; }
	public float getX() { return x; }
	public int getYStart() { return yStart; }
	public int getYEnd() { return yEnd; }
	
}
