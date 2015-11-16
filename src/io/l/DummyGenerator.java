package io.l;


public class DummyGenerator implements LModuleGenerator
{
	
	private ModuleSet production;
	
	public DummyGenerator(LModule ... modules)
	{
		this.production = new ModuleSet();
		
		for(int midx = 0; midx < modules.length; midx ++)
			production.add( modules[midx] );
	}
	
	public DummyGenerator(ModuleSet production)
	{
		this.production = production;
	}

	@Override
	public ModuleSet generate(LModule m) 
	{
		return production;
	}

}
