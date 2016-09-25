package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera 
{
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	private float pitch;
	
	private static final float OFFSET_Y = 6.5f;
	private Vector3f position = new Vector3f(0, 0, 0);
	private float yaw;
	private float roll;
	
	private Player player;
	
	public Camera(Player player)
	{
		this.player = player;
	}
	
	public void move()
	{
		claculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizontalDance = calculateHorizontalDistance();
		float verticalDance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDance, verticalDance);
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}

	public void setPosition(Vector3f position) 
	{
		this.position = position;
	}

	public void setPitch(float pitch) 
	{
		this.pitch = pitch;
	}

	public void setYaw(float yaw) 
	{
		this.yaw = yaw;
	}

	public void setRoll(float roll) 
	{
		this.roll = roll;
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
	
	public void calculateCameraPosition(float horizDistance, float verticDistance)
	{
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPsoition().x - offsetX;
		position.y = player.getPsoition().y + verticDistance + OFFSET_Y;
		position.z = player.getPsoition().z - offsetZ;
	}
	private float calculateHorizontalDistance()
	{
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance()
	{
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	
	private void claculateZoom()
	{
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		distanceFromPlayer -= zoomLevel;
		if(distanceFromPlayer > 100)
		{
			distanceFromPlayer = 100;
		}
		else if(distanceFromPlayer < 20)
		{
			distanceFromPlayer = 20;
		}
	}
	
	private void calculatePitch()
	{
		if(Mouse.isButtonDown(2))
		{
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
			if(pitch > 90)
			{
				pitch = 90;
			}
			else if(pitch < 1)
			{
				pitch = 1;
			}
		}
	}
	
	private void calculateAngleAroundPlayer()
	{
		if(Mouse.isButtonDown(2))
		{
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
}
