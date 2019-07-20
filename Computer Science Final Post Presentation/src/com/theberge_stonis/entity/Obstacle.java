package com.theberge_stonis.entity;

import java.util.ArrayList;

//import com.theberge_stonis.entity.Drawable;
import com.theberge_stonis.entity.Entity;
import com.theberge_stonis.game.Game;
//import com.theberge_stonis.entity.Updateable;
import com.theberge_stonis.game.Sprite;
import com.theberge_stonis.game.Window;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * 
 * @author Garrett Stonis
 *
 */
public class Obstacle extends Entity {
	
	private static ArrayList<Obstacle> listObstacles= new ArrayList<>();

	public Obstacle(int x, int y, int w, int h) {
		super(x, y, w, h, true);

		this.setSprite(new Sprite(new Image(Game.RESOURCE_PATH + "Obstacle.png")));
		
		//hitbox.setFill(new ImagePattern(this.sprite));
		
		listObstacles.add(this);
	}
	
	public Obstacle(int x, int y, int w, int h, String sprite) {
		super(x, y, w, h, true);
		
		this.setSprite(new Sprite(new Image(sprite)));
		
		//hitbox.setFill(new ImagePattern(this.sprite));
		
		listObstacles.add(this);
	}
	

	public static ArrayList<Obstacle> getObstacles() {
		return listObstacles;
	}
	
	@Override
	public void update() {
		//Nothing until rocks can be destroyed.
	}

	@Override
	public void delete() {
		//Nothing gets deleted.
	}

	@Override
	public void draw() {
		
		this.drawSprite(Game.getWindow().getGraphics(Window.CANVAS_PLAY));
		//graphics.fillRect(hitbox.getX(), hitbox.getY(), hitbox.getWidth(), hitbox.getHeight());
		//graphics.drawImage(sprite, hitbox.getX(), hitbox.getY(), hitbox.getWidth(), hitbox.getHeight());
	}

}
















