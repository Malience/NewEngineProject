package com.base.engine.core;

import java.util.ArrayList;

public class PriorityList<P extends Priority>
{
	private ArrayList<P> list;
	
	public PriorityList()
	{
		list = new ArrayList<P>();
	}
	
	public void add(P priority)
	{
		list.add(priority);
		sort();
	}
	
	public P get(int index)
	{
		return list.get(index);
	}
	
	public boolean indexOf(int priority)
	{
		for(Priority p : list)
			if(p.getPriority() == priority)
				return true;
		return false;
	}
	
	public int lastIndexOf(int priority)
	{
		boolean found = false;
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).getPriority() == priority)
				found = true;
			if(found && list.get(i).getPriority() != priority)
				return i - 1;
		}	
		return -1;
	}
	
	public Priority remove(int index)
	{
		return list.remove(index);
	}
	
	public int size()
	{
		return list.size();
	}
	
	public void sort()
	{
		ArrayList<P> end = new ArrayList<P>();
		for(int i = 0; i < list.size(); i++)
		{
			while(list.get(i).getPriority() == -1)
				end.add(list.remove(i));
		}
		boolean unsorted;
		do
		{
			unsorted = false;
			for(int i = 1; i < list.size(); i++)
			{
				if(list.get(i-1).compareTo(list.get(i)) == 1)
				{
					P temp = list.get(i - 1);
					list.set(i-1, list.get(i));
					list.set(i, temp);
					unsorted = true;
				}	
			}
		}
		while(unsorted);
		list.addAll(end);
	}
	
	public void flip()
	{
		int size = list.size() - 1;
		
		for(int i = 0; i < size; i++)
		{
			list.add(i, list.remove(size));
		}
	}
	
	public void print()
	{
		for(int i = 0; i < list.size(); i++)
		{
			System.out.println(i + ": " + list.get(i).getPriority());
		}
		System.out.println();
	}
}
