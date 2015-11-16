package io.l.tree;

import io.l.LModule;
import io.l.LModuleGenerator;
import io.l.ModuleSet;

public class StaleGenerator implements LModuleGenerator 
{
	private Tree tree;

	public StaleGenerator(Tree tree)
	{
		this.tree = tree;
	}
	
	@Override
	public ModuleSet generate(LModule m) 
	{
		TreeModule module = (TreeModule) m;
		return new ModuleSet(new TreeModule(m.label,
				module.lifeTime + tree.timeDelta,
				module.totalTime + tree.timeDelta,
				module.sizeModifier,
				module.angle
				));
	}

}
