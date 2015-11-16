package io.l;

import yarangi.numbers.RandomUtil;

public class BinaryCondition implements LCondition
{
	private float p;
	public BinaryCondition(float p)
	{
		this.p = p;
	}
	
	@Override
	public boolean accept(LModule prev, LModule curr, LModule next) {
		return RandomUtil.P(p);
	}
}
