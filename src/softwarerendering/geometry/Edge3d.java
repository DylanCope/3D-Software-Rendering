package softwarerendering.geometry;

import softwarerendering.maths.Vector;

public class Edge3d {
	
	private Vector source, target;
	private float x, xStep;
	private int yStart, yEnd;
	
	public Edge3d(Vector source, Vector target)
	{
		this.source = source; this.target = target;
	}
	
	public Vector[] getPoints() { return new Vector[] {source, target}; }
	
	public Vector getSource() { return source; }
	public Vector getTarget() { return target; }
	
//	int yStart = (int) Math.ceil(minYVert.getY());
//	int yEnd   = (int) Math.ceil(maxYVert.getY());
//	int xStart = (int) Math.ceil(minYVert.getX());
//	int xEnd   = (int) Math.ceil(maxYVert.getX());
//
//	int yDist = yEnd - yStart;
//	int xDist = xEnd - xStart;
//
//	if (yDist <= 0)
//		return;
//
//	float xStep = (float) xDist / (float) yDist;
//	float yPrestep = yStart - minYVert.getY();
//	float curX = minYVert.getX() + yPrestep * xStep;
//
//	for (int j = yStart; j < yEnd; j++) {
//		try {
//			m_scanBuffer[j * 2 + whichSide] = (int) Math.ceil(curX);
//		} catch (ArrayIndexOutOfBoundsException e) {}
//		
//		curX += xStep;
//	}
}
