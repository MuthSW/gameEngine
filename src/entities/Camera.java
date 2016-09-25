package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera 
{
	private static final float SPEED_SLOW = 0.4f;
	private static final float SPEED_FAST = 0.8f;
	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch;
	private float yaw;
	private float roll;
	
	public Camera()
	{
		
	}
	
	public void move()
	{

	}

	public Vector3f getPosition() 
	{
		return position;
	}

	public float getPitch() 
	{
		return pitch;
	}

	public float getYaw() 
	{
		return yaw;
	}

	public float getRoll() 
	{
		return roll;
	}
	
	
}
