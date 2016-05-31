package softwarerendering;

import softwarerendering.maths.Vector;

public class ViewPoint 
{
	
	private int m_renderDist, m_focalDist;
	private float m_FoV;
	private float m_azimuth, m_elevation;
	private Vector m_direction, m_pos; 
	public Vector up;
	
	public ViewPoint() {
		m_renderDist = 15000;
		m_focalDist = 5000;
		m_FoV = (float) Math.toRadians(90);
		m_pos = new Vector(0, 0, 0);
		m_azimuth = 0;
		m_elevation = 0;
		m_direction = new Vector(1, 0, 0);
		
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
		return m_FoV / 2 < m_direction.angleTo(point);
		
	}
	
	public float viewRangeAt(float depth) {
		return (float) (depth * Math.tan(m_FoV / 2));
	}
	
	public int[] getViewCoords(Vector point, Bitmap target) {
		Vector rel = m_pos
				.sub(point)
				.rotate(Vector.yAxis, -m_azimuth)
				.rotate(Vector.yAxis.crossProduct(m_direction), -m_elevation);
//		System.out.println(Vector.yAxis.crossProduct(m_direction));
		int[] coords = new int[2];
		
		float halfWidth = target.getWidth() / 2f;
		float halfHeight = target.getHeight() / 2f;
		
		float viewRange = viewRangeAt(rel.getZ());
		
		int xOnScreen = (int) (rel.getX() * halfWidth / viewRange + halfWidth);
		int yOnScreen = (int) (rel.getY() * halfWidth / viewRange + halfHeight);

		
		coords[0] = xOnScreen;
		coords[1] = yOnScreen;
		
		return coords;
	}
	
	public void drawPoint(Vector point, Bitmap target, byte[] colour)
	{
		
		Vector a = new Vector(point.getX(), point.getY(), point.getZ());
		
		a.sub(m_pos);
//		a.rotate(-m_azimuth, -m_elevation);
		
		int[] coords = getViewCoords(a, target);
		
		coords[0] += Math.tan(m_azimuth) * target.getWidth();
		coords[1] += Math.tan(m_elevation) * target.getWidth();
		
		target.drawPixel(coords[0], coords[1], colour);
			
	}
	
	public void drawLine(Vector a, Vector b, Bitmap target, byte[] colour)
	{
		int[] coordsA = getViewCoords(m_pos.sub(a), target);
		int[] coordsB = getViewCoords(m_pos.sub(b), target);
	
		target.drawLine(
				coordsA[0], coordsA[1], 
				coordsB[0], coordsB[1], 
				colour);
	}

//	public void drawLine(Vector a, Vector b, Bitmap target,
//			byte[] colour) {
//		
//		if (!isInView(a) || !isInView(b)) {
//			
//			Vector i, o;
//			
//			if (isInView(a) && !isInView(b)) {
//				i = a;
//				o = b;
//			} else if (!isInView(a) && isInView(b)) {
//				i = b;
//				o = a;
//			}else{
//				i = null;
//				o = null;
//			}
//			if(i != null && o != null){
//				float di = i.getI() - o.getI();
//				float dk = i.getK() - o.getK();
//				
//				float z = (i.getI() * di - i.getK() * dk) / 
//						(dk * (float)Math.tan(m_FoV/2) - di);
//				float x = 0.99f * (float) (z * Math.tan(m_FoV /2));
//				
//				int x1 = getViewCoords(new Vector(x, x, z), target)[0];
//				int y1 = getViewCoords(new Vector(x, x, z), target)[1];
//				
//				if (x1 != -1 && y1 != -1) {
//					int x2 = getViewCoords(i, target)[0];
//					int y2 = getViewCoords(i, target)[1];
//					target.drawLine(x1, y1, x2, y2, colour);
//				}
//			}
//		}
//	}
	
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
	
	public void setFOV(float FoV){
		m_FoV = (float) Math.toRadians(FoV);
	}
	
	public float getFOV(){
		return m_FoV;
	}
	
	public void move(float dx, float dy, float dz){
		m_pos = m_pos.add(new Vector(dx, dy, dz));
	}
	
	public void setPosition(float x, float y, float z){
		m_pos = new Vector(x, y, z);
	}
	
	public void rotateView(float azimuth, float elevation){
		m_azimuth   += (float) Math.toRadians(azimuth);
		m_elevation += (float) Math.toRadians(elevation);
		m_direction = new Vector(
				(float) Math.sin(m_elevation),
				(float) Math.sin(m_azimuth),
				(float) Math.cos(m_azimuth));
		
//		m_direction.rotate(Vector.yAxis, azimuth);
//		m_direction.rotate(Vector.yAxis.crossProduct(m_direction), elevation);
	}
	
	public void setView(float azimuth, float elevation){
		m_azimuth   = (float) Math.toRadians(azimuth);
		m_elevation = (float) Math.toRadians(elevation);
		m_direction = new Vector(
				(float) Math.sin(m_elevation),
				(float) Math.sin(m_azimuth),
				(float) Math.cos(m_azimuth));
	}
	
	public float getX(){
		return m_pos.getX();
	}
	
	public float getY(){
		return m_pos.getY();
	}
	
	public float getZ(){
		return m_pos.getZ();
	}
	
	public float getAzimuth(){
		return m_azimuth;
	}
	
	public float getElevation(){
		return m_elevation;
	}
	
	public Vector getDirection() {
		return m_direction;
	}
	
}
