package softwarerendering;
import java.util.ArrayList;
import java.util.Random;

import softwarerendering.geometry.Cube;
import softwarerendering.maths.Vector;


public class Main {
	
	public static byte[] WHITE = new byte[] {
		(byte) 0xFF, (byte) 0xFF,
		(byte) 0xFF, (byte) 0xFF
	};
	public static byte[] RED = new byte[] {
		(byte) 0xFF, (byte) 0x00,
		(byte) 0x00, (byte) 0xFF
	};
	public static byte[] BLUE = new byte[] {
		(byte) 0x00, (byte) 0x00,
		(byte) 0xFF, (byte) 0xFF
	};
	public static byte[] GREEN = new byte[] {
		(byte) 0x00, (byte) 0xFF,
		(byte) 0x00, (byte) 0xFF
	};
	
	public static final Random r = new Random();
	
	public static class Particle {
		Cube cube;
		Vector v, r;
	}
	
	public static void randomiseParticle(ViewPoint view, Particle p, float z0)
	{
		float x = r.nextFloat() * view.viewRangeAt(z0) - z0/2f;
		float y = r.nextFloat() * view.viewRangeAt(z0) - z0/2f;
		p.cube = new Cube(x, y, z0, r.nextFloat() / 10f);
		int i = r.nextInt(4);
		switch(i) {
			case 0:  	p.cube.setColour(BLUE); 	break;
			case 1:  	p.cube.setColour(RED); 		break;
			case 2:  	p.cube.setColour(GREEN); 	break;
			default: 	p.cube.setColour(WHITE); 	break;
		}
		p.v = new Vector(0, 0, -r.nextFloat() - 1);
		p.r = new Vector(r.nextFloat(), r.nextFloat(), r.nextFloat()).mul(5).add(-2.5f);
	}
	
	public static void main(String[] args)
	{	
		Display display = new Display(1100, 620, "Software Rendering");
		RenderContext target = display.getFrameBuffer();
		
		Cube cube = new Cube(0f, 0f, 0.5f, .1f);
		cube.setRenderingOption(Cube.RenderingOption.WIREFRAME);
		
		final ViewPoint view = new ViewPoint();
		
		ArrayList<Particle> list = new ArrayList<Particle>();
		int n = 500;
		for (int i = 0; i < n; i++) {
			Particle p = new Particle();
			float z0 = r.nextFloat()*10 + 0.1f;
			randomiseParticle(view, p, z0);
			list.add(p);
		}
		
		while (true) 
		{
			float delta = display.getDelta();
			target.clear((byte) 0x15);
			
			cube.rotate(Vector.zAxis, delta * 5);
			cube.rotate(Vector.yAxis, delta * 3f);
			cube.rotate(Vector.xAxis, delta * 5f);
			
			for (Particle p : list) {
				p.cube.draw(target, view);
				p.cube.setPosition(p.cube.getPosition().add(p.v.mul(delta)));
				if (p.cube.getPosition().getZ() < 0.1 || !view.isInView(p.cube.getPosition()))
					randomiseParticle(view, p, 10f);
				p.cube.rotate(Vector.xAxis, delta*p.r.getX());
				p.cube.rotate(Vector.yAxis, delta*p.r.getY());
				p.cube.rotate(Vector.zAxis, delta*p.r.getZ());
			}
			
//			cube.draw(target, view);
			
			display.swapBuffers();
		}
	}
	
}
