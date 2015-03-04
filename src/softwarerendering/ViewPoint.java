package softwarerendering;

public class ViewPoint 
{
	
	private int m_renderDist, m_focalDist;
	private float m_FoV, m_x, m_y, m_z;
	private float m_azimuth, m_elevation;
	private Vector direction; 
	public Vector up;
	
	public ViewPoint() {
		m_renderDist = 15000;
		m_focalDist = 5000;
		m_FoV = (float) Math.toRadians(90);
		m_x = 0;
		m_y = 0;
		m_z = 0;
		m_azimuth = 0;
		m_elevation = 0;
		direction = new Vector(1, 0, 0);
		
	}
	
	public boolean isInView(Vector point) {
//		double viewRangeX = point.getK() * Math.tan(m_azimuth + m_FoV / 2);
//		double viewRangeY = point.getK() * Math.tan(m_elevation + m_FoV / 2);
//		
//		if (Math.abs(point.getI()) <= viewRangeX &&
//				Math.abs(point.getJ()) <= viewRangeY) {
//			return true;
//		} else {
//			return false;
//		}
		return m_FoV / 2 < direction.angleTo(point);
		
	}
	
	public int[] getViewCoords(Vector point, Bitmap target) {
		Vector yAxis = new Vector(0, 1, 0);
		Vector rel = point
				.rotate(yAxis, -m_azimuth)
				.rotate(yAxis.crossProduct(direction), -m_elevation);
		int[] coords = new int[2];
		
		float halfWidth = target.getWidth() / 2f;
		float halfHeight = target.getHeight() / 2f;
		
		float viewRange = (float) (point.getK() * Math.tan(m_FoV / 2));
		
		int xOnScreen = (int) (point.getI() * halfWidth / viewRange + halfWidth);
		int yOnScreen = (int) (point.getJ() * halfWidth / viewRange + halfHeight);

		
		coords[0] = xOnScreen;
		coords[1] = yOnScreen;
		
		return coords;
	}
	
	public void drawPoint(Vector point, Bitmap target, byte[] colour){
		
		Vector a = new Vector(point.getI(), point.getJ(), point.getK());
		
		a.translate(-m_x, -m_y, -m_z);
//		a.rotate(-m_azimuth, -m_elevation);
		
		int[] coords = getViewCoords(a, target);
		
		coords[0] += Math.tan(m_azimuth) * target.getWidth();
		coords[1] += Math.tan(m_elevation) * target.getWidth();
		
		target.DrawPixel(coords[0], coords[1], colour);
			
	}

	public void drawLine(Vector a, Vector b, Bitmap target,
			byte[] colour) {
		
		if (!isInView(a) || !isInView(b)) {
			
			Vector i, o;
			
			if (isInView(a) && !isInView(b)) {
				i = a;
				o = b;
			} else if (!isInView(a) && isInView(b)) {
				i = b;
				o = a;
			}else{
				i = null;
				o = null;
			}
			if(i != null && o != null){
				float di = i.getI() - o.getI();
				float dk = i.getK() - o.getK();
				
				float z = (i.getI() * di - i.getK() * dk) / 
						(dk * (float)Math.tan(m_FoV/2) - di);
				float x = 0.99f * (float) (z * Math.tan(m_FoV /2));
				
				int x1 = getViewCoords(new Vector(x, x, z), target)[0];
				int y1 = getViewCoords(new Vector(x, x, z), target)[1];
				
				if (x1 != -1 && y1 != -1) {
					int x2 = getViewCoords(i, target)[0];
					int y2 = getViewCoords(i, target)[1];
					target.drawLine(x1, y1, x2, y2, colour);
				}
				
			}
			
		}
		
		
	}
	
	public void setRenderDistance(int renderDistance){
		m_renderDist = renderDistance;
	}
	
	public int getRenderDistance(){
		return m_renderDist;
	}
	
	public void setFocalDistance(int focalDistance){
		m_focalDist = focalDistance;
	}
	
	public int getFocalDistance(){
		return m_focalDist;
	}
	
	public void setFOV(float FOV){
		m_FoV = (float) Math.toRadians(FOV);
	}
	
	public float getFOV(){
		return m_FoV;
	}
	
	public void move(float dx, float dy, float dz){
		m_x += dx;
		m_y += dy;
		m_z += dz;
	}
	
	public void setPosition(float x, float y, float z){
		m_x = x;
		m_y = y;
		m_z = z;
	}
	
	public void rotateView(float azimuth, float elevation){
		m_azimuth   += (float) Math.toRadians(azimuth);
		m_elevation += (float) Math.toRadians(elevation);
	}
	
	public void setView(float azimuth, float elevation){
		m_azimuth   = (float) Math.toRadians(azimuth);
		m_elevation = (float) Math.toRadians(elevation);
	}
	
	public float getX(){
		return m_x;
	}
	
	public float getY(){
		return m_y;
	}
	
	public float getZ(){
		return m_z;
	}
	
	public float getAzimuth(){
		return m_azimuth;
	}
	
	public float getElevation(){
		return m_elevation;
	}
		
	
}
