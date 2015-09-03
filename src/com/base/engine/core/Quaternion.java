package com.base.engine.core;


public class Quaternion extends Vector
{
	public Quaternion()
	{
		this(0,0,0,1);
	}
	
	public Quaternion(float x, float y, float z, float w)
	{
		super(new float[]{x, y, z, w});
	}
	
	public Quaternion(float[] f)
	{
		super(f);
		if(f.length != 4)
		{
			this.set(this.trim(4).get());
			size = getSize();
		}
	}
	
	public Quaternion(Vector axis, float angle)
	{
		float cos = (float) Math.cos(angle / 2);
		float sin = (float) Math.sin(angle / 2);
		
		float rx = axis.getValue(0) * sin;
		float ry = axis.getValue(1) * sin;
		float rz = axis.getValue(2) * sin;
		float rw = cos;
		
		this.set(rx, ry, rz, rw);
	}
	
	
	public Quaternion(Matrix rot) {
		float trace = rot.get(0, 0) + rot.get(1, 1) + rot.get(2, 2);

		if(trace > 0)
		{
			float s = 0.5f / (float)Math.sqrt(trace+ 1.0f);
			v[3] = 0.25f / s;
			v[0] = (rot.get(1, 2) - rot.get(2, 1)) * s;
			v[1] = (rot.get(2, 0) - rot.get(0, 2)) * s;
			v[2] = (rot.get(0, 1) - rot.get(1, 0)) * s;
		}
		else
		{
			if(rot.get(0, 0) > rot.get(1, 1) && rot.get(0, 0) > rot.get(2, 2))
			{
				float s = 2.0f * (float)Math.sqrt(1.0f + rot.get(0, 0) - rot.get(1, 1) - rot.get(2, 2));
				v[3] = (rot.get(1, 2) - rot.get(2, 1)) / s;
				v[0] = 0.25f * s;
				v[1] = (rot.get(1, 0) + rot.get(0, 1)) / s;
				v[2] = (rot.get(2, 0) + rot.get(0, 2)) / s;
			}
			else if(rot.get(1, 1) > rot.get(2, 2))
			{
				float s = 2.0f * (float)Math.sqrt(1.0f + rot.get(1, 1) - rot.get(0, 0) - rot.get(2, 2));
				v[3] = (rot.get(2, 0) - rot.get(0, 2)) / s;
				v[0] = (rot.get(1, 0) + rot.get(0, 1)) / s;
				v[1] = 0.25f * s;
				v[2] = (rot.get(2, 1) + rot.get(1, 2)) / s;
			}
			else
			{
				float s = 2.0f * (float)Math.sqrt(1.0f + rot.get(2, 2) - rot.get(0, 0) - rot.get(1, 1));
				v[3] = (rot.get(0, 1) - rot.get(1, 0) ) / s;
				v[0] = (rot.get(2, 0) + rot.get(0, 2) ) / s;
				v[1] = (rot.get(1, 2) + rot.get(2, 1) ) / s;
				v[2] = 0.25f * s;
			}
		}

		float length = (float)Math.sqrt(v[0]*v[0] + v[1]*v[1] + v[2]*v[2] +v[3]*v[3]);
		v[3] /= length;
		v[0] /= length;
		v[1] /= length;
		v[2] /= length;
	}

	public Quaternion setRotation(Vector axis, float angle)
	{
		float cos = (float) Math.cos(angle / 2);
		float sin = (float) Math.sin(angle / 2);
		
		float rx = axis.getValue(0) * sin;
		float ry = axis.getValue(1) * sin;
		float rz = axis.getValue(2) * sin;
		float rw = cos;
		
		return new Quaternion(rx, ry, rz, rw);
	}
	
	public Quaternion conjugate()
	{
		return new Quaternion(-getValue(0), -getValue(1), -getValue(2), getValue(3));
	}
	
	public Quaternion mul(Quaternion r)
	{
		float x = getValue(0) * r.getValue(3) + getValue(3) * r.getValue(0) + getValue(1) * r.getValue(2) - getValue(2) * r.getValue(1);
		float y = getValue(1) * r.getValue(3) + getValue(3) * r.getValue(1) + getValue(2) * r.getValue(0) - getValue(0) * r.getValue(2);
		float z = getValue(2) * r.getValue(3) + getValue(3) * r.getValue(2) + getValue(0) * r.getValue(1) - getValue(1) * r.getValue(2);
		float w = getValue(3) * r.getValue(3) - getValue(0) * r.getValue(0) - getValue(1) * r.getValue(1) - getValue(2) * r.getValue(2);
		
		return new Quaternion(x, y, z, w);
	}
	
	public Quaternion mul(Vector r)
	{
		float x = getValue(3) * r.getValue(0) + getValue(1) * r.getValue(2) - getValue(2) * getValue(1);
		float y = getValue(3) * r.getValue(1) + getValue(2) * r.getValue(0) - getValue(0) * getValue(2);
		float z = getValue(3) * r.getValue(2) + getValue(0) * r.getValue(1) - getValue(1) * getValue(0);
		float w = -getValue(0) * r.getValue(0) - getValue(1) * r.getValue(1) - getValue(2) * getValue(2);
		
		return new Quaternion(x, y, z, w);
	}
	
	@Override
	public Quaternion mul(float f)
	{
		float[] value = new float [size];
		for(int i = 0; i < size; i++)
		{
			value[i] = v[i] * f;
		}
		return new Quaternion(value);
	}
	
	public Matrix toRotationMatrix()
	{
		Vector forward = new Vector(2.0f * (getValue(0)*getValue(2) - getValue(3)*getValue(1)), 2.0f * (getValue(1)*getValue(2) + getValue(3)*getValue(0)), 1.0f - 2.0f * (getValue(0)*getValue(0) + getValue(1)*getValue(1)));
		Vector up = new Vector(2.0f * (getValue(0)*getValue(1) + getValue(3)*getValue(2)), 1.0f - 2.0f * (getValue(0)*getValue(0) + getValue(2)*getValue(2)), 2.0f * (getValue(1)*getValue(2) - getValue(3)*getValue(0)));
		Vector right = new Vector(1.0f - 2.0f * (getValue(1)*getValue(1) + getValue(2)*getValue(2)), 2.0f * (getValue(0)*getValue(1) - getValue(3)*getValue(2)), 2.0f * (getValue(0)*getValue(2) + getValue(3)*getValue(1)));
		
		return new Matrix().setRotation(forward, up, right);
	}
	
	public Vector getForward()
	{
		return new Vector(0,0,1).rotate(this);
	}

	public Vector getBack()
	{
		return new Vector(0,0,-1).rotate(this);
	}

	public Vector getUp()
	{
		return new Vector(0,1,0).rotate(this);
	}

	public Vector getDown()
	{
		return new Vector(0,-1,0).rotate(this);
	}

	public Vector getRight()
	{
		return new Vector(1,0,0).rotate(this);
	}

	public Vector getLeft()
	{
		return new Vector(-1,0,0).rotate(this);
	}
	
	@Override
	public Quaternion normal()
	{
		return new Quaternion(this.div(length()).get());
	}
	
	@Override
	public Quaternion set(float... values)
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
	
	@Override
	public Quaternion set(Vector v)
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
	
	public Quaternion set(Quaternion v)
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
	
}
