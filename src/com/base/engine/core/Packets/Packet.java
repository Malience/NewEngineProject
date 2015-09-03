package com.base.engine.core.Packets;

public class Packet<E>
{
	String dest;
	String send;
	E pack;
	
	public Packet(String dest, String send, E pack)
	{
		this.dest = dest;
		this.send = send;
		this.pack = pack;
	}
}
