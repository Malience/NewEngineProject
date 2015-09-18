package com.base.engine.components;

import com.base.engine.core.CoreEngine;
import com.base.engine.core.Vector;
import com.base.engine.rendering.Shader;

public class BaseLight extends GameComponent
{
	private Vector color;
	private float intensity;
	private Shader shader;
	
	public BaseLight(Vector color, float intensity)
	{
		this.color = color;
		this.intensity = intensity;
	}

	@Override
	public void addToEngine(CoreEngine engine)
	{
		engine.getRenderingEngine().addLight(this);
	}

	public void setShader(Shader shader)
	{
		this.shader = shader;
	}

	public Shader getShader()
	{
		return shader;
	}

	public Vector getColor()
	{
		return color;
	}

	public void setColor(Vector color)
	{
		this.color = color;
	}

	public float getIntensity()
	{
		return intensity;
	}

	public void setIntensity(float intensity)
	{
		this.intensity = intensity;
	}
}
