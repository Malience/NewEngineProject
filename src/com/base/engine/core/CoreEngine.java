package com.base.engine.core;

import java.util.ArrayList;

import com.base.engine.rendering.RenderingEngine;

public class CoreEngine extends Engine
{
	private ArrayList<Engine> engines;
	
	public CoreEngine()
	{		
		priority = 0;
		start();
	}
	
	@Override
	protected void start()
	{	
		engines = new ArrayList<Engine>();
		engines.add(new RenderingEngine());
		
		run();
	}
	
	public void stop()
	{
		isRunning = false;
	}
	
	@Override
	protected void run()
	{
		isRunning = true;
		
		
		FrameLimiter framer = new FrameLimiter(60);
		
		
		while(isRunning)
		{
			boolean render = false;
			
			framer.update();
			
			while(framer.process())
			{
				render = true;
				
				framer.iterateProcess();
				
				//TODO Updating happens here
				
				framer.printFrames();
			}
			
			if(render)
			{
				if(!engines.isEmpty())
				{
					engines.get(0).run();
					if(!engines.get(0).getRunning())
						stop();
				}
				framer.iterateFrames();
			}
			else
			{
				framer.sleep();
			}
		}
		
		dispose();
	}
}