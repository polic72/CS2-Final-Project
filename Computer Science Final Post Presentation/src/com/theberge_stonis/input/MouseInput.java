package com.theberge_stonis.input;

import java.awt.Point;
import java.util.LinkedList;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * A wrapper type class to store event-driven mouse inputs
 * for the game loop.
 * 
 * @author Conner Theberge
 *
 */
public class MouseInput implements EventHandler<MouseEvent> {

	public static Point mousePos = new Point();
	
	public static Point getRelativeMousePos(int x, int y) {
		
		return new Point((int)(mousePos.x - x), (int)(mousePos.y - y));
	
	}
	
	private LinkedList<MouseEvent> events = new LinkedList<MouseEvent>();
		
	@Override
	public void handle(MouseEvent e) {
		
		if (e.getEventType() == MouseEvent.MOUSE_MOVED) {
			
			mousePos.setLocation(e.getSceneX(), e.getSceneY());
			
		} else {
		
			events.addLast(e);
		
		}
		
	}
	
	/**
	 * @return Whether there are more input events to handle
	 */
	public boolean hasNext() {
		
		return !events.isEmpty();
		
	}
	
	/**
	 * Gets the next input event in the queue
	 * 
	 * @return The next event to handle
	 */
	public MouseEvent getNextEvent() {
		
		return events.removeFirst();
		
	}
	
}