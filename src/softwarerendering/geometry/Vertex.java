package softwarerendering.geometry;

import softwarerendering.maths.Matrix;
import softwarerendering.maths.Vector;

public class Vertex {
	
	public Vector pos;
	
	public Vertex(Vector pos)
	{
		this.pos = pos;
	}
	
	public Vector getPosition() { return pos; }
	
	public float getY() { return pos.getY(); }
	public float getX() { return pos.getX(); }
	public float getZ() { return pos.getZ(); }
	
	public Vertex transform(Matrix transformation)
	{
		return new Vertex(transformation.transform(pos));
	}

}
