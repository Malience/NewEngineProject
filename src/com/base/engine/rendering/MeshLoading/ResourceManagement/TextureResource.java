package com.base.engine.rendering.MeshLoading.ResourceManagement;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

import com.base.engine.core.ReferenceCounter;

public class TextureResource extends ReferenceCounter
{
	private int textureID;
	private int width;
	private int height;
	private int framebuffer;
	private int depthbuffer;
	
	public TextureResource(int width, int height)
	{
		super();
		textureID = glGenTextures();
		this.width = width;
		this.height = height;
		
		
		
		framebuffer = glGenFramebuffers();
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, framebuffer);
		
		glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureID, 0);
		
		depthbuffer = glGenRenderbuffers();
		
		glBindRenderbuffer(GL_RENDERBUFFER, depthbuffer);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT24, width, height);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthbuffer);
		
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		
	}
	
	@Override
	protected void finalize()
	{
		glDeleteTextures(textureID);
		glDeleteBuffers(textureID);
	}
	
	public int getId() {
		return textureID;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getFramebuffer() {
		return framebuffer;
	}

	public int getDepthbuffer() {
		return depthbuffer;
	}

	
	
}
