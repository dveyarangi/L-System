package io.l;



public class LModule 
{
	
	public final String label;
	
	


	public LModule( String label )
	{
		this.label = label;
	}
	
	public String getLabel() { return label; }
	
	public boolean isTerminal() { return false; }
	
	@Override
	public String toString() { return label; }
	
}
