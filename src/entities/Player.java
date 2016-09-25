package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;

public class Player extends Entity
{
	private static final float WALK_SPEED = 20;
	private static final float RUN_SPEED = 40;
	private static final float TURN_SPEED = 160;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	
	public Player(TexturedModel model, Vector3f psoition, float rotX, float rotY, float rotZ, float scale) 
	{
		super(model, psoition, rotX, rotY, rotZ, scale);

	}

	public void move()
	{
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
	}
	
	private void checkInputs()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			this.currentSpeed = RUN_SPEED;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			this.currentSpeed = -RUN_SPEED;
		}

		currentSpeed = 0;
			
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			this.currentTurnSpeed = TURN_SPEED;
		}
			
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			this.currentTurnSpeed = -TURN_SPEED;
		}
		
		currentTurnSpeed = 0;
	}
}
