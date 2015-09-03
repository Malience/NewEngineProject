package com.base.engine.core.Packets;

import java.util.ArrayList;

public class Mail<E>
{
	private ArrayList<Packet<E>> packs;
	
	public Mail()
	{
		packs = new ArrayList<Packet<E>>();
	}
	
	public E[] checkMail()
	{
		ArrayList<E> contents = new ArrayList<E>();
		for(int i = 0; i < packs.size(); i++)
		{
			contents.add(packs.get(i).pack);
		}
		E[] out = null;
		contents.toArray(out);
		return out;
	}
}
