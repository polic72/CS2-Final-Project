package com.theberge_stonis.entity;

import com.theberge_stonis.game.Game;
import com.theberge_stonis.game.Sprite;
import com.theberge_stonis.game.Window;

import javafx.scene.image.Image;

/**
 * 
 * @author Garrett Stonis
 */
public class Treasure extends Entity{
	
	private boolean collected = false;

	public Treasure(int x, int y, int w, int h) {
		super(x, y, w, h, true);

		this.setSprite( new Sprite( new Image( Game.RESOURCE_PATH + "treasure.png" ) ) );
	}
	
	@Override
	public void update() {
		if (isCollidingWith(Game.getPlayer().getHitBox())) {
			collected = true;
			
			Game.deactivateElement(this);
		}
	}

	@Override
	public void draw() {
		this.drawSprite(Game.getWindow().getGraphics(Window.CANVAS_PLAY));
	}

	@Override
	public void delete() {
		if (collected) {
			Game.getPlayer().score += 150;
		}
	}

}





















