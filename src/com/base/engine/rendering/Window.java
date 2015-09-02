package com.base.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;
//import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GLContext;

import com.base.engine.core.Color;
import com.base.engine.core.Disposable;
import com.base.engine.core.Priority;
import com.base.engine.core.Vector2f;

public class Window implements Disposable, Priority
{
	private long windowID;
	private float width;
	private float height;
	private int priority;
	private Color clearColor;
	private Window parent;
	private ArrayList<Window> children;
	private GLContext context;
	
	public Window(int width, int height, String title, float red, float green, float blue)
	{
		this(width, height, title, new Color(red, green, blue));
	}
	
	public Window(int width, int height, String title, float red, float green, float blue, float alpha)
	{
		this(width, height, title, new Color(red, green, blue, alpha));
	}
	
	public Window(int width, int height, String title, Color clearColor)
	{
		this(width, height, title, clearColor, null);
	}
	
	public Window(int width, int height, String title, Color clearColor, Window parent)
	{		
		if(parent == null)
		{
			windowID = glfwCreateWindow(width, height, title, NULL, NULL);
			priority = 1;
		}
		
		else
		{
			windowID = glfwCreateWindow(width, height, title, NULL, parent.getID());
			priority = 2;
			this.parent = parent;
			parent.add(this);
		}
		
		
		children = new ArrayList<Window>();
		generateSize();
		this.clearColor = clearColor;
		
		glfwMakeContextCurrent(windowID);
		glfwSwapInterval(1);	
		
		context = GLContext.createFromCurrent();
		
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);

		glEnable(GL_DEPTH_CLAMP);

		glEnable(GL_TEXTURE_2D);
	}
	
//	public static void bindAsRenderTarget()
//	{
//		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
//		glViewport(0, 0, (int)getWidth(), (int)getHeight());
//	}
//	
//	public static void bindRenderTarget(int render)
//	{
//		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, render);
//		glViewport(0, 0, (int)getWidth(), (int)getHeight());
//	}
//	
	public void showWindow()
	{
		glfwShowWindow(windowID);
	}
	
	public static void showWindow(long window)
	{
		glfwShowWindow(window);
	}
	
	public void showChildren()
	{
		if(!children.isEmpty())
			for(Window child : children)
				glfwShowWindow(child.getID());
	}
	
	public void showChild(int i)
	{
		if(!children.isEmpty())
			glfwShowWindow(children.get(i).getID());
	}
	
	public void showChild(long windowID)
	{
		glfwShowWindow(getChild(windowID).getID());
	}
	
	public boolean isCloseRequested()
	{
		return glfwWindowShouldClose(windowID) == GL_TRUE;
	}
	
	public long getID()
	{
		return windowID;
	}
	
	public float getWidth()
	{
		return width;
	}
	
	public float getHeight()
	{
		return height;
	}
	
	public Window getParent()
	{
		return parent;
	}
	
	public Window getChild(int i)
	{
		return children.get(i);
	}
	
	public Window getChild(long windowID)
	{
		for(Window child : children)
			if(child.getID() == windowID)
				return child;
		return null;
	}
	
	public void add(Window window)
	{
		children.add(window);
	}
	
	public Window remove(int i)
	{
		return children.remove(i);
	}
	
	public boolean remove(Window window)
	{
		return children.remove(window);
	}
	
	public static void render(long windowID)
	{
		glfwSwapBuffers(windowID);
	}
	
	public void render()
	{
		if(!children.isEmpty())
			for(Window child : children)
				child.render();
		glfwSwapBuffers(windowID);
	}
	
//	public static void renderAll()
//	{
//		
//		for(int i = 0; i < WindowList.length; i++)
//		{
//			render(WindowList[i]);
//		}
//	}
	
	public void clearScreen()
	{
		if(!children.isEmpty())
			for(Window child : children)
				child.clearScreen();
		glfwMakeContextCurrent(windowID);
		glClearColor(clearColor.getRed(), clearColor.getGreen(), clearColor.getBlue(), clearColor.getAlpha());
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	
	public static void dispose(long windowID)
	{
		glfwDestroyWindow(windowID);
	}
	
	public void dispose()
	{
		if(!children.isEmpty())
			for(Window child : children)
				child.dispose();
		glfwDestroyWindow(windowID);
	}
	
	private void generateSize()
	{
		ByteBuffer w = BufferUtils.createByteBuffer(4);
		ByteBuffer h = BufferUtils.createByteBuffer(4);
		glfwGetWindowSize(windowID, w, h);
		width = (float)w.getInt(0);
		height = (float)h.getInt(0);
	}
	
//	public static Vector2f getCenter(long windowID)
//	{
//		return new Vector2f(width/2, height/2);
//	}
//	
	public Vector2f getCenter()
	{
		return new Vector2f(width/2, height/2);
	}

	@Override
	public int getPriority() {
		return priority;
	}
}
