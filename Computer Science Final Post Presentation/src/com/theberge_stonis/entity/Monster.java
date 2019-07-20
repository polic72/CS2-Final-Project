package com.theberge_stonis.entity;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

//import com.theberge_stonis.entity.Drawable;
import com.theberge_stonis.entity.Entity;
import com.theberge_stonis.game.Game;
import com.theberge_stonis.game.Sprite;
import com.theberge_stonis.game.Window;
import com.theberge_stonis.entity.Obstacle;
//import com.theberge_stonis.entity.Updateable;

import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * An enemy that attacks the player.
 * 
 * @author Garrett Stonis
 *
 */
public class Monster extends Entity {

	private int wanderSpeed = 32;
	private int walkWait = 0;
	
	private boolean collidingObstacle = false;
	
	private Circle sight;
	private int sightSize = 175;
	
	private boolean killed = false;
	
	public Monster( int x, int y, int w, int h ) {
		super( x, y, w, h, false, 10 );
		
		this.setSprite( new Sprite( new Image( Game.RESOURCE_PATH + "Monster.png" ) ) );
		
		sight = new Circle();
		sight.setRadius(sightSize);
		sight.setCenterX(getHitBox().getX());
		sight.setCenterY(getHitBox().getY());
		//sight.setStroke(Color.GREEN);
	}
	
	private boolean canSee(Rectangle hitbox) {
		if (sight.intersects(hitbox.getBoundsInLocal())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean chasePlayer() {
		sight.setCenterX(getHitBox().getX());
		sight.setCenterY(getHitBox().getY());
		
		if (canSee(Game.getPlayer().getHitBox())) {
			return true;
		}
		else {
			return false;
		}
		
		/*int playerX = (int) Game.getPlayer().getX();
		int playerY = (int) Game.getPlayer().getY();
		
		if (Math.sqrt((playerX * playerX) + (getHitBox().getX() * getHitBox().getX())) <= 500 && Math.sqrt((playerY * playerY) + (getHitBox().getY() * getHitBox().getY())) <= 500) {
			return true;
		}
		else {
			return false;
		}*/
	}
	
	private boolean collidingObstacle(Rectangle rect) {
		LinkedList<Obstacle> listObsctacles = Game.getObjectList(Obstacle.class);

		for (int i = 0; i < listObsctacles.size(); ++i) {
			if (Entity.isCollidingWith(rect, listObsctacles.get(i).getHitBox())) {
				collidingObstacle = true;
				return true;
			}
		}
		
		collidingObstacle = false;
		return false;
	}
	
	private boolean canMove( int x_move, int y_move ) {
		
		Rectangle sightBox = new Rectangle();
		sightBox.setWidth( getHitBox().getWidth() );
		sightBox.setHeight( getHitBox().getHeight() );
		sightBox.setX(getX() + x_move);
		sightBox.setY(getY() + y_move);
		
		if (Math.abs( getX() - Game.getPlayer().getX()) < 32 &&
				Math.abs(getY() - Game.getPlayer().getY()) < 32 ) {
			
			return false;
		
		}
		
		if ( collidingObstacle( sightBox ) ) { return false; }
		
		return true;
		
	}
	
	private void moveMonster_Wander() throws InterruptedException {
		
		Random rand = new Random();
		
		int go = rand.nextInt(100);
		
		if (go % 50 == 0) {
			int direction = rand.nextInt(360);
			int distance = rand.nextInt(wanderSpeed);
		
			int moveX = (int)(distance * Math.cos(Math.toRadians(direction)));
			int moveY = (int)(distance * Math.sin(Math.toRadians(direction)));
		
			int sightX = 0;
			int sightY = 0;
		
			if (moveX == 0) sightX = 0;
			if (moveY == 0) sightY = 0;
			if (moveX > 0) sightX = 1;
			if (moveY > 0) sightY = 1;
			if (moveX < 0) sightX = -1;
			if (moveY < 0) sightY = -1;
		
			int moveD = (int)(Math.sqrt((moveX * moveX) + (moveY * moveY)));
		
			for (int i = 0; i < moveD; ++i) {
				if (canMove(sightX, sightY)) {
					setVelocity(sightX, sightY);
				
					Thread.sleep(walkWait);	//May be unnecessary; but may have instant travel.
				}
				else {
					break;
				}
			}
			
			//setVelocity(0, 0);
		
			/*if (canMove(sightX, sightY)) {
				this.setX(moveX);
				this.setY(moveY);
			
				return true;
			}
			else {
				return false;
			}*/
		}
	}
	
	private void moveMonster_Pursuit() throws InterruptedException {
		//while (chasePlayer()) {
			
			int moveX = (int)(Game.getPlayer().getX() - getX());
			int moveY = (int)(Game.getPlayer().getY() - getY());
			
			int sightX = 0;
			int sightY = 0;
			
			if (moveX == 0) sightX = 0;
			if (moveY == 0) sightY = 0;
			if (moveX > 0) sightX = 1;
			if (moveY > 0) sightY = 1;
			if (moveX < 0) sightX = -1;
			if (moveY < 0) sightY = -1;
			
			if (canMove(sightX, sightY)) {
				
				setVelocity(sightX, sightY);
					
				Thread.sleep(walkWait);	//May be unnecessary; but may have instant travel.
			}
			else {
				if (collidingObstacle) {
					if (sightY == 1) {
						setVelocity(1, 0);
					}
					/*else if (sightY == -1) {
						setVelocity(-1, 0);
					}*/
					else if (sightX == 1) {
						setVelocity(0, 1);
					}
					/*else if (sightX == -1) {
						setVelocity(0, -1);
					}*/
				}
				
				//setVelocity(0, 0);
				
				/*if (canMove(0, sightY)) {
					setVelocity(sightX, sightY);
				}
				else if (canMove(sightX, 0)) {
					setVelocity(sightX, sightY);
				}
				if (canMove(0, 1)) {
					setVelY(1);
				}
				else if (canMove(0, -1)) {
					setVelY(-1);
				}
				else if (canMove(1, 0)) {
					setVelX(1);
				}
				else if (canMove(-1, 0)) {
					setVelX(-1);
				}
				else {
					if (sightX == 1) {
						setVelY(1);
					}
					else if (sightY == 1) {
						setVelX(-1);
					}
					else if (sightX == -1) {
						setVelY(1);
					}
					else if (sightY == -1) {
						setVelX(-1);
					}
				}*/
				
				//break;
			}
		//}
	}
	
	@Override
	public void update() {
		super.update();
		
		if (Math.abs(getX() - Game.getPlayer().getX()) < 32 &&
			Math.abs(getY() - Game.getPlayer().getY()) < 32) {
			
			attackAtPlayer();
			
		}
		
		if (atkCool > 0) { atkCool--; }
		
		if (tentaclePos.x != tentacleDest.x || tentaclePos.y != tentacleDest.y) {
			
			if (Game.getPlayer().isCollidingWith(tentacleHitboxHori) || Game.getPlayer().isCollidingWith(tentacleHitboxVert)) {
				
				Game.getPlayer().hurt(5);
				
			}
			
			tentaclePos.x += (int)Math.signum(tentacleDest.x - tentaclePos.x) * 4;
			tentaclePos.y += (int)Math.signum(tentacleDest.y - tentaclePos.y) * 4;
			tentacleHitboxVert.setX(tentaclePos.x);
			tentacleHitboxVert.setY(tentaclePos.y);
			tentacleHitboxHori.setX(tentaclePos.x);
			tentacleHitboxHori.setY(tentaclePos.y);
		}
		
		if (chasePlayer()) {
			try {
				moveMonster_Pursuit();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//moveMonster_Pursuit();
		}
		else {
			try {
				moveMonster_Wander();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//moveMonster_Wander();
		}
	}

	Sprite spr_tentacleL = new Sprite(new Image(Game.RESOURCE_PATH + "tentacleL.png"));
	Sprite spr_tentacleR = new Sprite(new Image(Game.RESOURCE_PATH + "tentacleR.png"));
	Sprite spr_tentacleU = new Sprite(new Image(Game.RESOURCE_PATH + "tentacleU.png"));
	Sprite spr_tentacleD = new Sprite(new Image(Game.RESOURCE_PATH + "tentacleD.png"));
	Sprite curTentacle;
	Point tentaclePos = new Point(0,0);
	Point tentacleDest = new Point(0,0);
	Rectangle tentacleHitboxVert = new Rectangle(0,0,23,32);
	Rectangle tentacleHitboxHori = new Rectangle(0,0,23,32);
	int atkCool = 0;
	
	public void attackAtPlayer() {
		
		if(atkCool == 0) {
			
			int pX = (int) Game.getPlayer().getX();
			int pY = (int) Game.getPlayer().getY();
		
			//What direction is my sword going to move in?
			int xDiff = pX - (int)(getX());
			int yDiff = pY - (int)(getY());
		
			if (Math.abs(xDiff) > Math.abs(yDiff)) {
			
				//Left or right
				if (xDiff > 0) {
					//Swipe Right
					curTentacle = spr_tentacleR;
					tentaclePos = new Point((int)(getX()) + 32,(int)(getY()) - 32);
					tentacleDest = new Point((int)(getX()) + 32,(int)(getY()) + 32);
				} else {
					//Swipe Left
					curTentacle = spr_tentacleL;
					tentaclePos = new Point((int)(getX()) - 32,(int)(getY()) - 32);
					tentacleDest = new Point((int)(getX()) - 32,(int)(getY()) + 32);
				}
			
			} else {
			
				//Up or down
				if (yDiff < 0) {
					//Swipe Up
					curTentacle = spr_tentacleU;
					tentaclePos = new Point((int)(getX()) - 32,(int)(getY()) - 32);
					tentacleDest = new Point((int)(getX()) + 32,(int)(getY()) - 32);
				} else {
					//Swipe Down
					curTentacle = spr_tentacleD;
					tentaclePos = new Point((int)(getX()) - 32,(int)(getY()) + 32);
					tentacleDest = new Point((int)(getX()) + 32,(int)(getY()) + 32);
				}
			
			}
			
			atkCool = 120;
		
		}
		
	}
	
	@Override
	public void draw() {
		this.drawSprite(Game.getWindow().getGraphics(Window.CANVAS_PLAY));
		
		if (tentaclePos.x != tentacleDest.x || tentaclePos.y != tentacleDest.y) {
			
			curTentacle.draw(Game.getWindow().getGraphics(Window.CANVAS_PLAY), tentaclePos.x, tentaclePos.y);
			
		}
		//Game.getWindow().getGraphics(Window.CANVAS_PLAY).str
		
		Game.getWindow().getGraphics(Window.CANVAS_PLAY).strokeText(Integer.toString(this.getHP()), (int)(getX()), (int)(getY()));
	}

	@Override
	public void delete() {
		if (this.getHP() <= 0) {
			killed = true;
		}
		
		if (killed) {
			Game.getPlayer().score += 100;
		}
	}

}




















