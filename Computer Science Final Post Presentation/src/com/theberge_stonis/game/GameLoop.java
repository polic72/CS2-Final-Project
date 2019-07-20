package com.theberge_stonis.game;

import com.theberge_stonis.input.KeyListener;
import com.theberge_stonis.input.MouseListener;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class GameLoop extends AnimationTimer {

	@Override
	public void handle(long now) {
		
		//Game Loop

		updateAll();
		
		drawAll();
		
	}
	
	public void updateAll() {
		
		while(Game.getKeyInput().hasNext()) {
			
			KeyEvent e = Game.getKeyInput().getNextEvent();
			
			for (KeyListener k : Game.getKeyListeners()) {
				
				k.handleKey(e);
				
			}
			
		}
		
		while(Game.getMouseInput().hasNext()) {
			
			MouseEvent e = Game.getMouseInput().getNextEvent();
			
			for (MouseListener m : Game.getMouseListeners()) {

				m.handleMouse(e);
				
			}
			
		}
		
		for (Element e : Game.getObjectList(Element.class)) { e.update(); }
		
	}
	
	public void drawAll() {
		
		//Clear canvas
		Game.getWindow().clearCanvases();
		
		for (Element e : Game.getObjectList(Element.class)) { e.draw(); }
	
	}

}
