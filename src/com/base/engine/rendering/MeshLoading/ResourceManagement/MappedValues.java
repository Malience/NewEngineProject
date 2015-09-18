package com.base.engine.rendering.MeshLoading.ResourceManagement;

import java.util.HashMap;

import com.base.engine.core.Vector;

public abstract class MappedValues
{
	HashMap<String, Vector> vectorHashMap;
	HashMap<String, Float> floatHashMap;
	
	public MappedValues()
	{
		vectorHashMap = new HashMap<String, Vector>();
		floatHashMap = new HashMap<String, Float>();
	}
	
	public void addVector(String name, Vector vector) { vectorHashMap.put(name, vector); }
	public void addFloat(String name, float floatValue) { floatHashMap.put(name, floatValue); }
	
	public Vector getVector(String name)
	{
		Vector result = vectorHashMap.get(name);
		if(result != null)
			return result;

		return new Vector(0,0,0);
	}

	public float getFloat(String name)
	{
		Float result = floatHashMap.get(name);
		if(result != null)
			return result;

		return 0;
	}
}
