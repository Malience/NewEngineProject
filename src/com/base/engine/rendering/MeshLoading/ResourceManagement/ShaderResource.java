package com.base.engine.rendering.MeshLoading.ResourceManagement;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;
import java.util.HashMap;

import com.base.engine.core.ReferenceCounter;

public class ShaderResource extends ReferenceCounter
{
	private int program;
	private HashMap<String, Integer> uniforms;
	private ArrayList<String> uniformNames;
	private ArrayList<String> uniformTypes;
	
	public ShaderResource()
	{
		super();
		this.program = glCreateProgram();
		
		if(program == 0)
		{
			System.err.println("Shader creation failed: Could not find valid memory location in constructor");
			System.exit(1);
		}
		
		uniforms = new HashMap<String, Integer>();
		uniformNames = new ArrayList<String>();
		uniformTypes = new ArrayList<String>();
	}
	
	@Override
	protected void finalize()
	{
		glDeleteBuffers(program);
	}

	public int getProgram() {
		return program;
	}

	public HashMap<String, Integer> getUniforms() {
		return uniforms;
	}

	public ArrayList<String> getUniformNames() {
		return uniformNames;
	}

	public ArrayList<String> getUniformTypes() {
		return uniformTypes;
	}
}
