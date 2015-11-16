package io.l;

public class LParameter 
{
	private String name;
	private float value;
	
	public LParameter(String name)
	{
		this.name = name;
		this.value = 0;
	}
	
	public LParameter(String name, float value)
	{
		this.name = name;
		this.value = value;
	}
	
	public void setValue( float value ) { this.value = value; }
	public String getName() { return name; }
	public float getValue() { return value; }
}
