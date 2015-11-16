package io.l.tree;

import io.l.BinaryCondition;
import io.l.DummyGenerator;
import io.l.LCondition;
import io.l.LModuleGenerator;
import io.l.LSystem;
import io.l.ModuleSet;

public class Tree {
	
	public static final String MROOT = "R";
	public static final String MAPEX = "A";
	public static final String MBRANCH = "B";
	public static final String MLEAF = "L";
	public static final String MSTOP = "S";
	
	
	private LModuleGenerator branchingGenerator = new GenomeGenerator();

	private float apexEater = 0.5f;
	public float timeDelta = 0.1f;


	public static void main(String ... args)
	{
		Tree tree = new Tree();
		
		TreeModule ROOT = new TreeModule(MROOT, 0, 0, 1, 0);
		
		LSystem lsystem = new LSystem( ROOT );
		
		lsystem.addRule(MROOT, new DummyGenerator(new TreeModule(MAPEX, 0, 0, 1, 0)));
		
		LCondition apexDeathCond = new BinaryCondition( tree.apexEater );
		
		lsystem.addRule(MAPEX, tree.branchingGenerator, apexDeathCond);
		lsystem.addRule(MAPEX, new DummyGenerator(new TreeModule(MSTOP, 0, 0, 1, 0)));
		
		lsystem.addRule(MBRANCH, new StaleGenerator(tree));
		lsystem.addRule(MLEAF, new StaleGenerator(tree));
		lsystem.addRule(MSTOP, new StaleGenerator(tree));
		
		
		ModuleSet result = lsystem.iterate(10);
		
	}
}
