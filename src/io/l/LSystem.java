package io.l;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * L system is defined by set of {@link LModule}s and mapping of {@link LProductionRule}s.
 *
 * Production rules are iteratively applied to {@link #axiom} module set, to produce consequent L system states.
 * 
 * @author Fima
 *
 */
public class LSystem
{
	
	/**
	 * Set of labels of all participating modules
	 */
	public List <String> labels;
	
	/**
	 * Initial module
	 */
	public ModuleSet axiom;
	
	/**
	 * Set of regular production rules
	 */
	public Map <String, List <LProductionRule>> rules = new HashMap <String, List <LProductionRule>> ();
	
	/**
	 * Create L system starting with specified axiom set
	 * @param axiom
	 */
	public LSystem( LModule ... axiom )
	{
		
		this.axiom = new ModuleSet ( axiom );
		
		this.labels = new ArrayList <String> ();
	} 

/*	public void addStates( String label )
	{
		labels.add( label );
	}
	
	public void addStates( String ... labels )
	{
		for(String s : labels)
			this.labels.add( s );
	}*/
	
	/**
	 * @return axiom
	 */
	public ModuleSet axiom()
	{
		return axiom;
	}

	public void addRule(String label, LModuleGenerator generator, LCondition condition) 
	{
	
		addRule( label, new LProductionRule(label, condition, generator));
	}	
	
	/**
	 * Add rule with collaborative condition.
	 * @param label
	 * @param generator
	 */
	public void addRule(String label, LModuleGenerator generator) 
	{
	
		addRule( label, new LProductionRule(label, generator));
	}	
	
	public void addRule(String label, LProductionRule rule) 
	{
		List <LProductionRule> labelRules = rules.get( label );
		if(labelRules == null)
		{
			labelRules = new ArrayList <LProductionRule> ();
			this.rules.put( label, labelRules );
		}
		
		labelRules.add( rule );
		
	}	
	
	/**
	 * Context-dependent module expansion.
	 * @param prev 
	 * @param curr
	 * @param next
	 * @param target
	 */
	public void expandModule( LModule prev, LModule curr, LModule next, ModuleSet target)
	{
		// getting rule set for module label:
		List <LProductionRule> moduleRules = rules.get( curr.label );
		if(moduleRules == null || moduleRules.isEmpty())
			throw new IllegalStateException("No rules for module " + curr.label);

		// checking rules:
		for(int ridx = 0; ridx < moduleRules.size(); ridx ++)
		{
			LProductionRule rule = moduleRules.get( ridx );
			if(!rule.condition.accept( prev, curr, next ) )
				continue; // skip
			
			// expanding module
			ModuleSet result = rule.generator.generate( curr );
			
			target.addAll( result );
		}
		
	}
	
	/**
	 * Expand the module array:
	 * 
	 * @param sentence
	 * @return
	 */
	public ModuleSet expand(ModuleSet sentence)
	{
		ModuleSet newSentence = new ModuleSet ();
		boolean isTerminal = true;
		for(int idx = 0; idx < sentence.size(); idx ++ )
		{
			LModule currModule = sentence.get( idx );
			if(currModule.isTerminal())
			{
				newSentence.add( currModule );
				continue;
			}
			
			isTerminal = false;
			
			LModule prevModule = idx == 0 ? null : sentence.get(idx-1);
			LModule nextModule = idx == sentence.size()-1 ? null : sentence.get(idx+1);
			
			expandModule( prevModule,  currModule, nextModule, newSentence );
		}
		
		if(isTerminal)
			return null;
		
		return newSentence;
	}


	/**
	 * Recursively expand the system from axiom, for specified num of iterations
	 * @param iterations
	 * @return
	 */
	public ModuleSet iterate(int iterations) 
	{
		ModuleSet expansion = axiom();
		ModuleSet newExpansion;
		for(int iteration = 0; iteration < iterations; iteration ++)
		{
			newExpansion = expand( expansion );
			if(newExpansion == null)
				return expansion;
			
			expansion = newExpansion;
		}
		
		return expansion;
	}	
	
	/**
	 * Recursively expand the system from axiom, until all modules are in terminal state.
	 * @return
	 */
	public ModuleSet iterate() 
	{
		ModuleSet expansion = axiom();
		ModuleSet newExpansion;
		while( true )
		{
			newExpansion = expand( expansion );
			if(newExpansion == null)
				return expansion;
			
			expansion = newExpansion;
		}
	}	
	
	public static void main(String ... args)
	{
		String As = "A";
		String Bs = "B";
		
		LModule A = new LModule( As );
		LModule B = new LModule( Bs);
		
		LSystem system = new LSystem ( A );
		
		system.addRule( As, new DummyGenerator(A, B) );
		system.addRule( Bs, new DummyGenerator(A)    );
		
		
		ModuleSet expansion = system.iterate( 3 );
		
		expansion.print();
	}

}
