package com.base.game;

import com.base.engine.core.Matrix;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.PriorityList;
import com.base.engine.core.Vector;
import com.base.engine.core.Vector2f;

public class Tester 
{
	public static void main(String [] args)
	{
		//TestMatrix();
		TestVector2f();
	}
	
	public static void TestPriority()
	{
		PriorityList<TestPriority> pl = new PriorityList<TestPriority>();
		TestPriority t0 = new TestPriority(0);
		TestPriority t1 = new TestPriority(1);
		TestPriority t2 = new TestPriority(2);
		TestPriority t3 = new TestPriority(3);
		pl.add(t2);
		pl.add(t3);
		pl.add(t0);
		pl.add(t1);
		pl.print();
		pl.sort();
		pl.print();
		pl.flip();
		pl.print();
	}
	
	public static void TestMatrix()
	{
		Matrix mine = new Matrix();
		Matrix4f benny = new Matrix4f();
		
		mine.print();
		benny.print();
		
		mine.setIdentity();
		benny.initIdentity();
		
		mine.print();
		benny.print();
		
		mine.setTranslation(3, 5, 2);
		benny.initTranslation(3, 5, 2);
		
		mine.print();
		benny.print();
		
		mine.setRotation(20, 40, 30);
		benny.initRotation(20, 40, 30);
		
		mine.print();
		benny.print();		
		System.out.println(mine.equals(benny));
		
		mine.setScale(1,3,4);
		benny.initScale(1, 3, 4);
		
		mine.print();
		benny.print();		
		System.out.println(mine.equals(benny));
		
		mine = mine.setTranslation(2, 3, 5).mul(new Matrix().setRotation(20, 30, 40).mul(new Matrix().setScale(4, 2, 7)));
		benny = benny.initTranslation(2, 3, 5).mul(new Matrix4f().initRotation(20, 30, 40).mul(new Matrix4f().initScale(4, 2, 7)));
		
		mine.print();
		benny.print();		
		System.out.println(mine.equals(benny));
	}
	
	public static void TestQuaternion()
	{
		
	}
	
	public static void TestVector2f()
	{
		Vector mine = new Vector(2, 4);
		Vector2f benny = new Vector2f(2, 4);
		
		mine.print();
		benny.print();		
		System.out.println(mine.equals(benny));
	}
	
	public static void TestVector3f()
	{
		
	}
}
