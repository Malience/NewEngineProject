package com.base.game;

import com.base.engine.core.Priority;

public class TestPriority implements Priority
{
	private int priority;
	public TestPriority(int i)
	{
		priority = i;
	}
	
	@Override
	public int getPriority()
	{
		return priority;
	}
}
