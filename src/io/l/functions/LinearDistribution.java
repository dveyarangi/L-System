package io.l.functions;

public class LinearDistribution implements Distribution
{
	
	private final float a, b;
	
	public LinearDistribution(float a, float b)
	{
		this.a = a;
		this.b = b;
	}

	@Override
	public float eval(int x) { return a*x + b; }
	
}
