package com.base.engine.components;

import com.base.engine.core.Vector;
import com.base.engine.rendering.Shader;

public class DirectionalLight extends BaseLight
{
	public DirectionalLight(Vector color, float intensity)
	{
		super(color, intensity);

		setShader(new Shader("forward-directional"));
	}

	public Vector getDirection()
	{
		return getTransform().getTransformedRot().getForward();
	}
}
