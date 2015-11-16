package io.l;

/**
 * Production rule is defined by 
 * <li> module label it processe
 * <li> condition on context, module parameters or environment
 * <li> module generator that expands the input module to array of successors.
 * 
 * @author Fima
 */
public class LProductionRule 
{
	public final String label;
	
	public final LCondition condition;
	
	public final LModuleGenerator generator;

	public LProductionRule(String label, LCondition condition, LModuleGenerator generator) 
	{
		super();
		this.label = label;
		this.condition = condition;
		this.generator = generator;
	}

	public LProductionRule(String label, LModuleGenerator generator) 
	{
		this( label, DummyCondition.singleton, generator);
	}
	
	
	

}
