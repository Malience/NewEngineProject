package com.base.engine.components;

import com.base.engine.components.PointLight;
import com.base.engine.core.Vector;
import com.base.engine.rendering.Attenuation;
import com.base.engine.rendering.Shader;

public class SpotLight extends PointLight
{
	private float cutoff;
	
	public SpotLight(Vector color, float intensity, Attenuation attenuation, float cutoff)
	{
		super(color, intensity, attenuation);
		this.cutoff = cutoff;

		setShader(new Shader("forward-spot"));
	}
	
	public Vector getDirection()
	{
		return getTransform().getTransformedRot().getForward();
	}

	public float getCutoff()
	{
		return cutoff;
	}
	public void setCutoff(float cutoff)
	{
		this.cutoff = cutoff;
	}
}
