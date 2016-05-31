package softwarerendering;

import softwarerendering.maths.Vector;

public class RenderContext extends Bitmap
{
	private final int m_scanBuffer[];
	
	public RenderContext(int width, int height)
	{
		super(width, height);
		m_scanBuffer = new int[height * 2];
	}
	
	public void DrawScanBuffer(int yCoord, int xMin, int xMax)
	{
		m_scanBuffer[yCoord * 2    ] = xMin;
		m_scanBuffer[yCoord * 2 + 1] = xMax;
	}
	
	public void fillShape(int yMin, int yMax, byte[] colour)
	{
		for(int j = yMin; j < yMax; j++)
		{
			int xMin = 0, xMax = 0;
			
			try {
				xMin = m_scanBuffer[j * 2];
			} catch (ArrayIndexOutOfBoundsException e) {}
			
			try {
				xMax = m_scanBuffer[j * 2 + 1];
			} catch (ArrayIndexOutOfBoundsException e) {}
			
			for(int i = xMin; i < xMax; i++)
				drawPixel(i, j, colour);
		}
	}

	public void scanTriangle(Vector minYVert, Vector midYVert, 
			Vector maxYVert, boolean handedness)
	{
		Edge topToBottom    = new Edge(minYVert, maxYVert);
		Edge topToMiddle    = new Edge(minYVert, midYVert);
		Edge middleToBottom = new Edge(midYVert, maxYVert);

		scanEdges(topToBottom, topToMiddle, handedness);
		scanEdges(topToBottom, middleToBottom, handedness);
		
		// Repeat for bottom half of triangle.
	}
	
	private void scanEdges(Edge a, Edge b, boolean handedness)
	{
		Edge left  = a;
		Edge right = b;
		
		if (handedness) {
			Edge temp = left;
			left = right; right = temp;
		}
		
		int yStart = b.getYStart();
		int yEnd = b.getYEnd();
		
		for (int j = yStart; j < yEnd; j++) {
			drawScanLine(left, right, j);
			left.step();
			right.step();
		}
	}
	
	private void drawScanLine(Edge left, Edge right, int j)
	{
		int xMin = (int) Math.ceil(left.getX());
		int xMax = (int) Math.ceil(right.getX());
		for(int i = xMin; i < xMax; i++)
			drawPixel(i, j, Display.WHITE);
	}
	
	public void fillTriangle(Vector v0, Vector v1, Vector v2, byte[] colour)
	{
		Vector min = v0, mid = v1, max = v2;
		
		if (max.getY() < mid.getY()) {
			Vector temp = max;
			max = mid; mid = temp;
		}
		
		if (min.getY() > mid.getY()) {
			Vector temp = min;
			min = mid; mid = temp;
		}
		
		if (max.getY() < mid.getY()) {
			Vector temp = max;
			max = mid; mid = temp;
		}
		
		boolean handedness = min.triangeArea(max, mid) >= 0;
		scanTriangle(min, mid, max, handedness);
		fillShape((int) Math.ceil(min.getY()), (int) Math.ceil(max.getY()), colour); 
	}
}
