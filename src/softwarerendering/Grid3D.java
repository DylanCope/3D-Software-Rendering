package softwarerendering;

public class Grid3D {
	
	private final float m_spread;
	private final float m_x[], m_y[], m_z[];
	private int m_currentCol, m_currentRow, m_currentDep;
	private final int m_numPoints;
	
	
	public Grid3D(int numPoints, float spread){
		m_spread = spread;
		
		m_numPoints = numPoints;
		
		m_currentCol = - (int) Math.sqrt((double)(m_numPoints)) / 2;
		m_currentRow = - (int) Math.sqrt((double)(m_numPoints)) / 2;
		m_currentDep = - (int) Math.sqrt((double)(m_numPoints)) / 2;
		
		m_x = new float[numPoints];
		m_y = new float[numPoints];
		m_z = new float[numPoints];
		
		for(int i = 0; i < m_x.length; i++){
			placePoint(i);
		}
	}
	
	private void placePoint(int index){
		
		double halfGridSize = Math.sqrt((double)(m_numPoints)) / 2;
		
		if(m_currentCol >= halfGridSize){
			m_currentCol = - (int) halfGridSize;
			if(m_currentRow >= halfGridSize){
				m_currentRow = - (int) halfGridSize;
				if(m_currentDep >= halfGridSize){
					m_currentDep = - (int) halfGridSize;
				}else{
					m_currentDep++;
				}
			}else{
				m_currentRow++;
			}
		}else{
			m_currentCol++;
		}
		
		m_x[index] = ((float) m_currentRow) * m_spread;
		m_y[index] = ((float) m_currentCol) * m_spread;
		m_z[index] = ((float) m_currentDep) * m_spread;
		
	}
	
	public void offset(float offsetX, float offsetY, float offsetZ){
		for(int i = 0; i < m_x.length; i++){
			m_x[i] += offsetX;
			m_y[i] += offsetY;
			m_z[i] += offsetZ;
		}
	}
	
//	public void updateAndRender(Bitmap target, float delta, ViewPoint view){
//		
//		for(int i = 0; i < m_x.length; i++){
//			
//			int x;
//			int y;
//			
//			if (view.getScreenCoords(m_x[i], m_y[i], m_z[i], target) != null) {
//				
//				x = view.getScreenCoords(m_x[i], m_y[i], m_z[i], target)[0];
//				y = view.getScreenCoords(m_x[i], m_y[i], m_z[i], target)[1];
//				
//				if(m_z[i] > 0){
//					
//					float maxDist = view.getRenderDistance();
//					float focalDist = view.getFocalDistance();
//					
//					byte c;
//					
//					if(m_z[i] > focalDist && m_z[i] < maxDist){
//						c = (byte)(0xFF - 0xFF * 
//								(m_z[i] - focalDist)/(maxDist - focalDist));
//					}
//					else if(m_z[i] < focalDist){
//						c = (byte)0xFF;
//					}
//					else{
//						c = (byte)0x00;
//					}
//					
//					target.DrawPixel(x, y, c, c, c, c);
//				}
//				
//			}
//		}
//	}

}
