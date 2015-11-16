package io.l.tree;

import io.l.LModule;
import io.l.LModuleGenerator;
import io.l.ModuleSet;
import io.l.functions.Distribution;

public class GenomicGenerator implements LModuleGenerator
{

	private String genes;
	
	private Distribution anglesDistribution;
	private Distribution sizesDistribution;
		
	public GenomicGenerator(String genes, Distribution anglesDistribution, Distribution sizeDistribution)
	{
		this.genes = genes;
		this.anglesDistribution = anglesDistribution;
		this.sizesDistribution = sizeDistribution;
	}

	@Override
	public ModuleSet generate(LModule m) 
	{
		ModuleSet result = new ModuleSet();
		
		TreeModule module = (TreeModule) m;
		
		for(int sidx = 0; sidx < genes.length(); sidx ++)
		{
			char M = genes.charAt(sidx);
			
			TreeModule newModule = new TreeModule(""+M, 
					0,            // newborn 
					module.totalTime+Tree.dt,
					sizesDistribution.eval(sidx),
					anglesDistribution.eval(sidx)
					);
			
			result.add( newModule );
		}
		
		return result;
	}


	

}
