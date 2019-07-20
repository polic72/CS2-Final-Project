package com.theberge_stonis.entity;

import com.theberge_stonis.game.Element;
import com.theberge_stonis.game.Game;
import com.theberge_stonis.game.Sprite;
import com.theberge_stonis.game.Window;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

/**
 * An on-screen creature with a hitbox and position.
 * 
 * @author Conner Theberge
 *
 */
public abstract class Entity extends Element {
	
	private boolean canMove = true;
	protected void disableMovement() { canMove = false; }
	protected void enableMovement() { canMove = true; }
	
	private Rectangle hitbox = new Rectangle( 32, 32 );

	double xPrev = 0;
	double yPrev = 0;
	public double getXPrev() { return xPrev; }
	public double getYPrev() { return yPrev; }

	double xVel = 0;
	double yVel = 0;
	public void setVelocity( double dx, double dy ) { xVel = dx; yVel = dy; }
	public void setVelX( double dx ) { xVel = dx; }
	public void setVelY( double dy ) { yVel = dy; }
	
	private Sprite sprite = null;
	public Sprite getSprite() { return sprite; }
	public void setSprite( Sprite sprite ) { this.sprite = sprite; }
	public void drawSprite( GraphicsContext g ) {
		
		sprite.draw(g, (int)getX(), (int)getY());
		
	}
	
	private boolean solid = false;
	public boolean isSolid() { return solid; }
	
	double posX = 0;
	double posY = 0;
	
	public double getX() { return posX; }
	public void setX( double x ) { this.posX = x; getHitBox().setX( x ); }
	public double getY() { return posY; }
	public void setY( double y ) { this.posY = y; getHitBox().setY( y ); }
	
	public Rectangle getHitBox() { return hitbox; }
	public void setHitBox( Rectangle hitbox ) { this.hitbox = hitbox; }
	
	private int hp = 0;
	public int getHP() { return hp; }
	
	private int hurtDelay = 0;
	
	public void hurt( int damage ) {
		
		if ( hurtDelay == 0 ) {
			
			hp -= damage;
			hurtDelay = 30;
			
		}
		
		if ( hp <= 0 ) {
			
			Game.deactivateElement( this );
			
		}
		
	}
	
	@Override
	public void update() {
		
		if ( canMove ) {
			
			double nextX = getX() + xVel;
			double nextY = getY() + yVel;
			
			if ( nextX > 0 && nextX < Game.getWindow().getCanvasWidth( Window.CANVAS_PLAY ) &&
					nextY > 0 && nextY < Game.getWindow().getCanvasHeight( Window.CANVAS_PLAY ) ) {
				
				xPrev = getX(); yPrev = getY();
				
				setX( nextX ); setY( nextY );
				
			}
			
		}
		
		if ( hurtDelay > 0 ) {
			
			hurtDelay--;
			
		}
		
	}
	
	public Entity( int x, int y, int w, int h, boolean solid ) {
		this.solid = solid;
		setX( x );
		setY( y );
		hitbox.resize( w, h );
	}
	
	public Entity( int x, int y, int w, int h, boolean solid, int hp ) {
		
		this.solid = solid;
		setX( x );
		setY( y );
		hitbox.resize( w, h );
		this.hp = hp;
		
	}
	
	public boolean isCollidingWith( Rectangle hitbox2 ) {
		
		return hitbox.intersects( hitbox2.getBoundsInLocal() );
		
	}

	public static boolean isCollidingWith( Rectangle hitbox1, Rectangle hitbox2 ) {
		
		return hitbox1.intersects( hitbox2.getBoundsInLocal() );
		
	}
	
}