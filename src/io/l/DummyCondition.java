package io.l;

public class DummyCondition implements LCondition 
{

	public static LCondition singleton = new DummyCondition();

	@Override
	public boolean accept(LModule prev, LModule curr, LModule next) {
		return true;
	}

}
