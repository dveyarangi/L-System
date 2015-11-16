package io.l.functions;

import yarangi.numbers.RandomUtil;

public class UniformDistribution implements Distribution
{
	
	private final float min;
	private final float max;

	public UniformDistribution(float min, float max)
	{
		this.min = min;
		this.max = max;
	}

	@Override
	public float eval(int x) {
		return RandomUtil.R(min, max);
	}

}
