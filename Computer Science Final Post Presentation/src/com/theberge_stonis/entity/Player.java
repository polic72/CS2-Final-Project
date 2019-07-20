package com.theberge_stonis.entity;

import java.awt.Point;
import java.util.LinkedList;

import com.theberge_stonis.game.Game;
import com.theberge_stonis.game.Sprite;
import com.theberge_stonis.game.Window;
import com.theberge_stonis.input.KeyListener;
import com.theberge_stonis.input.MouseListener;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/*
 * TestPerson, upon creation, is an inert piece of junk.
 * it will do nothing until it is added using Game.activateElement();
 * 
 * It can later be deactivated by using Game.deactivateElement();
 */

/**
 * Entity Child Testing
 * 
 * @author Conner Theberge
 *
 */
public class Player extends Entity implements KeyListener, MouseListener {
	
	public int score = 0;
	
	Sprite spr_swordL = new Sprite(new Image(Game.RESOURCE_PATH + "swordL.png"));
	Sprite spr_swordR = new Sprite(new Image(Game.RESOURCE_PATH + "sword.png"));
	Sprite spr_swordU = new Sprite(new Image(Game.RESOURCE_PATH + "swordU.png"));
	Sprite spr_swordD = new Sprite(new Image(Game.RESOURCE_PATH + "swordD.png"));
	Sprite curSword;
	Point swordPos = new Point(0,0);
	Point swordDest = new Point(0,0);
	Rectangle swordHitboxVert = new Rectangle(0,0,23,32);
	Rectangle swordHitboxHori = new Rectangle(0,0,23,32);
	int atkCool = 0;
	static final int MOVESPEED = 2;
	
	public Player(int x, int y, int w, int h, int hp) {
		super(x, y, w, h, false, hp);
		
		Game.addKeyListener(this);
		Game.addMouseListener(this);
		
		this.setSprite(new Sprite(new Image(Game.RESOURCE_PATH + "playerPH.png")));
		
	}
	
	/*private boolean collidingObstacle(Rectangle rect) {
		LinkedList<Obstacle> listObsctacles = Game.getObjectList(Obstacle.class);

		for (int i = 0; i < listObsctacles.size(); ++i) {
			if (Entity.isCollidingWith(rect, listObsctacles.get(i).getHitBox())) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean canMove( int x_move, int y_move ) {
		
		Rectangle sightBox = new Rectangle();
		sightBox.setWidth( getHitBox().getWidth() );
		sightBox.setHeight( getHitBox().getHeight() );
		sightBox.setX(getX() + x_move);
		sightBox.setY(getY() + y_move);
		
		if ( collidingObstacle( sightBox ) ) { return false; }
		System.out.println("Yes");
		return true;
		
	}*/
	
	@Override
	public void update() {
		super.update();
		
		if (atkCool > 0) { atkCool--; }
	
		if (swordPos.x != swordDest.x || swordPos.y != swordDest.y) {
			
			for (Monster m : Game.getObjectList(Monster.class)) {
				
				if (m.isCollidingWith(swordHitboxHori) || m.isCollidingWith(swordHitboxVert)) {
					
					m.hurt(5);
					
				}
				
			}
			
			swordPos.x += Math.signum(swordDest.x - swordPos.x) * 4;
			swordPos.y += Math.signum(swordDest.y - swordPos.y) * 4;
			swordHitboxVert.setX(swordPos.x);
			swordHitboxVert.setY(swordPos.y);
			swordHitboxHori.setX(swordPos.x);
			swordHitboxHori.setY(swordPos.y);
			
			if (swordPos.x == swordDest.x && swordPos.y == swordDest.y) {
				enableMovement();
			}
			
		}
		
	}

	@Override
	public void draw() {
		
		GraphicsContext g = Game.getWindow().getGraphics(Window.CANVAS_PLAY);
		
		//Draw myself using the getGraphics()!
		this.drawSprite(g);
		
		if (swordPos.x != swordDest.x || swordPos.y != swordDest.y) {
			
			curSword.draw(g, swordPos.x, swordPos.y);
			
		}
		
		Game.getWindow().getGraphics(Window.CANVAS_PLAY).strokeText(Integer.toString(this.getHP()), (int)getX(), (int)getY() - 32);
		Game.getWindow().getGraphics(Window.CANVAS_HUD).strokeText(String.format("Score: %d", score), 32, 32);
		
	}

	@Override
	public void delete() {
		
	}

	@Override
	public void handleKey(KeyEvent e) {
		
		if (e.getEventType() == KeyEvent.KEY_PRESSED) {
			
			switch(e.getCode()) {
			case W:
				/*if (canMove(0, -1)) {
					this.setVelY(-MOVESPEED);
				}
				else {
					this.setVelY(0);
				}*/
				
				this.setVelY(-MOVESPEED);
				break;
			case S:
				/*if (canMove(0, 1)) {
					this.setVelY(MOVESPEED);
				}
				else {
					this.setVelY(0);
				}*/
				
				this.setVelY(MOVESPEED);
				break;
			case D:
				/*if (canMove(1, 0)) {
					this.setVelX(MOVESPEED);
				}
				else {
					this.setVelX(0);
				}*/
				
				this.setVelX(MOVESPEED);
				break;
			case A:
				/*if (canMove(-1, 0)) {
					this.setVelX(-MOVESPEED);
				}
				else {
					this.setVelX(0);
				}*/
				
				this.setVelX(-MOVESPEED);
				break;
			case R:
				/*for (Monster m : Game.getObjectList(Monster.class)) {
					Game.deactivateElement(m);
				}
				for (Obstacle o : Game.getObjectList(Obstacle.class)) {
					Game.deactivateElement(o);
				}*/
				Game.getRoom().destroy();
				Game.regen();
				break;
			case EQUALS :
				this.score += 1000;	//A cheat code to increase score and for testing.
				break;
			case MINUS :
				this.score -= 1000;
				break;
			default:
				break;
			}
			
		}
		
		if (e.getEventType() == KeyEvent.KEY_RELEASED) {
			int dir = (int) Math.signum(this.getX() - this.getXPrev());
			if (e.getCode() == KeyCode.D && dir > 0) {
				this.setVelX(0);
			} else if (e.getCode() == KeyCode.A && dir < 0)  {
				this.setVelX(0);
			}
			
			int dirY = (int) Math.signum(this.getY() - this.getYPrev());
			if (e.getCode() == KeyCode.S && dirY > 0) {
				this.setVelY(0);
			} else if (e.getCode() == KeyCode.W && dirY < 0)  {
				this.setVelY(0);
			}
			
		}
		
	}

	@Override
	public void handleMouse(MouseEvent e) {
		
		if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
			
			if (e.getButton() == MouseButton.PRIMARY) {
				
				if (atkCool == 0) {
					disableMovement();
					Point mp = Game.getWindow().mousePosToCanvas(Window.CANVAS_PLAY);
					
					//What direction is my sword going to move in?
					int xDiff = mp.x - (int)(getX());
					int yDiff = mp.y - (int)(getY());
					
					if (Math.abs(xDiff) >=  Math.abs(yDiff)) {
						
						//Left or right
						if (xDiff > 0) {
							//Swipe Right
							curSword = spr_swordR;
							swordPos = new Point((int)(getX()) + 32,(int)(getY()) - 32);
							swordDest = new Point((int)(getX()) + 32,(int)(getY()) + 32);
						} else {
							//Swipe Left
							curSword = spr_swordL;
							swordPos = new Point((int)(getX()) - 32,(int)(getY()) - 32);
							swordDest = new Point((int)(getX()) - 32,(int)(getY()) + 32);
						}
						
					} else {
						
						//Up or down
						if (yDiff < 0) {
							//Swipe Up
							curSword = spr_swordU;
							swordPos = new Point((int)(getX()) - 32,(int)(getY()) - 32);
							swordDest = new Point((int)(getX()) + 32,(int)(getY()) - 32);
						} else {
							//Swipe Down
							curSword = spr_swordD;
							swordPos = new Point((int)(getX()) - 32,(int)(getY()) + 32);
							swordDest = new Point((int)(getX()) + 32,(int)(getY()) + 32);
						}
						
					}
					
					atkCool = 30;
				
				}
				
			}
			
		}
		
	}

}