package softwarerendering.geometry;

import softwarerendering.RenderContext;
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
	
	public void setNormal(Vector normal)
	{
		m_normal = normal;
	}
	
	public void draw(ViewPoint view, RenderContext target, byte[] colour)
	{
		Vector[] points = new Vector[3];
		for (int i = 0; i < m_vertices.length; i++) {
			int[] coords = view.getViewCoords(m_vertices[i], target);
			points[i] = new Vector(coords[0], coords[1]);
		}
		target.fillTriangle(points[0], points[1], points[2], colour);
	}
	
	public Simplex2d rotateOnAxisAroundOrigin(Vector axis, float theta)
	{
		return rotateOnAxisAroundPoint(axis, Vector.origin, theta);
	}
	
	public Simplex2d rotateOnAxisAroundPoint(Vector axis, Vector point, float theta)
	{
		Vector[] newVertices = new Vector[m_vertices.length];
		for (int i = 0; i < m_vertices.length; i++)
			newVertices[i] = m_vertices[i].sub(point).rotate(axis, theta).add(point);
		return new Simplex2d(newVertices, m_normal);
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
	
	public Simplex2d translate(Vector dv) {
		Vector[] newVertices = new Vector[m_vertices.length];
		for (int i = 0; i < m_vertices.length; i++)
			newVertices[i] = m_vertices[i].add(dv);
		return new Simplex2d(newVertices, m_normal);
	}
	
}
