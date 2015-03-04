package softwarerendering;
import java.util.ArrayList;


public class Cube {
	
	private float m_size;
	private ArrayList<Vector> m_vertices;
	private Vector m_position;
	
	private byte[] WHITE;
	
	
	public Cube(float x, float y, float z, float size){
		m_size = size;
		m_vertices = new ArrayList<Vector>();
		m_position = new Vector(x, y, z);
		
		WHITE =  new byte[4];
		WHITE[0] = (byte)0xFF; WHITE[1] = (byte)0xFF;
		WHITE[2] = (byte)0xFF; WHITE[3] = (byte)0xFF;
		
		m_vertices.add(new Vector(
				m_size,
				m_size,
				m_size
				));
		m_vertices.add(new Vector(
				m_size,
				- m_size,
				- m_size
				));
		m_vertices.add(new Vector(
				m_size,
				- m_size,
				m_size
				));
		m_vertices.add(new Vector(
				m_size,
				m_size,
				- m_size
				));
		m_vertices.add(new Vector(
				- m_size,
				m_size,
				m_size
				));
		m_vertices.add(new Vector(
				- m_size,
				- m_size,
				- m_size
				));
		m_vertices.add(new Vector(
				- m_size,
				- m_size,
				m_size
				));
		m_vertices.add(new Vector(
				- m_size,
				m_size,
				- m_size
				));
		
	}
	
	public void update(float delta, Bitmap target, ViewPoint view){

		for(int i = 0; i < m_vertices.size(); i++){
			
			view.drawPoint(m_vertices.get(i).add(m_position), target, WHITE);
			
			for(int j = i + 1; j < m_vertices.size(); j++){
				if(m_vertices.get(i).vectorTo(m_vertices.get(j)).len() < m_size * 2.5){
					view.drawLine(m_vertices.get(i), m_vertices.get(j), target, WHITE);
				}
			}
			
		}
	}
	
	public void rotate(Vector axis, float theta) {
		for (int i = 0; i < m_vertices.size(); i++)
			m_vertices.set(i, m_vertices.get(i).rotate(axis, theta));
	}
	
}
