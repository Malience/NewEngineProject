package com.base.engine.core;


public class Vector
{
	protected float[] v;
	protected int size;
	
	public Vector()
	{
		v = new float[2];
		size = getSize();
	}
	
	public Vector(int size)
	{
		v = new float[size];
		size = getSize();
	}
	
	public Vector(float[] values)
	{
		float[] f = new float [values.length];
		for(int i = 0; i < values.length; i++)
		{
			f[i] = values[i];
		}
		v = f;
		size = getSize();
	}
	
	public Vector(float x, float y)
	{
		float[] f = new float []{x, y};
		v = f;
		size = getSize();
	}
	
	public Vector(float x, float y, float z)
	{
		float[] f = new float []{x, y, z};
		v = f;
		size = getSize();
	}
	public Vector(float x, float y, float z, float w) {
		float[] f = new float []{x, y, z, w};
		v = f;
		size = getSize();
	}

	/*	
	public Vector(Vector v)
	{
		float[] f = new float [v.getSize()];
		for(int i = 0; i < v.getSize(); i++)
		{
			f[i] = v.getValue(i);
		}
		
	}
	*/
	public float length()
	{
		float ans = 0;
		for(int i = 0; i < getSize(); i++)
		{
			ans += (float) Math.pow(v[i], 2);
		}
		return (float) Math.sqrt(ans);
	}
	
	public Vector mul(float f)
	{
		float[] value = new float [size];
		for(int i = 0; i < size; i++)
		{
			value[i] = v[i] * f;
		}
		return new Vector(value);
	}
	
	public Vector div(float f)
	{
		float[] value = new float [size];
		for(int i = 0; i < size; i++)
		{
			value[i] = v[i] / f;
		}
		return new Vector(value);
	}
	
	public float max()
	{
		float ans = getValue(0);
		for(int i = 1; i < this.size; i++)
			ans = Math.max(ans, getValue(i));
		return ans;
	}
	
	public float dot(Vector v)
	{
		if(this.size != v.size)
			return 0;
		float ans = 0;
		for(int i = 0; i < this.size; i++)
		{
			ans += this.v[i] * v.v[i];
		}
		
		return ans;
		
	}
	
	public Vector cross(Vector v)
	{
		if(this.size != v.size || this.size <= 2)
			return null;
		float[] f = new float[this.size];
		for(int i = 0; i < this.size; i++)
		{
			if(i == this.size - 1)
				f[i] = this.v[0] * v.v[1] - this.v[1] * v.v[0];
			else if(i == this.size - 2)
				f[i] = this.v[this.size-1] * v.v[0] - this.v[0] * v.v[this.size-1];
			else
				f[i] = this.v[i+1] * v.v[i+2] - this.v[i+2] * v.v[i+1];
		}
		return new Vector(f);
	}
	
	public float cross2d(Vector v)
	{
		return this.getValue(0) * v.getValue(1) - this.getValue(1) * v.getValue(0);
	}
	
	public Vector normal()
	{
		return new Vector(this.div(length()).get());
	}
	
	public Vector rotate(float angle)
	{
		if(size != 2)
			return null;
		
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		
		return new Vector(new float[] {(float)(getValue(0) * cos - getValue(1) * sin), (float)(getValue(0) * sin + getValue(1) * cos)});
	}
	
	public Vector rotate(Vector axis, float angle)
	{
		
		float sinAngle = (float)Math.sin(-angle); 
 		float cosAngle = (float)Math.cos(-angle); 
 
 
		return this.cross(axis.mul(sinAngle)).add(           //Rotation on local X 
 				(this.mul(cosAngle)).add(                     //Rotation on local Z 
 						axis.mul(this.dot(axis.mul(1 - cosAngle))))); //Rotation on local Y 

	}
	
	public Vector rotate(Quaternion rotation)
	{
		
		Quaternion conjugate = rotation.conjugate();
		
		
		Quaternion w = rotation.mul(this).mul(conjugate);
		
		
		return  new Vector(new float[]{w.getValue(0), w.getValue(1), w.getValue(2)});
	}
	
	public Vector add(float value)
	{
		float[] f = new float[this.size];
		for(int i = 0; i < this.size; i++)
		{
			f[i] = this.getValue(i) + value;
		}
		return new Vector(f);
	}
	
	public Vector add(Vector v)
	{
		float[] f = new float[this.size];
		for(int i = 0; i < this.size; i++)
		{
			f[i] = this.getValue(i) + v.getValue(i);
		}
		return new Vector(f);
	}
	
	public Vector sub(Vector v)
	{
		float[] f = new float[this.size];
		for(int i = 0; i < this.size; i++)
		{
			f[i] = this.getValue(i) - v.getValue(i);
		}
		return new Vector(f);
	}
	
	public int getSize()
	{
		return v.length;
	}
	
	
	public float[] get()
	{
		float[] f = new float [size];
		for(int i = 0; i < size; i++)
		{
			f[i] = getValue(i);
		}
		return f;
	}
	
	public float getValue(int i)
	{
		return v[i];
	}
	
	public float getX()
	{
		return v[0];
	}
	
	public float getY()
	{
		return v[1];
	}
	
	public float getZ()
	{
		return v[2];
	}
	
	public Vector set(Vector v)
	{
		float[] f = new float [v.size];
		for(int i = 0; i < v.size; i++)
		{
			f[i] = v.getValue(i);
		}
		this.v = f;
		size = getSize();
		return this;
	}
	
	public Vector set(float... values)
	{
		float[] f = new float [values.length];
		for(int i = 0; i < values.length; i++)
		{
			f[i] = values[i];
		}
		v = f;
		size = getSize();
		return this;
	}
	
	public void setValue(int i, float value)
	{
		v[i] = value;
	}
	
	public Vector trim(int i)
	{
		float[] f = new float[i];
		for(int j = 0; j < i; j++)
		{
			f[j] = getValue(j);
		}
		
		return new Vector(f);
	}
	
	public Vector swizzle(int... swiz)
	{
		float[] f = new float[swiz.length];
		for(int i = 0; i < swiz.length; i++)
		{
			f[i] = getValue(swiz[i]); 
		}
		return new Vector(f);	
	}
	
	public Vector lerp(Vector dest, float lerpFactor)
	{
		return dest.sub(this).mul(lerpFactor).add(this);
	}
	
	public boolean equals(Vector v)
	{
		if(this.getSize() != v.getSize())
			return false;
		for(int i = 0; i < this.size; i++)
			if(this.getValue(i) != v.getValue(i)) return false;
		return true;
	}
	
	public boolean equals(Vector2f v)
	{
		return v.getX() == getX() && v.getY() == getY();
	}
	
	public boolean equals(Vector3f v)
	{
		return v.getX() == getX() && v.getY() == getY() && v.getZ() == getZ();
	}
	
	public void print()
	{
		String line = new String("Vector: ");
		for(int i = 0; i < size; i++)
		{
			line += v[i];
			if(i != size-1)
				line += ", ";
		}
		System.out.println(line);
	}
}
