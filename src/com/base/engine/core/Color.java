package com.base.engine.core;

public class Color 
{
	private float red;
	private float green;
	private float blue;
	private float alpha;
	
	public Color(float red, float green, float blue)
	{
		this(red, green, blue, 0);
	}
	
	public Color(float red, float green, float blue, float alpha)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	public float getRed() 
	{
		return red;
	}

	public float getGreen() 
	{
		return green;
	}

	public float getBlue() 
	{
		return blue;
	}

	public float getAlpha() 
	{
		return alpha;
	}
}
