package com.base.engine.core;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

public class Input
{
	final static int NUM_KEYS = 338;
	final static int NUM_MOUSE = 8;
	
	static boolean[] currentKeys = new boolean[NUM_KEYS];
	static boolean[] currentMouse = new boolean[NUM_MOUSE];
	
	static long MainWindow;
	
	//private static GLFWKeyCallback keyCallback;
	
	private static DoubleBuffer mx;
	private static DoubleBuffer my;
	
	public static void init(long window)
	{
		MainWindow = window;
		
		mx = BufferUtils.createDoubleBuffer(1);
		my = BufferUtils.createDoubleBuffer(1);
		/*
		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
            }
        });
        */

	}
	
	public static void update()
	{
		for(int i = 0; i < NUM_KEYS; i++)
		{
			currentKeys[i] = glfwGetKey(MainWindow, i) == 1;
		}
		for(int i = 0; i < NUM_MOUSE; i++)
		{
			currentMouse[i] = glfwGetMouseButton(MainWindow, i) == 1;
		}
	}
	
	public static boolean getKey(int key)
	{
		return currentKeys[key];
	}
	
	public static boolean getMouse(int mouse)
	{
		return currentMouse[mouse];
	}
	
	
	public static Vector2f getMousePosition()
	{
		glfwGetCursorPos(MainWindow, mx, my);
		return new Vector2f((float)mx.get(0), (float)my.get(0));
	}
	
	public static void setMousePosition(Vector2f pos)
	{
		glfwSetCursorPos(MainWindow, (double)pos.getX(), (double)pos.getY());
	}
	
	public static void setCursor(boolean enabled)
	{
		if(enabled)
			glfwSetInputMode(MainWindow, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
		else
			glfwSetInputMode(MainWindow, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	}

}
