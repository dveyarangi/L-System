package io.l;

public interface LCondition {

	public boolean accept(LModule prev, LModule curr, LModule next);
	
	public static LCondition TRUE = new LCondition() {
		@Override
		public boolean accept(LModule prev, LModule curr, LModule next) {
			return true;
		}
	};

}
