package com.base.engine.rendering;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.HashMap;

import com.base.engine.components.BaseLight;
import com.base.engine.components.Camera;
import com.base.engine.core.Color;
import com.base.engine.core.Engine;
import com.base.engine.core.Transform;
import com.base.engine.core.Vector;
import com.base.engine.core.Vector3f;

public class RenderingEngine extends Engine
{
	ArrayList<Window> windows;
	HashMap<String, Integer> samplerMap;
	private BaseLight activeLight;
	
	public RenderingEngine()
	{
		super();
	}

	@Override
	protected void start()
	{
		vectorHashMap = new HashMap<String, Vector>();
		floatHashMap = new HashMap<String, Float>();
		
		samplerMap = new HashMap<String, Integer>();
		samplerMap.put("diffuse", 0);
		
		windows = new ArrayList<Window>();
		
		glfwInit();
		
		isRunning = true;
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		
		Window MainWindow = new Window(800, 600, "Main Window", new Color(0, 255, 0));
		disposables.add(MainWindow);
		windows.add(MainWindow);
		new Window(600, 300, "Sub Window", new Color(255,0,255), MainWindow);
		
		MainWindow.showWindow();
		MainWindow.showChildren();
	}

	@Override
	protected void run() 
	{
		if(isRunning)
		{
			for(Window window : windows)
			{
				if(window.isCloseRequested())
				{
					window.dispose();
					windows.remove(window);
					if(windows.isEmpty())
						stop();
				}
				else
				{
					window.clearScreen();
					window.render();
				}
			}
		}
	}

	public int getSamplerSlot(String samplerName)
	{
		return samplerMap.get(samplerName);
	}
	
	public void addLight(BaseLight baseLight) 
	{
		//TODO Add lighting
	}

	public void addCamera(Camera camera) 
	{
		// TODO Add Camera
		
	}

	public Camera getCamera(int i) {
		return null;
	}
	
	public BaseLight getActiveLight()
	{
		return activeLight;
	}
	
	HashMap<String, Vector> vectorHashMap;
	HashMap<String, Float> floatHashMap;
	/////////////////////////
	public void addVector(String name, Vector vector) { vectorHashMap.put(name, vector); }
	public void addFloat(String name, float floatValue) { floatHashMap.put(name, floatValue); }
	
	public Vector getVector(String name)
	{
		Vector result = vectorHashMap.get(name);
		if(result != null)
			return result;

		return new Vector(0,0,0);
	}

	public float getFloat(String name)
	{
		Float result = floatHashMap.get(name);
		if(result != null)
			return result;

		return 0;
	}
	/////////////////////////
	
	public void updateUniformStruct(Transform transform, Material material, RenderingEngine renderingEngine, String uniformName, String uniformType)
	{
		throw new IllegalArgumentException(uniformName + " is not a supported type in Rendering Engine");
	}
}
