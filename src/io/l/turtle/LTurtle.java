package io.l.turtle;

import io.l.LSystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import eir.rendering.IRenderer;

public class LTurtle <S>
{
	
	public static class Pointer
	{
		Vector2 position;
		float angle;
		public Pointer(Vector2 position, float angle)
		{
			this.position = position;
			this.angle = angle;
		}
		public Pointer(Pointer pointer)
		{
			this.position = pointer.position.cpy();
			this.angle = pointer.angle;
		}
	}
		
	public interface Move
	{
		public void move(Pointer pointer, IRenderer renderer);
	}

	public final Move PUSH = new Move() {
		@Override public void move(Pointer pointer, IRenderer renderer)	{ LTurtle.this.push(); };
	};
	public final Move POP = new Move() { 
		@Override public void move(Pointer pointer, IRenderer renderer)	{ LTurtle.this.pop(); };
	};
	
	public LTurtle(LSystem <S> system, List <S> expansion, Matrix4 matrix)
	{
		this.expansion = expansion;
		
		this.matrix = matrix.cpy();
	}
	
	
	private List <S> expansion;
	Stack <Pointer> stack = new Stack<Pointer> ();
	
	Map <S, Move> moves = new HashMap <S, Move> ();
	
	Matrix4 matrix = new Matrix4();
	
	Pointer pointer;
	
	public LTurtle(Pointer startingPointer)
	{
		this.pointer = startingPointer;
	}
	
	
	public void addMove(S state, Move move)
	{
		moves.put( state, move );
	}
	
	public void push()
	{
		Pointer newPointer = new Pointer( pointer );
		stack.push( pointer );
		
		pointer = newPointer;
		
	}
	
	public Pointer pop()
	{
		this.pointer = stack.pop();
		
		return this.pointer;
	}
	
	public void render(IRenderer renderer)
	{
		for(S state : expansion)
		{
			Move move = moves.get( state );
			
			move.move(pointer, renderer);
		}
	}
}
