package softwarerendering.maths;

public class Quaternion 
{
	private float x, y, z, w;
	
	public Quaternion(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Quaternion(Vector axis, float theta) {
		Vector n = axis.normalised();
		float cos = (float) Math.cos(theta / 2f);
		float sin = (float) Math.sin(theta / 2f);
		x = n.getX() * sin;
		y = n.getY() * sin;
		z = n.getZ() * sin;
		w = cos;
	}
	
	public float len() {
		return (float) Math.sqrt(x*x + y*y + z*z + w*w);
	}
	
	public Vector vector() {
		return new Vector(x, y, z);
	}
	
	public Quaternion getNormalized() {
		return new Quaternion(
				x / len(), y / len(), z / len(), w / len());
	}
	
	public Quaternion getConjugate() {
		return new Quaternion(
				-x, -y, -z, w);
	}
	
	public Quaternion mul(Quaternion b) {
		
		float w_ = w * b.getW() - x * b.getX() - y * b.getY() - z * b.getZ();
		float x_ = x * b.getW() + w * b.getX() + y * b.getZ() - z * b.getY();
		float y_ = y * b.getW() + w * b.getY() + z * b.getX() - x * b.getZ();
		float z_ = z * b.getW() + w * b.getZ() + x * b.getY() - y * b.getX();
		
		return new Quaternion(x_, y_, z_, w_);
	}
	
	public Quaternion mul(Vector b) {
		
		float w_ = -x * b.getX() - y * b.getY() - z * b.getZ();
		float x_ =  w * b.getX() + y * b.getZ() - z * b.getY();
		float y_ =  w * b.getY() + z * b.getX() - x * b.getZ();
		float z_ =  w * b.getZ() + x * b.getY() - y * b.getX();
		
		return new Quaternion(x_, y_, z_, w_);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}
}
