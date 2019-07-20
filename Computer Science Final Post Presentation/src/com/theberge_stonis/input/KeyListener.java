package com.theberge_stonis.input;

import javafx.scene.input.KeyEvent;

/**
 * An interface that designates an instance
 * that receives input from the keyboard
 * 
 * @author Conner Theberge
 *
 */
public interface KeyListener {

	public void handleKey(KeyEvent e);
	
}