package com.theberge_stonis.game;

import java.util.Random;
//import Monster;
//import Player;
//import Obstacle;
//import Item;

//import com.theberge_stonis.entity.Drawable;
import com.theberge_stonis.game.Game;
import com.theberge_stonis.entity.Monster;
import com.theberge_stonis.entity.Obstacle;
import com.theberge_stonis.entity.Treasure;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * 
 * @author Garrett Stonis
 *
 */
public class Room {
	
	private Sprite floor = new Sprite(new Image(Game.RESOURCE_PATH + "Ground48.png"));
	
	//private long roomSeed;
	
	//private LinkedList<Item> listItems = new LinkedList<Item>();
	
	private int numTilesVertical = 11;
	private int numTilesHorizontal = 14;
	private int tileSize = 48;
	private final int hitboxSize = 32;
	
	public Room() {
		//
	}
	
	/**
	 * The generate() method is the main generation of an individual room.
	 * It is going to handle the generation of enemies, loot, obstacles,
	 * etc.
	 */
	public void generate() {
		Random rand = new Random();
		
		createBarrier();
		
		int num = 0;
		
		for (int i = 0; i < numTilesVertical; ++i) {
			for (int q = 0; q < numTilesHorizontal; ++q) {
				num = rand.nextInt((numTilesHorizontal * numTilesVertical));
				
				if (num % 10 == 0 && !inBarrier(q * tileSize, i * tileSize)) {
					spawnObstacle(q * tileSize, i * tileSize, hitboxSize, hitboxSize);
				}
				else if (num % (64 - Game.getPlayer().score / 1000)  == 0 && !inBarrier(q * tileSize, i * tileSize)) {
					spawnMonster(q * tileSize, i * tileSize, hitboxSize, hitboxSize);
				}
				else if (num % (14 + Game.getPlayer().score / 1000) == 0 && !inBarrier(q * tileSize, i * tileSize)) {
					spawnTreasure(q * tileSize, i * tileSize, hitboxSize, hitboxSize);
				}
			}
		}
	}
	
	/**
	 * The generate() method is the main generation of an individual room.
	 * It is going to handle the generation of enemies, loot, obstacles,
	 * etc.
	 * 
	 * @param seed - can be used to generate the same room every time.
	 */
	public void generate(long seed) {
		Random rand = new Random();
		
		rand.setSeed(seed);
		
		createBarrier();
		
		int num = 0;
		
		for (int i = 0; i < numTilesVertical; ++i) {
			for (int q = 0; q < numTilesHorizontal; ++q) {
				num = rand.nextInt((numTilesHorizontal * numTilesVertical));
				
				if (num % 10 == 0 && !inBarrier(q * tileSize, i * tileSize)) {
					spawnObstacle(q * tileSize, i * tileSize, hitboxSize, hitboxSize);
				}
				else if (num % (64 - Game.getPlayer().score / 1000)  == 0 && !inBarrier(q * tileSize, i * tileSize)) {
					spawnMonster(q * tileSize, i * tileSize, hitboxSize, hitboxSize);
				}
				else if (num % (14 + Game.getPlayer().score / 1000) == 0 && !inBarrier(q * tileSize, i * tileSize)) {
					spawnTreasure(q * tileSize, i * tileSize, hitboxSize, hitboxSize);
				}
			}
		}
	}
	
	public static void destroy() {
		for (Monster m : Game.getObjectList(Monster.class)) {
			Game.deactivateElement(m);
		}
		for (Obstacle o : Game.getObjectList(Obstacle.class)) {
			Game.deactivateElement(o);
		}
		for (Treasure t : Game.getObjectList(Treasure.class)) {
			Game.deactivateElement(t);
		}
	}
	
	private void createBarrier() {
		for (int i = 0; i < numTilesVertical; ++i) {
			for (int q = 0; q < numTilesHorizontal; ++q) {
				if (i == 0) {
					spawnObstacle(q * tileSize, 0, hitboxSize, hitboxSize);
					spawnObstacle(q * tileSize, (numTilesVertical * tileSize) - tileSize, hitboxSize, hitboxSize);
				}
			}
			
			spawnObstacle(0, i * tileSize, hitboxSize, hitboxSize);
			spawnObstacle((numTilesHorizontal * tileSize) - tileSize, i * tileSize, hitboxSize, hitboxSize);
		}
	}
	
	private boolean inBarrier(int x, int y) {
		for (int i = 0; i < numTilesVertical; ++i) {
			for (int q = 0; q < numTilesHorizontal; ++q) {
				if (i == 0) {
					if (x == q * tileSize && y == 0) {
						return true;
					}
					if (x == q * tileSize && y == (numTilesVertical * tileSize) - tileSize) {
						return true;
					}
				}
			}
			
			if (x == 0 && y == i * tileSize) {
				return true;
			}
			if (x == (numTilesHorizontal * tileSize) - tileSize && y == i * tileSize) {
				return true;
			}
		}
		
		return false;
	}
	
	private void spawnObstacle(int x, int y, int w, int h) {
		Game.activateElement(new Obstacle(x, y, w, h));
	}
	
	private void spawnMonster(int x, int y, int w, int h) {
		Game.activateElement(new Monster(x, y, w, h));
	}
	
	private void spawnTreasure(int x, int y, int w, int h) {
		Game.activateElement(new Treasure(x, y, w, h));
	}
	
	public void draw(GraphicsContext g) {
		
		for (int i = 0; i < numTilesHorizontal; i++) {
			for (int j = 0; j < numTilesVertical; j++) {
				
				floor.draw(g, i * tileSize, j * tileSize);
				
			}
		}
		
	}
}



















