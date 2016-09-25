package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop 
{
	public static void main(String[] args)
	{		 
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer();
		
//		TexturedModel dragon = new TexturedModel(OBJLoader.LoadObjModel("dragon", loader), new ModelTexture(loader.loadTexture("transptest")));
//		ModelTexture dragonTex = dragon.getTexture();
//		dragonTex.setShineDamper(10);
//		dragonTex.setReflectivity(0);
//		Entity entity = new Entity(dragon, new Vector3f(0, 0, -20), 0, 0, 0, 1);

		TexturedModel stallModel = new TexturedModel(OBJLoader.LoadObjModel("stall", loader), new ModelTexture(loader.loadTexture("stallTexture")));
		Entity stall = new Entity(stallModel, new Vector3f(0,0,-50),0,0,0, 2f);
		TexturedModel grassModel = new TexturedModel(OBJLoader.LoadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
		grassModel.getTexture().setHasTransparency(true);
		grassModel.getTexture().setUseFakeLighting(true);
		TexturedModel fernModel = new TexturedModel(OBJLoader.LoadObjModel("fern", loader), new ModelTexture(loader.loadTexture("fern")));
		fernModel.getTexture().setHasTransparency(true);
		fernModel.getTexture().setUseFakeLighting(true);
		TexturedModel treeModel = new TexturedModel(OBJLoader.LoadObjModel("tree", loader), new ModelTexture(loader.loadTexture("tree")));
		TexturedModel person = new TexturedModel(OBJLoader.LoadObjModel("person", loader), new ModelTexture(loader.loadTexture("playerTexture")));
		
		Light light = new Light(new Vector3f(3000,2000,2000), new Vector3f(1f, 1f, 0.9f));
		
		//**********TERRAIN TEXTURES*************
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		Terrain terrain = new Terrain(-1,-1,loader, texturePack, blendMap);
		Terrain terrain2 = new Terrain(0,-1,loader, texturePack, blendMap);
		
		//****************************************
		
		Player player = new Player(person, new Vector3f(100, 0, -150), 0, 180, 0, 0.7f);
		Camera camera = new Camera(player);
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		
		for(int i = 0; i < 1000; i++)
		{
			entities.add(new Entity(grassModel, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * 800 - 800), 0, 0, 0, 2f));
			entities.add(new Entity(grassModel, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * 800 - 800), 0, 0, 0, 2f));
			entities.add(new Entity(fernModel, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * 800 - 800), 0, 0, 0, 1f));
			entities.add(new Entity(treeModel, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * 800 - 800), 0, 0, 0, 5f));
		}
		
		while(!Display.isCloseRequested())
		{
			player.move();
			camera.move();
			
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			renderer.processEntity(stall);
			for(Entity entity:entities)
			{
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
