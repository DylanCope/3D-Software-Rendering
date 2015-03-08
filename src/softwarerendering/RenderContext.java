package softwarerendering;

import java.lang.reflect.Array;

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

	public void scanConvertTriangle(Vector minYVert, Vector midYVert, 
			Vector maxYVert, int handedness)
	{
		scanConvertLine(minYVert, maxYVert, 0 + handedness);
		scanConvertLine(minYVert, midYVert, 1 - handedness);
		scanConvertLine(midYVert, maxYVert, 1 - handedness);
	}

	private void scanConvertLine(Vector minYVert, Vector maxYVert, int whichSide)
	{
		int yStart = (int) minYVert.getY();
		int yEnd   = (int) maxYVert.getY();
		int xStart = (int) minYVert.getX();
		int xEnd   = (int) maxYVert.getX();

		int yDist = yEnd - yStart;
		int xDist = xEnd - xStart;

		if (yDist <= 0)
			return;

		float xStep = (float) xDist / (float) yDist;
		float curX = (float) xStart;

		for (int j = yStart; j < yEnd; j++) {
			try {
				m_scanBuffer[j * 2 + whichSide] = (int) curX;
			} catch (ArrayIndexOutOfBoundsException e) {}
			
			curX += xStep;
		}
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
		
		int handedness = min.triangeArea(max, mid) >= 0 ? 1 : 0;
		scanConvertTriangle(min, mid, max, handedness);
		fillShape((int) min.getY(), (int) max.getY(), colour); 
	}
}
