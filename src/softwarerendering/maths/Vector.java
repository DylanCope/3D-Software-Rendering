package softwarerendering.maths;

public class Vector 
{
	public static final Vector xAxis = new Vector(1, 0, 0);
	public static final Vector yAxis = new Vector(0, 1, 0);
	public static final Vector zAxis = new Vector(0, 0, 1);
	
	private float m_i, m_j, m_k;
	
	public Vector(float i, float j, float k) {
		m_i = i;
		m_j = j;
		m_k = k;
	}
	
	public float len() {
		return (float) Math.sqrt(
				Math.pow(m_i, 2f) +
				Math.pow(m_j, 2f) +
				Math.pow(m_k, 2f));
	}
	
	public Vector normalised()
	{
		return new Vector(m_i, m_j, m_k).div(len());
	}
	
	public float getAzimuthalAngle() {
		return (float) Math.atan(m_i / m_k);
	}
	
	public float getElevationAngle() {
		return (float) Math.asin(m_k / len());
	}
	
	public float dotProduct(Vector b) {
		return m_i * b.getI()
				+ m_j * b.getJ()
				+ m_k * b.getK();
	}
	
	public Vector crossProduct(Vector b) {
		return new Vector(
				m_j * b.getK() - m_k * b.getJ(),
				m_k * b.getI() - m_i * b.getK(),
				m_i * b.getJ() - m_j * b.getI());
	}
	
	public double angleTo(Vector b) {
		double cos = dotProduct(b) / (len() * b.len());
		return Math.acos(cos);
	}
	
	public void rotate(float dA, float dE) {
		
		double e = Math.asin(m_j / len());
		double a = Math.atan(m_i / m_k);
		
		m_j = (float) (len() * Math.sin(e + dE));
		m_i = (float) (len() * Math.cos(e + dE) * Math.sin(a + dA));
		m_k = (float) (len() * Math.cos(e + dE) * Math.cos(a + dA));
		
	}
	
	public Vector rotate(Vector axis, float theta) {
		Vector normAxis = axis.normalised();
		Quaternion q = new Quaternion(normAxis, theta);
		return q.mul(this).mul(q.getConjugate()).vector();
	}
	
	public void translate(float di, float dj, float dk) {
		m_i += di;
		m_j += dj;
		m_k += dk;
	}
	
	public Vector vectorTo(Vector b) {
		float i = b.getI() - m_i;
		float j = b.getJ() - m_j;
		float k = b.getK() - m_k;
		return new Vector(i, j, k);
	}
	
	public float getI() {
		return m_i;
	}
	
	public float getJ() {
		return m_j;
	}
	
	public float getK() {
		return m_k;
	}
	
	public Vector add(Vector b) { 
		return new Vector(m_i + b.getI(), m_j + b.getJ(), m_k + b.getK());
	}
	
	public Vector sub(Vector b) { 
		return new Vector(m_j - b.getI(), m_j - b.getJ(), m_k - b.getK());
	}
	
	public Vector mul(Vector b) { 
		return new Vector(m_i * b.getI(), m_j * b.getJ(), m_k * b.getK());
	}
	
	public Vector div(Vector b){
		return new Vector(m_i / b.getI(), m_j / b.getJ(), m_k / b.getK());
	}
	
	public Vector add(float k) { return add(new Vector(k, k, k)); }
	public Vector sub(float k) { return sub(new Vector(k, k, k)); }
	public Vector mul(float k) { return mul(new Vector(k, k, k)); }
	public Vector div(float k) { return div(new Vector(k, k, k)); }

	public String toString() { return "(" + m_i + ", " + m_j + ", " + m_k + ")"; }
	
}
