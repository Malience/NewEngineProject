 package com.base.engine.core;

public final class FrameLimiter
{
	private double frameRate;
	
	private double startTime;
	private double passedTime;
	private double lastTime;
	private double unprocessedTime;
	private double frameCounter;
	private double frameTime;
	
	private double frames;
	
	public FrameLimiter(double frameRate)
	{
		this.frameRate = frameRate;
		
		frameTime = 1.0/this.frameRate;
		frames = 0;
		frameCounter = 0;
		lastTime = Time.getTime();
		unprocessedTime = 0;
	}
	
	public void update()
	{
		startTime = Time.getTime();
		passedTime = startTime - lastTime;
		lastTime = Time.getTime();
		
		unprocessedTime += passedTime;
		frameCounter += passedTime;
	}
	
	public boolean process()
	{
		return unprocessedTime > frameTime;
	}
	
	public void iterateProcess()
	{
		unprocessedTime -= frameTime;
	}
	
	public void printFrames()
	{
		if(frameCounter >= 1.0)
		{
			System.out.println(frames);
			frames = 0;
			frameCounter = 0;
		}	
	}
	
	public void iterateFrames()
	{
		frames++;
	}
	
	public void sleep()
	{
		try
		{
			Thread.sleep(1);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void sleep(long millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public double getFrameTime()
	{
		return frameTime;
	}
	
	public float getFrameTimef()
	{
		return (float)frameTime;
	}
}