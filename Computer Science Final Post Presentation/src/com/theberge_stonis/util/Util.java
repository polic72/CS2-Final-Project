package com.theberge_stonis.util;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.theberge_stonis.entity.Entity;
import com.theberge_stonis.game.Game;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * A class with static utility functions
 * 
 * @author Conner Theberge
 *
 */
public class Util {

	/**
	 * Sees if a position is free for movement only
	 * if the hitbox given does not collide with any solid entity.
	 * 
	 * Moves the given hitbox to the given position, checks for
	 * intersection with every solid entity, and puts the given hitbox
	 * back where it was before function call.
	 * 
	 * @param x X position to check
	 * @param y Y position to check
	 * @param hitbox The hitbox to test with
	 * @return Whether there was at least one collision
	 */
	public static boolean placeFree(int x, int y, Rectangle hitbox) {
		
		Point tempPos = new Point(hitbox.x, hitbox.y);
		
		hitbox.setLocation(x, y);
		
		LinkedList<Entity> allEntities = Game.getObjectList(Entity.class);
		
		for (Entity e : allEntities) {
			
			if (e.isSolid()) {
				
				if (e.isCollidingWith(e.getHitBox())) {
					
					hitbox.setLocation(tempPos.x, tempPos.y);
					
					return true;
					
				}
				
			}
			
		}
		
		return false;
		
	}
	
	public static double pointAt(int x1, int y1, int x2, int y2) {

		double a = (x2 - x1); //Adj
		double b = y2 - y1; //Opp
		
		//SOH CAH TOA
		double d = Math.atan2(b, a);
		d *= (180/Math.PI);
		d += 360;
		return d;
	}
	
	public static Image rotateImage(Image img, double angle) {

		ImageView iv = new ImageView(img);
		
		iv.setRotate(angle);
		
		SnapshotParameters p = new SnapshotParameters();
		
		p.setFill(Color.TRANSPARENT);
		
		return iv.snapshot(p, null);
		
	}
	
	public static Image resetRotation(Image img) {
		
		return rotateImage(img, 0);
		
	}
	
}