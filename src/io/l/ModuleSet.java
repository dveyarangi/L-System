package io.l;

import java.util.ArrayList;

/**
 * Set of modules, for convenience
 */
public class ModuleSet extends ArrayList <LModule> 
{

	private static final long serialVersionUID = -5827860483932913472L;


	public ModuleSet(LModule ... modules)
	{
		super();
		for(int midx = 0; midx < modules.length; midx ++)
		{
			add( modules[midx] );
		}
	}
	
	public void print()
	{
		for(LModule s : this)
		{
			System.out.print( s );
		}
	}
}
