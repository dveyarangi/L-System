package io.l;

import java.util.HashMap;
import java.util.Map;

public class ParametricModule 
{
	public Map <String, LParameter> params;
	
	public ParametricModule(LParameter ... params)
	{
		this.params = new HashMap <String, LParameter> ();
		for(LParameter p : params)
		{
			this.params.put(p.getName(), p);
		}
	}
	
	public float getValue(String paramName)
	{
		return params.get(paramName).getValue();
	}
}
