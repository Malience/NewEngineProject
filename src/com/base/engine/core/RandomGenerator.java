package com.base.engine.core;

import java.util.Random;

public class RandomGenerator 
{
	private static Random r = new Random();
	
	public static byte nextByte()
	{
		byte b[] = new byte[1];
		r.nextBytes(b);
		return b[0];
	}
}
