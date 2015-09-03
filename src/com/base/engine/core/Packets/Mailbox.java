package com.base.engine.core.Packets;

import com.base.engine.rendering.*;

public class Mailbox
{
	private byte address;
	private Mail mail[];
	
	public Mailbox(byte address, mailType m[])
	{
		this.address = address;
		mail = new Mail[m.length];
		for(int i = 0; i < mail.length; i++)
		{
			switch(m[i])
			{
			case texture:
				mail[i] = new Mail<Texture>();
				break;
			case mesh:
				mail[i] = new Mail<Mesh>();
				break;
			case request:
				mail[i] = new Mail<Request>();
				break;
			}
		}
	}
	
	public enum mailType
	{
		texture,
		mesh,
		request;
	}
}
