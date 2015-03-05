package softwarerendering;
import java.util.ArrayList;


public class Cube {
	
	private float m_size;
	private ArrayList<Vector> m_vertices;
	private ArrayList<Edge> m_edges;
	private Vector m_position;
	
	private byte[] WHITE;
	
	
	public Cube(float x, float y, float z, float size)
	{
		m_size = size / 2;
		m_vertices = new ArrayList<Vector>();
		m_position = new Vector(x, y, z);
		
		WHITE = new byte[4];
		WHITE[0] = (byte) 0xFF; WHITE[1] = (byte) 0xFF;
		WHITE[2] = (byte) 0xFF; WHITE[3] = (byte) 0xFF;
		
		updateVertices();
	}
	
	private void updateVertices()
	{	
		Vector v0 = new Vector( m_size,  m_size,  m_size);
		Vector v1 = new Vector(-m_size,  m_size,  m_size);
		Vector v2 = new Vector( m_size, -m_size,  m_size);
		Vector v3 = new Vector(-m_size, -m_size,  m_size);
		Vector v4 = new Vector( m_size,  m_size, -m_size);
		Vector v5 = new Vector(-m_size,  m_size, -m_size);
		Vector v6 = new Vector( m_size, -m_size, -m_size);
		Vector v7 = new Vector(-m_size, -m_size, -m_size);
		
		m_vertices.add(v0);
		m_vertices.add(v1);
		m_vertices.add(v2);
		m_vertices.add(v3);
		m_vertices.add(v4);
		m_vertices.add(v5);
		m_vertices.add(v6);
		m_vertices.add(v7);
		
		updateEdges();
		
	}
	
	private void updateEdges()
	{
		m_edges = new ArrayList<Edge>();
		m_edges.add(new Edge(m_vertices.get(0), m_vertices.get(4)));
		m_edges.add(new Edge(m_vertices.get(4), m_vertices.get(5)));
		m_edges.add(new Edge(m_vertices.get(5), m_vertices.get(1)));
		m_edges.add(new Edge(m_vertices.get(1), m_vertices.get(0)));
		m_edges.add(new Edge(m_vertices.get(0), m_vertices.get(2)));
		m_edges.add(new Edge(m_vertices.get(4), m_vertices.get(6)));
		m_edges.add(new Edge(m_vertices.get(5), m_vertices.get(7)));
		m_edges.add(new Edge(m_vertices.get(1), m_vertices.get(3)));
		m_edges.add(new Edge(m_vertices.get(6), m_vertices.get(2)));
		m_edges.add(new Edge(m_vertices.get(7), m_vertices.get(6)));
		m_edges.add(new Edge(m_vertices.get(3), m_vertices.get(7)));
		m_edges.add(new Edge(m_vertices.get(2), m_vertices.get(3)));
	}
	
	public void update(float delta, Bitmap target, ViewPoint view)
	{
//		for(int i = 0; i < m_vertices.size(); i++){
////			view.drawPoint(m_vertices.get(i).add(m_position), target, WHITE);
//			
//		}
		
		for (Edge edge : m_edges) {
			Vector p1 = edge.getPoints()[0].add(m_position);
			Vector p2 = edge.getPoints()[1].add(m_position);
			
			view.drawLine(p1, p2, target, WHITE);
		}
	}
	
	public void rotate(Vector axis, float theta) {
		for (int i = 0; i < m_vertices.size(); i++)
			m_vertices.set(i, m_vertices.get(i).rotate(axis, theta));
		updateEdges();
	}
	
	public void scale(float k) {
		for (int i = 0; i < m_vertices.size(); i++)
			m_vertices.set(i, m_vertices.get(i).mul(k));
		updateEdges();
	}
	
	public void setSize(float size) { scale(0.5f * size / m_size); }
	
}
