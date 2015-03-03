
public class Vector {
	
	private float m_i, m_j, m_k;
	
	public Vector(float i, float j, float k){
		m_i = i;
		m_j = j;
		m_k = k;
	}
	
	public double getMod(){
		return Math.sqrt(
				Math.pow(m_i, 2f) +
				Math.pow(m_j, 2f) +
				Math.pow(m_k, 2f));
	}
	
	public double getAzimuthalAngle(){
		return Math.atan(m_i/m_k);
	}
	
	public double getElevationAngle(){
		return Math.asin(.1 * m_k/getMod());
	}
	
	public float dotProduct(Vector b){
		return m_i * b.getI()
				+ m_j * b.getJ()
				+ m_k * b.getK();
	}
	
	public double angleTo(Vector b){
		double cos = dotProduct(b) / (getMod() * b.getMod());
		return Math.acos(cos);
	}
	
	public void rotate(float dA, float dE){
		
		double e = Math.asin(m_j / getMod());
		double a = Math.atan(m_i/m_k);
		
		m_j = (float) (getMod() * Math.sin(e + dE));
		m_i = (float) (getMod() * Math.cos(e + dE) * Math.sin(a + dA));
		m_k = (float) (getMod() * Math.cos(e + dE) * Math.cos(a + dA));
		
	}
	
	public void translate(float di, float dj, float dk){
		m_i += di;
		m_j += dj;
		m_k += dk;
	}
	
	public Vector vectorTo(Vector b){
		float i = b.getI() - m_i;
		float j = b.getJ() - m_j;
		float k = b.getK() - m_k;
		return new Vector(i, j, k);
	}
	
	public float getI(){
		return m_i;
	}
	
	public float getJ(){
		return m_j;
	}
	
	public float getK(){
		return m_k;
	}
	
}
