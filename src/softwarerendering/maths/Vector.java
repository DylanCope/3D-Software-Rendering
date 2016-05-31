package softwarerendering.maths;

public class Vector 
{
	public static final Vector origin = new Vector(0, 0, 0);
	public static final Vector xAxis  = new Vector(1, 0, 0);
	public static final Vector yAxis  = new Vector(0, 1, 0);
	public static final Vector zAxis  = new Vector(0, 0, 1);
	
	private float[] m_values;
	
	public Vector(float i, float j, float k) {
		m_values = new float[] {i, j, k};
	}
	
	public Vector(float i, float j) {
		m_values = new float[] {i, j};
	}
	
	public Vector(float[] values) { 
		m_values = values;
	}
	
	public Vector(int size) {
		m_values = new float[size];
	}
	
	public float len() {
		float len = 0;
		for (float value : m_values)
			len += value * value;
		
		return (float) Math.sqrt(len);
	}
	
	public float distanceTo(Vector position)
	{
		return sub(position).len();
	}
	
	public Vector normalised()
	{
		float[] newValues = new float[m_values.length];
		float length = len();
		for (int i = 0; i < m_values.length; i++)
			newValues[i] = m_values[i] / length;
		return new Vector(newValues);
	}
	
	public float getAzimuthalAngle() {
		if (m_values.length == 3)
			return (float) Math.atan(m_values[0] / m_values[2]);
		return 0;
	}
	
	public float getElevationAngle() {
		if (m_values.length == 3)
			return (float) Math.asin(m_values[2] / len());
		return 0;
	}
	
	public float dotProduct(Vector b) {
		float product = 0;
		if (b.getValues().length == m_values.length)
			for (int i = 0; i < m_values.length; i++)
				product += m_values[i] * b.getValues()[i];
		return product;
			
	}
	
	public Vector crossProduct(Vector b) {
		if (m_values.length == 3)
			return new Vector(
					m_values[0] * b.getZ() - m_values[2] * b.getY(),
					m_values[2] * b.getX() - m_values[0] * b.getZ(),
					m_values[0] * b.getY() - m_values[1] * b.getX());
		else if (m_values.length == 2)
			return new Vector(
				new float[] {
					m_values[0] * b.getY() - m_values[1] * b.getX()
				});
		return null;
	}
	
	public float triangeArea(Vector v1, Vector v2)
	{
		return sub(v1).crossProduct(sub(v2)).getX() / 2f;
	}
	
	public double angleTo(Vector b) {
		double cos = dotProduct(b) / (len() * b.len());
		return Math.acos(cos);
	}
	
	public void rotate(float dA, float dE) {
		
		double e = Math.asin(m_values[2] / len());
		double a = Math.atan(m_values[0] / m_values[2]);
		
		m_values[1] = (float) (len() * Math.sin(e + dE));
		m_values[0] = (float) (len() * Math.cos(e + dE) * Math.sin(a + dA));
		m_values[2] = (float) (len() * Math.cos(e + dE) * Math.cos(a + dA));
		
	}
	
	public Vector rotate(Vector axis, float theta) {
		Vector normAxis = axis.normalised();
		Quaternion q = new Quaternion(normAxis, theta);
		return q.mul(this).mul(q.getConjugate()).vector();
	}
	
	public Vector vectorTo(Vector b) {
		float[] newValues = new float[m_values.length];
		if (b.getValues().length == m_values.length)
			for (int i = 0; i < m_values.length; i++)
				m_values[i] = b.getValues()[i] - m_values[i];
		return new Vector(newValues);
	}
	
	public float getX() { if (m_values.length > 0) return m_values[0]; return 0; }
	public float getY() { if (m_values.length > 1) return m_values[1]; return 0; }
	public float getZ() { if (m_values.length > 2) return m_values[2]; return 0; }
	public float getW() { if (m_values.length > 3) return m_values[3]; return 0; }
	
	public float[] getValues() { return m_values; }
	
	public Vector add(Vector b) 
	{ 
		if (b.getValues().length != m_values.length)
			return null;
		
		float[] newValues = new float[m_values.length];
		for (int i = 0; i < m_values.length; i++)
			newValues[i] = m_values[i] + b.getValues()[i];
		return new Vector(newValues);
	}
	
	public Vector sub(Vector b) { 
		return add(b.mul(-1));
	}
	
	public Vector mul(Vector b) { 
		float[] newValues = new float[m_values.length];
		for (int i = 0; i < m_values.length; i++)
			newValues[i] = m_values[i] * b.getValues()[i];
		return new Vector(newValues);
	}

	public Vector div(Vector b){ 
		float[] newValues = new float[m_values.length];
		for (int i = 0; i < m_values.length; i++)
			newValues[i] = m_values[i] / b.getValues()[i];
		return new Vector(newValues);
	}
	
	public Vector add(float k) { return add(new Vector(k, k, k)); }
	public Vector sub(float k) { return sub(new Vector(k, k, k)); }
	public Vector mul(float k) { return mul(new Vector(k, k, k)); }
	public Vector div(float k) { return div(new Vector(k, k, k)); }

	public String toString() { 
		String str = "(";
		for (int i = 0; i < m_values.length - 1; i++)
			str += m_values[i] + ", ";
		str += m_values[m_values.length - 1] + ")";
		return str;
	}

	public int getDimension() {
		return m_values.length;
	}
	
}
