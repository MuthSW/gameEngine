package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Entity 
{
	private TexturedModel model;
	private Vector3f psoition;
	private float rotX, rotY, rotZ;
	private float scale;
	
	private int textureIndex = 0;
	
	public Entity(TexturedModel model, Vector3f psoition, float rotX, float rotY, float rotZ, float scale) 
	{
		super();
		this.model = model;
		this.psoition = psoition;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}

	public Entity(TexturedModel model, int index, Vector3f psoition, float rotX, float rotY, float rotZ, float scale) 
	{
		super();
		this.textureIndex = index;
		this.model = model;
		this.psoition = psoition;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public float getTextureXOffset()
	{
		int column = textureIndex % model.getTexture().getNuberOfRows();
		return (float) column / (float) model.getTexture().getNuberOfRows();
	}
	
	public float getTextureYOffset()
	{
		int row = textureIndex / model.getTexture().getNuberOfRows();
		return (float) row / (float) model.getTexture().getNuberOfRows();
	}
	
	public void increasePosition(float dx, float dy, float dz)
	{
		this.psoition.x += dx;
		this.psoition.y += dy;
		this.psoition.z += dz;
	}
	
	public void increaseRotation(float dx, float dy, float dz)
	{
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}
	
	public void increaseScale(float dscale)
	{
		this.scale += dscale;
	}
	
	public TexturedModel getModel() 
	{
		return model;
	}

	public void setModel(TexturedModel model) 
	{
		this.model = model;
	}

	public Vector3f getPsoition() 
	{
		return psoition;
	}

	public void setPsoition(Vector3f psoition) 
	{
		this.psoition = psoition;
	}

	public float getRotX() 
	{
		return rotX;
	}

	public void setRotX(float rotX) 
	{
		this.rotX = rotX;
	}

	public float getRotY() 
	{
		return rotY;
	}

	public void setRotY(float rotY) 
	{
		this.rotY = rotY;
	}

	public float getRotZ() 
	{
		return rotZ;
	}

	public void setRotZ(float rotZ) 
	{
		this.rotZ = rotZ;
	}

	public float getScale() 
	{
		return scale;
	}

	public void setScale(float scale) 
	{
		this.scale = scale;
	}
	
}
