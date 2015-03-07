package softwarerendering.geometry;

import softwarerendering.Bitmap;
import softwarerendering.ViewPoint;
import softwarerendering.maths.Vector;

public class Simplex2d 
{
	private Vector[] m_vertices;
	private Vector m_normal;
	
	public Simplex2d(Vector p1, Vector p2, Vector p3)
	{
		m_vertices = new Vector[] {p1, p2, p3};
		m_normal = p1.sub(p2).crossProduct(p1.sub(p3));
	}
	
	public Simplex2d(Vector[] vertices, Vector normal)
	{
		m_vertices = vertices;
		m_normal = normal;
	}

	public Simplex2d flipNormal() {
		return new Simplex2d(m_vertices, m_normal.mul(-1));
	}
	
	public void draw(ViewPoint view, Bitmap target)
	{
		
	}
	
	public boolean facingView(ViewPoint view)
	{
		return Math.abs(m_normal.angleTo(view.getDirection())) > Math.PI / 2f;
	}
	
	public Vector[] getVertices() { return m_vertices; }
	public Vector getNormal() { return m_normal; }
	
	public Simplex2d setVertices(Vector[] vertices) {
		return new Simplex2d(vertices, m_normal);
	}
	
}
