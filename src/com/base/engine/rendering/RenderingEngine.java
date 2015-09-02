package com.base.engine.rendering;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import com.base.engine.core.Color;
import com.base.engine.core.Engine;

public class RenderingEngine extends Engine
{
	ArrayList<Window> windows;
	public RenderingEngine()
	{
		super();
	}

	@Override
	protected void start()
	{
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
}
