package io.l.tree;

import io.l.BinaryCondition;
import io.l.DummyGenerator;
import io.l.LCondition;
import io.l.LModule;
import io.l.LModuleGenerator;
import io.l.LSystem;
import io.l.ModuleSet;
import io.l.functions.Distribution;
import io.l.functions.LinearDistribution;
import yarangi.numbers.RandomUtil;

public class Tree {
	
	public static final String MROOT = "R";
	public static final String MAPEX = "A";
	public static final String MBRANCH = "B";
	public static final String MFLOWER = "F";
	public static final String MPREE = "P";
	public static final String MLEAF = "L";
	public static final String MSTOP = "S";
	public static final String MPUSH = "[";
	public static final String MPOP = "]";
	
	static final float dt = 1;
	
	public static final float MAX_TOTAL_ANGLE = 60;
	public static final float MIN_TOTAL_ANGLE = 10;
	
	public static final float MIN_SIZE = 1;
	public static final float MAX_SIZE = 1;
	
	public static final String [] BODY_GENES = new String [] {   "B",  "[BA]",  "[BF]",  "[L]" };
	public static final float  [] BODY_PROBS = new float []  { 0.3f,    0.3f,    0.1f,    0.3f };
	public static final String [] BUD_GENES = new String [] {   "B",  "[BF]",  "[BP]"};
	public static final float  [] BUD_PROBS = new float []  { 0.1f,    0.6f,    0.3f,    };
	
	
	private LModuleGenerator bodyGenerator;
	private LModuleGenerator budGenerator;

	private float apexEater = 1f;
	public float timeDelta = 0.1f;
	
	public Tree()
	{
		int bodyFactor = 5;
		
		String bodyGenes = createModules( bodyFactor, BODY_PROBS, BODY_GENES );
		
		Distribution bodyAngles = createAnglesDistribution( bodyFactor );
		Distribution bodySizes  = createSizeDistribution( bodyFactor );
		
		bodyGenerator = new GenomicGenerator(bodyGenes, bodyAngles, bodySizes);
		
		int budFactor = 5;
		
		String budGenes = createModules( budFactor, BUD_PROBS, BUD_GENES );
		
		Distribution budAngles = createAnglesDistribution( budFactor );
		Distribution budSizes  = createSizeDistribution( budFactor );
		
		budGenerator = new GenomicGenerator(budGenes, budAngles, budSizes);

	}
	
	
	public static String createModules(int N, float [] probs, String [] genes)
	{
		StringBuilder sb = new StringBuilder();
		
		for(int midx = 0; midx < N; midx ++)
		{
			String label = RandomUtil.pick(probs, genes);
			
			sb.append(label);
		}
		
		return sb.toString();
	}

	
	public static Distribution createAnglesDistribution(int N)
	{
		float sign = RandomUtil.sign();
		
		// total angle width of the distribution:
		float width = RandomUtil.R(MAX_TOTAL_ANGLE, MAX_TOTAL_ANGLE);
		
		float offset = sign * RandomUtil.R(MAX_TOTAL_ANGLE, MAX_TOTAL_ANGLE);
		
		float step = sign * RandomUtil.R(width / (N-1));
		
		return new LinearDistribution(step, offset);
		
		
	}
	
	public static Distribution createSizeDistribution(int N)
	{
		float sign = RandomUtil.sign();
		
		// total angle width of the distribution:
		float width = RandomUtil.R(MAX_TOTAL_ANGLE, MAX_TOTAL_ANGLE);
		
		float offset = sign * RandomUtil.R(MAX_TOTAL_ANGLE, MAX_TOTAL_ANGLE);
		
		float step = sign * RandomUtil.R(width / (N-1));
		
		return new LinearDistribution(step, offset);
		
		
	}


	public static void main(String ... args)
	{
		Tree tree = new Tree();
		
		TreeModule ROOT = new TreeModule(MROOT, 0, 0, 1, 0);
		
		LSystem lsystem = new LSystem( ROOT );
		
		lsystem.addRule(MROOT, new DummyGenerator(new TreeModule(MAPEX, 0, 0, 1, 0)));
		
		LCondition apexDeathCond = new BinaryCondition( tree.apexEater );
		
		lsystem.addRule(MAPEX, tree.bodyGenerator);
//		lsystem.addRule(MAPEX, new DummyGenerator(new TreeModule(MSTOP, 0, 0, 1, 0)), apexDeathCond) ;
		
		lsystem.addRule(MBRANCH, new StaleGenerator(tree));
		lsystem.addRule(MLEAF, new StaleGenerator(tree));
		lsystem.addRule(MSTOP, new StaleGenerator(tree));
		lsystem.addRule(MPUSH, new StaleGenerator(tree));
		lsystem.addRule(MPOP, new StaleGenerator(tree));
		lsystem.addRule(MFLOWER, tree.budGenerator);
		lsystem.addRule(MPREE, new StaleGenerator(tree));
		
		ModuleSet result = lsystem.iterate(10);
		
		for(LModule m : result)
			System.out.print(m);
		
	}
}
