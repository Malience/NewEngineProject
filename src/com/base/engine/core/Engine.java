package com.base.engine.core;

public abstract class Engine implements Disposable, Priority
{
	protected boolean isRunning;
	protected PriorityList<Disposable> disposables;
	protected int priority;
	private byte address;
	
	public Engine()
	{
		disposables = new PriorityList<Disposable>();
		isRunning = false;
		address = RandomGenerator.nextByte();
		start();
	}
	
	public void stop()
	{
		isRunning = false;
	}
	
	abstract protected void start();
	
	abstract protected void run();
	
	@Override
	public void dispose()
	{
		for(int i = 0; i < disposables.size(); i++)
			disposables.get(i).dispose();
	}
	
	@Override
	public int getPriority()
	{
		return priority;
	}
	
	public boolean getRunning()
	{
		return isRunning;
	}
	
	
}