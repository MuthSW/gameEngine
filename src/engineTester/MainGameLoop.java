package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
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
		
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNuberOfRows(2);
		TexturedModel fernModel = new TexturedModel(OBJLoader.LoadObjModel("fern", loader), fernTextureAtlas);
		fernModel.getTexture().setHasTransparency(true);
//		fernModel.getTexture().setUseFakeLighting(true);
		
		TexturedModel treeModel = new TexturedModel(OBJLoader.LoadObjModel("pine", loader), new ModelTexture(loader.loadTexture("pine")));
		TexturedModel person = new TexturedModel(OBJLoader.LoadObjModel("person", loader), new ModelTexture(loader.loadTexture("playerTexture")));
		TexturedModel lowPolyTreeModel = new TexturedModel(OBJLoader.LoadObjModel("lowPolyTree", loader), new ModelTexture(loader.loadTexture("lowPolyTree")));
		
		//**********TERRAIN TEXTURES*************
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		Terrain terrain = new Terrain(-1,-1,loader, texturePack, blendMap, "heightmap");
		
		//-----------TERRAIN TEXTURES--------------
		Player player = new Player(person, new Vector3f(-100, 0, 0), 0, 180, 0, 0.7f);
		Camera camera = new Camera(player);
		List<Light> lights = new ArrayList<Light>();
		lights.add(new Light(new Vector3f(0, 10000, -7000), new Vector3f(0.4f, 0.4f, 0.3f)));		
		lights.add(new Light(new Vector3f(-100, 0, 0), new Vector3f(2f, 0f, 0f), new Vector3f(1, 0.01f, 0.002f)));		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		GuiTexture gui = new GuiTexture(loader.loadTexture("health"), new Vector2f(-0.75f, -0.75f), new Vector2f(0.25f, 0.3f));
		guis.add(gui);
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random(676452);
		
		for(int i = 0; i < 400; i++)
		{
			if(i % 20 == 0)
			{
				float x = random.nextFloat() * 800 - 800;
				float z = random.nextFloat() * -600;
				float y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(grassModel, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 2f));
			}
			
			if(i % 5 == 0)
			{
				float x = random.nextFloat() * 800 - 800;
				float z = random.nextFloat() * -600;
				float y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(treeModel, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, random.nextFloat() * 0.25f + 1f));
				x = random.nextFloat() * 800 - 800;
				z = random.nextFloat() * -600;
				y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(lowPolyTreeModel, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, random.nextFloat() * 0.1f + 0.6f));
			}
		}
		
		for(int i = 0; i < 1000; i++)
		{
			float x = random.nextFloat() * 800 - 800;
			float z = random.nextFloat() * -600;
			float y = terrain.getHeightOfTerrain(x, z);
			entities.add(new Entity(fernModel, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 0.9f));

		}
//***************MAIN_GAME_LOOP******************************
		while(!Display.isCloseRequested())
		{
			player.move(terrain);
			camera.move();
			
			renderer.processTerrain(terrain);
			renderer.processEntity(stall);
			for(Entity entity:entities)
			{
				renderer.processEntity(entity);
			}
			renderer.processEntity(player);
			renderer.render(lights, camera);
			guiRenderer.render(guis);
			DisplayManager.updateDisplay();
		}
//-----------------MAIN_GAME_LOOP-------------------------
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
