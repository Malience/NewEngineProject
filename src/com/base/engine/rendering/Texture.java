package com.base.engine.rendering;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.base.engine.core.Util;
import com.base.engine.rendering.MeshLoading.ResourceManagement.TextureResource;



public class Texture
{
	private static HashMap<String, TextureResource> loadedTextures = new HashMap<String, TextureResource>();
	private TextureResource resource;
	private String fileName;
	
	public Texture(String fileName)
	{
		this.fileName = fileName;
		TextureResource oldResource = loadedTextures.get(fileName);
		
		if(oldResource != null)
		{
			resource = oldResource;
			resource.addReference();
		}
		else
		{
			resource = loadTexture(fileName);
			loadedTextures.put(fileName, resource);
		}
	}
	
	@Override
	protected void finalize()
	{
		if(resource.removeReference() && !fileName.isEmpty())
			loadedTextures.remove(fileName);
	}
	
	public void bind()
	{
		bind(0);
	}
	
	public void bind(int samplerSlot)
	{
		assert(samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, resource.getId());
	}
	
	public int getID()
	{
		return resource.getId();
	}
	
	
	public static TextureResource loadTexture(String fileName)
	{
		try
		{
		
			BufferedImage image = ImageIO.read(new File("./res/textures/" + fileName));
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			
	
	        ByteBuffer buffer = Util.createByteBuffer(image.getWidth() * image.getHeight() * 4);
	        boolean hasAlpha = image.getColorModel().hasAlpha();
	
	        for(int y = 0; y < image.getHeight(); y++)
	        {
	            for(int x = 0; x < image.getWidth(); x++)
	            {
	                int pixel = pixels[y * image.getWidth() + x];
	                
	                buffer.put((byte) ((pixel >> 16) & 0xFF));
	                buffer.put((byte) ((pixel >> 8) & 0xFF));
	                buffer.put((byte) ((pixel) & 0xFF));
	                if(hasAlpha)
	                	buffer.put((byte) ((pixel >> 24) & 0xFF));
	                else
	                	buffer.put((byte)(0xFF));
	            }
	        }
	
	        buffer.flip();
	
	
	        TextureResource textureResource = new TextureResource(image.getWidth(), image.getHeight()); //Generate texture ID
	        
	        glBindTexture(GL_TEXTURE_2D, textureResource.getId()); //Bind texture ID
	
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	
	        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
	
	         
	        return textureResource;
		}
        catch(Exception e)
        {
        	e.printStackTrace();
        	System.exit(1);
        }
		
		return null;
	}
	
//	public static int renderToTexture()
//	{
//		int framebuffer = glGenFramebuffers();
//		glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
//		
//		TextureResource textureResource = new TextureResource(); //Generate texture ID
//        
//        glBindTexture(GL_TEXTURE_2D, textureResource.getId()); //Bind texture ID
//
//
//        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, (int)Window.getWidth(), (int)Window.getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
//        
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
//        
//        int depthrenderbuffer = glGenRenderbuffers();
//        glBindRenderbuffer(GL_RENDERBUFFER, depthrenderbuffer);
//        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, (int)Window.getWidth(), (int)Window.getHeight());
//        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthrenderbuffer);
//        
//        glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, textureResource.getId(), 0);
//        
//        glDrawBuffers(GL_COLOR_ATTACHMENT0);
//        
//       
//        return framebuffer;
//	}
}