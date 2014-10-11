
public class Matrix {
	
	float[] m_values;
	int m_width, m_height;
	
	public Matrix(int width, int height){
		m_values = new float[width * height];
	}
	
	
	public float getValue(int i, int j){
		return m_values[j * m_width + i];
	}
	
	public void setValue(int i, int j, float value){
		m_values[j * m_width + i] = value;
	}
	
}
