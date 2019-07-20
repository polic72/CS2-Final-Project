package com.theberge_stonis.game;

/**
 * An Element is a base for all things that need to be drawn or updated within the game loop.
 * 
 * Element is always used in lists when organizing instances. From this class all other children
 * can be type-casted from.
 * 
 * @author Conner Theberge
 *
 */
public abstract class Element {
	
	public abstract void update();
	
	public abstract void draw();
	
	public abstract void delete();
	
}