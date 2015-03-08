package softwarerendering;
import java.awt.event.KeyEvent;

import softwarerendering.geometry.Cube;
import softwarerendering.geometry.Simplex2d;
import softwarerendering.maths.Vector;


public class Main {
	
	public static byte[] WHITE = new byte[] {
		(byte) 0xFF, (byte) 0xFF,
		(byte) 0xFF, (byte) 0xFF
	};
	
	public static void main(String[] args){
		
		Display display = new Display(800, 600, "Software Rendering");
		RenderContext target = display.getFrameBuffer();
		//Grid3D stars = new Grid3D(20000, 60f);
		Cube cube = new Cube(0, -.2f, .5f, .1f);
//		Cube cube1 = new Cube(0, 0, .5f, .15f);
//		Cube cube2 = new Cube(0.3f, -.4f, .1f, .05f);
		
		final ViewPoint view = new ViewPoint();
		
//		float elapsedTime = 0;
		long previousTime = System.nanoTime();
		
		Vector v0 = new Vector(0, 0.1f, 0.5f);
		Vector v1 = new Vector(0.15f, 0, 0.5f);
		Vector v2 = new Vector(-.15f, 0, 0.5f);
		Simplex2d simplex = new Simplex2d(v0, v1, v2);
		
		while (true) {
			target.clear((byte) 0x15);

			long currentTime = System.nanoTime();
			float delta = (float) ((currentTime - previousTime) / 1000000000.0);
//			elapsedTime += delta;
			previousTime = currentTime;
			
			cube.rotate(Vector.xAxis, delta);
			cube.rotate(Vector.yAxis, delta);
			cube.rotate(Vector.zAxis, delta);
			
			simplex = simplex.rotateOnAxisAroundPoint(Vector.yAxis, new Vector(0, 0, 0.45f), delta);
			simplex.draw(view, target, Display.RED);
			
//			cube1.rotate(Vector.xAxis, delta);
//			float sin = (float) Math.sin(Math.toRadians(elapsedTime * 50));
//			float size = 0.15f + 0.00015f * sin;
//			cube1.setSize(size);
			
//			target.fillTriangle(v0, v1, v2);
			
			cube.draw(target, view);
//			cube1.update(delta, target, view);
//			cube2.update(delta, target, view);
			
			float speed = 30f;
			
			if(display.getInput().getKey(KeyEvent.VK_UP)){
				view.rotateView(0, delta * speed);
			}
			else if(display.getInput().getKey(KeyEvent.VK_DOWN)){
				view.rotateView(0, - delta * speed);
			}
			else if(display.getInput().getKey(KeyEvent.VK_LEFT)){
				view.rotateView(delta * speed, 0);
			}
			else if(display.getInput().getKey(KeyEvent.VK_RIGHT)){
				view.rotateView(- delta * speed, 0);
			}
			
			speed = .5f;
			
			if(display.getInput().getKey(KeyEvent.VK_W)) {
				view.move(0, 0, delta * speed);
			}
			else if(display.getInput().getKey(KeyEvent.VK_S)) {
				view.move(0, 0, - delta * speed);
			}
			if(display.getInput().getKey(KeyEvent.VK_A)) {
				view.move(- delta * speed, 0, 0);
			}
			else if(display.getInput().getKey(KeyEvent.VK_D)) {
				view.move(delta * speed, 0, 0);
			}
			else if(display.getInput().getKey(KeyEvent.VK_SPACE)) {
				view.move(0, - delta * speed, 0);
			}
			else if(display.getInput().getKey(KeyEvent.VK_SHIFT)) {
				view.move(0, delta * speed, 0);
			}
			
			else if(display.getInput().getKey(KeyEvent.VK_R)) {
				view.setPosition(0, 0, 0);
				view.setView(0, 0);
			}
			
			
//			
//			cube.setPosition(.5f * sin, .5f * cos, .75f + .3f * (float)Math.pow(sin, 2));
			
//			float offsetX = (float)(70*Math.sin(Math.toRadians(System.currentTimeMillis()/10)));
//			float offsetY = 1000 + (float)(70*Math.cos(Math.toRadians(System.currentTimeMillis()/10)));
			
			//stars.updateAndRender(target, delta, view);
			
			display.swapBuffers();
		}
	}
	
}
