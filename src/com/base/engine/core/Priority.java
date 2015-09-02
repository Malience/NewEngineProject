package com.base.engine.core;

public interface Priority extends Comparable<Priority>
{
	public int getPriority();
	
	@Override
	public default int compareTo(Priority p)
	{
		if(getPriority() > p.getPriority())
			return 1;
		else if(getPriority() == p.getPriority())
			return 0;
		else
			return -1;
	}
}
