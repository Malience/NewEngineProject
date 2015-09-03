package com.base.engine.core;

public class Transform
{
	private Transform parent;
	private Matrix parentMatrix;

	private Vector pos;
	private Quaternion rot;
	private Vector scale;

	private Vector oldPos;
	private Quaternion oldRot;
	private Vector oldScale;

	public Transform()
	{
		pos = new Vector(0,0,0);
		rot = new Quaternion(0,0,0,1);
		scale = new Vector(1,1,1);

		parentMatrix = new Matrix().setIdentity();
	}

	public void update()
	{
		if(oldPos != null)
		{
			oldPos.set(pos);
			oldRot.set(rot);
			oldScale.set(scale);
		}
		else
		{
			oldPos = new Vector(0,0,0).set(pos).add(1.0f);
			oldRot = new Quaternion(0,0,0,0).set(rot).mul(0.5f);
			oldScale = new Vector(0,0,0).set(scale).add(1.0f);
		}
	}

	public void rotate(Vector axis, float angle)
	{
		rot = new Quaternion(axis, angle).mul(rot).normal();
	}

	public void lookAt(Vector point, Vector up)
	{
		rot = getLookAtRotation(point, up);
	}

	public Quaternion getLookAtRotation(Vector point, Vector up)
	{
		return new Quaternion(new Matrix().setRotation(point.sub(pos).normal(), up));
	}

	public boolean hasChanged()
	{
		if(parent != null && parent.hasChanged())
			return true;

		if(!pos.equals(oldPos))
			return true;

		if(!rot.equals(oldRot))
			return true;

		if(!scale.equals(oldScale))
			return true;

		return false;
	}

	public Matrix getTransformation()
	{
		Matrix translationMatrix = new Matrix().setTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix rotationMatrix = rot.toRotationMatrix();
		Matrix scaleMatrix = new Matrix().setScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	private Matrix getParentMatrix()
	{
		if(parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	public void setParent(Transform parent)
	{
		this.parent = parent;
	}

	public Vector getTransformedPos()
	{
		return getParentMatrix().transform(pos);
	}

	public Quaternion getTransformedRot()
	{
		Quaternion parentRotation = new Quaternion(0,0,0,1);

		if(parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

	public Vector getPos()
	{
		return pos;
	}
	
	public void setPos(Vector pos)
	{
		this.pos = pos;
	}

	public Quaternion getRot()
	{
		return rot;
	}

	public void setRot(Quaternion rotation)
	{
		this.rot = rotation;
	}

	public Vector getScale()
	{
		return scale;
	}

	public void setScale(Vector scale)
	{
		this.scale = scale;
	}
	
	public void setScale(float scale)
	{
		this.scale = new Vector(1,1,1).mul(scale);
	}
}
