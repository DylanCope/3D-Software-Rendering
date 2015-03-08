package softwarerendering.geometry;
import java.util.ArrayList;

import softwarerendering.Display;
import softwarerendering.RenderContext;
import softwarerendering.ViewPoint;
import softwarerendering.maths.Vector;


public class Cube {
	private float m_size;
	private ArrayList<Vector> m_vertices;
	private ArrayList<Edge> m_edges;
	private ArrayList<SquareFace> m_faces;
	private Vector m_position;
	
	private byte[] WHITE;
	
	public static enum RenderingOption {
		VERTEX_ONLY, WIREFRAME, SOLID_FILL
	};
	
	public RenderingOption m_renderingOption;
	
	private class SquareFace 
	{
		public Simplex2d s0, s1;
		public byte[] colour;
		
		public SquareFace(
				Vector v0, Vector v1, Vector v2, Vector v3, 
				Vector normal, byte[] colour)
		{
			s0 = new Simplex2d(v0, v1, v2); 
			s0.setNormal(normal);
			s1 = new Simplex2d(v3, v1, v2); 
			s1.setNormal(normal);
			this.colour = colour;
		}
		
		public SquareFace(Simplex2d s0, Simplex2d s1, byte[] colour)
		{
			this.s0 = s0; this.s1 = s1; this.colour = colour;
		}
		
		public void draw(ViewPoint view, RenderContext target)
		{
			s0.draw(view, target, colour);
			s1.draw(view, target, colour);
		}
		
		public SquareFace translate(Vector dv)
		{
			return new SquareFace(s0.translate(dv), s1.translate(dv), colour);
		}
		
		public boolean isFacingView(ViewPoint view)
		{
			return s0.facingView(view);
		}
		
		public void flipNormal() { s0 = s0.flipNormal(); s1 = s1.flipNormal(); }
	}
	
	public Cube(float x, float y, float z, float size)
	{
		m_size = size / 2;
		m_vertices = new ArrayList<Vector>();
		m_position = new Vector(x, y, z);
		m_renderingOption = RenderingOption.WIREFRAME;
		
		WHITE = new byte[4];
		WHITE[0] = (byte) 0xFF; WHITE[1] = (byte) 0xFF;
		WHITE[2] = (byte) 0xFF; WHITE[3] = (byte) 0xFF;
		
		updateVertices();
	}
	
	private void updateVertices()
	{	
		for (int i = 0; i < 8; i++) {
			String binaryString = Integer.toBinaryString(i);
			while (binaryString.length() < 3)
				binaryString = "0" + binaryString;
			Vector v = new Vector(m_size, m_size, m_size);
			
			for (int j = 0; j < binaryString.length(); j++)
				if (binaryString.charAt(j) == '1')
					v.getValues()[j] *= -1;
			
			m_vertices.add(v);
		}

		updateFaces();
		
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
	
	private void updateFaces()
	{
		m_faces = new ArrayList<SquareFace>();
		m_faces.add(new SquareFace(
				m_vertices.get(0), m_vertices.get(4), 
				m_vertices.get(1), m_vertices.get(5), 
				m_vertices.get(0).sub(m_vertices.get(4))
					.crossProduct(m_vertices.get(0).sub(m_vertices.get(1))), 
				Display.WHITE));
		m_faces.get(0).flipNormal();
	}
	
	public void draw(RenderContext target, ViewPoint view)
	{	
		if (m_renderingOption == RenderingOption.WIREFRAME)
			for (Edge edge : m_edges) {
				Vector p1 = edge.getPoints()[0].add(m_position);
				Vector p2 = edge.getPoints()[1].add(m_position);
				
				view.drawLine(p1, p2, target, WHITE);
			}
//		else if (m_renderingOption == RenderingOption.SOLID_FILL)
			for (SquareFace face : m_faces)
				if (face.isFacingView(view))
					face.translate(m_position.mul(-1)).draw(view, target);
	}
	
	public void rotate(Vector axis, float theta) {
		for (int i = 0; i < m_vertices.size(); i++)
			m_vertices.set(i, m_vertices.get(i).rotate(axis, theta));
		updateEdges();
		updateFaces();
	}
	
	public void scale(float k) {
		for (int i = 0; i < m_vertices.size(); i++)
			m_vertices.set(i, m_vertices.get(i).mul(k));
		updateEdges();
	}
	
	public void setSize(float size) { scale(0.5f * size / m_size); }
	
	public void setRenderingOption(RenderingOption option) { m_renderingOption = option; }
	public RenderingOption getRenderingOption() { return m_renderingOption; }

}
