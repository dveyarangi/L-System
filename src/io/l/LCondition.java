package io.l;

public interface LCondition {

	public boolean accept(LModule prev, LModule curr, LModule next);
}
