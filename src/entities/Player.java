package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity
{
	private static final float WALK_SPEED = 15;
	private static final float RUN_SPEED = 30;
	private static final float TURN_SPEED = 100;
	private static final float JUMP_POWER = 30;
	private static final float JUMP_DELAY = 0.1f;
	
	private static final float GRAVITY = -50;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	private float timeToNextJump;
	
	private boolean isInAir = false;
	
	public Player(TexturedModel model, Vector3f psoition, float rotX, float rotY, float rotZ, float scale) 
	{
		super(model, psoition, rotX, rotY, rotZ, scale);
	}

	public void move(Terrain terrain)
	{
		currentSpeed = 0;
		currentTurnSpeed = 0;
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSconds();
		super.increasePosition(dx, upwardsSpeed * DisplayManager.getFrameTimeSconds(), dz);
		float terrainHeight = terrain.getHeightOfTerrain(super.getPsoition().x, super.getPsoition().z);
		if(super.getPsoition().y < terrainHeight)
		{
			upwardsSpeed = 0;
			timeToNextJump -= DisplayManager.getFrameTimeSconds();
			if(timeToNextJump < 0)
			{
				timeToNextJump = 0;
			}
			isInAir = false; 
			super.getPsoition().y = terrainHeight;
		}
	}
	
	private void jump()
	{
		if(!isInAir && timeToNextJump == 0)
		{
			upwardsSpeed = JUMP_POWER;
			timeToNextJump = JUMP_DELAY;
			isInAir = true;
		}
	}
	
	private void checkInputs()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			if(Keyboard.isKeyDown(Keyboard.KEY_W))
			{
				this.currentSpeed += RUN_SPEED;
			}
		
			if(Keyboard.isKeyDown(Keyboard.KEY_S))
			{
				this.currentSpeed -= RUN_SPEED;
			}
		
			if(Keyboard.isKeyDown(Keyboard.KEY_A))
			{
				this.currentTurnSpeed += TURN_SPEED;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_D))
			{
				this.currentTurnSpeed -= TURN_SPEED;
			}
			
		}
		else
		{
			if(Keyboard.isKeyDown(Keyboard.KEY_W))
			{
				this.currentSpeed += WALK_SPEED;
			}
		
			if(Keyboard.isKeyDown(Keyboard.KEY_S))
			{
				this.currentSpeed -= WALK_SPEED;
			}
		
			if(Keyboard.isKeyDown(Keyboard.KEY_A))
			{
				this.currentTurnSpeed += TURN_SPEED;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_D))
			{
				this.currentTurnSpeed -= TURN_SPEED;
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			jump();
		}
	}
}
