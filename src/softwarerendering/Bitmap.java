package softwarerendering;
import java.util.Arrays;

public class Bitmap {
	
	private final int m_width;
	private final int m_height;
	private final byte m_components[];
	
	public Bitmap(int width, int height){
		m_width = width;
		m_height = height;
		m_components = new byte[width * height * 4];
		
	}
	
	public void clear(byte shade){
		Arrays.fill(m_components, shade);
	}
	
	public void drawPixel(int x, int y, byte[] colour) 
	{	
		int index = (x + y * m_width) * 4;
		
		if(index + 3 < m_components.length && index >= 0){
			if(x <= m_width && x >= 0 
					&& y <= m_height && y >= 0){
				m_components[index    ] = colour[0];
				m_components[index + 1] = colour[1];
				m_components[index + 2] = colour[2];
				m_components[index + 3] = colour[3];
			}
		}
			
	}
	
	public void drawLine(int x1, int y1, int x2, int y2, 
			byte[] colour){
		
		int dx = x1 - x2;
		int dy = y1 - y2;
		
		int steps, k;
		float xIncrement, yIncrement, x = x1, y = y1;
		
		if(Math.abs(dx) > Math.abs(dy)){
			steps = Math.abs(dx);
		}else{
			steps = Math.abs(dy);
		}
		xIncrement = dx / (float) steps;
		yIncrement = dy / (float) steps;
		
		drawPixel((int)Math.round(x), (int)Math.round(y), colour);
		
		for(k = 0; k < steps; k++){
			x -= xIncrement;
			y -= yIncrement;
			drawPixel((int)Math.round(x), (int)Math.round(y), colour);
		}
		
	}
	
	public void copyToByteArray(byte[] dest){
		for(int i = 0; i < m_width * m_height; i++){
			dest[i*3    ] = m_components[i*4 + 1];
			dest[i*3 + 1] = m_components[i*4 + 2];
			dest[i*3 + 2] = m_components[i*4 + 3];
		}
		
}

	public int getWidth() {
		return m_width;
	}

	public int getHeight() {
		return m_height;
	}
	

}
