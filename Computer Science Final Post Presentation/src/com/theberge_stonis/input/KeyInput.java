package com.theberge_stonis.input;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * A wrapper type class to store event-driven key inputs
 * for the game loop.
 * 
 * @author Conner Theberge
 *
 */
public class KeyInput implements EventHandler<KeyEvent> {

	private LinkedList<KeyEvent> events = new LinkedList<KeyEvent>();
	
	public Map<KeyCode, Boolean> keyState = new HashMap<>();
	
	public boolean isKeyPressed(KeyCode key) { return keyState.get(key); }
	
	@Override
	public void handle(KeyEvent e) {
		
		events.addLast(e);
		
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
	public KeyEvent getNextEvent() {
		
		return events.removeFirst();
		
	}

}