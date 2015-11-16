package io.l.tree;

import io.l.LModule;

public class TreeModule extends LModule 
{

	public float lifeTime;
	public float totalTime;
	public float sizeModifier;
	public float angle;
	
	public TreeModule(String label, float lifeTime, float totalTime, float sizeModifier, float angle) 
	{
		super(label);
		this.lifeTime = lifeTime;
		this.totalTime = totalTime;
		this.sizeModifier = sizeModifier;
		this.angle = angle;
	}
	
}
