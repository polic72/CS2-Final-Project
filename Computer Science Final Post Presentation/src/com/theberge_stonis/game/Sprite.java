package com.theberge_stonis.game;

import java.awt.Point;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Sprite {

	Point origin;
	public Point getOrigin() { return origin; }
	
	double angle = 0;
	public double getAngle() { return angle; }
	
	Image baseImg;
	Image curImg;
	
	public Sprite(Image img, int originX, int originY) {
		
		this.baseImg = img;
		this.curImg = this.baseImg;
		
		origin = new Point(originX, originY);
		
	}
	
	public Sprite(Image img) {
		
		this.baseImg = img;
		this.curImg = this.baseImg;
		
		origin = new Point((int)(img.getWidth() / 2), (int)(img.getHeight() / 2));
		
	}
	
	public void draw(GraphicsContext g, int x, int y) {
		
		g.drawImage(curImg, x - origin.x, y - origin.y);
		
	}
	
	public void rotateImage(double angle) {

		if (this.angle == angle) { return; }
		
		this.angle = angle;
		
		ImageView iv = new ImageView(baseImg);
		
		iv.setRotate(angle);
		
		SnapshotParameters p = new SnapshotParameters();
		
		p.setFill(Color.TRANSPARENT);
		
		curImg = iv.snapshot(p, null);
		
	}
	
	public void resetRotation() {
		
		rotateImage(0);
		
	}
	
}