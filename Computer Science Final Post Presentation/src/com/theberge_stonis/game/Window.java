package com.theberge_stonis.game;

import java.awt.Point;

import com.theberge_stonis.input.MouseInput;

import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;

public class Window {
	
	/**
	 * The canvas where the game play is
	 */
	public static final int CANVAS_PLAY = 0;
	
	/**
	 * The canvas where the HUD is
	 */
	public static final int CANVAS_HUD = 1;
	
	private static final Canvas canvases[] = 
		{ new Canvas(640,480), new Canvas(160,480) };
	
	public static Point getCanvasPosition(int canvas) {
		
		Bounds canvasBounds = canvases[canvas].localToScene(canvases[canvas].getBoundsInLocal());
		
		return new Point((int)canvasBounds.getMinX(), (int)canvasBounds.getMinY());
		
	}
	
	public static Point mousePosToCanvas(int canvas) {
		
		Point canvasPos = getCanvasPosition(canvas);
		Point mousePos = new Point(MouseInput.mousePos.x,MouseInput.mousePos.y);
		
		mousePos.x = Math.max(0, Math.min(mousePos.x - canvasPos.x, getCanvasWidth(canvas)));
		mousePos.y = Math.max(0, Math.min(mousePos.y - canvasPos.y, getCanvasHeight(canvas)));
		return mousePos;
		
	}
	
	public static int getCanvasWidth(int canvas) {
		Bounds canvasBounds = canvases[canvas].getBoundsInLocal();
		return (int) canvasBounds.getWidth();
	}
	
	public static int getCanvasHeight(int canvas) {
		Bounds canvasBounds = canvases[canvas].getBoundsInLocal();
		return (int) canvasBounds.getHeight();
	}
	
	private BorderPane myPane = new BorderPane();
	
	/**
	 * @return The pane where the canvases are on
	 */
	public BorderPane getMyPane() { return myPane; }
	
	public Window() {
		
		Canvas cp = canvases[CANVAS_PLAY];
		Canvas ch = canvases[CANVAS_HUD];
		
		cp.setFocusTraversable(true);
		cp.requestFocus();
		cp.setOnKeyPressed(Game.getKeyInput());
		cp.setOnKeyReleased(Game.getKeyInput());
		cp.setOnMouseClicked(Game.getMouseInput());
		cp.setOnMouseMoved(Game.getMouseInput());
		cp.setOnMousePressed(Game.getMouseInput());
		cp.setOnMouseReleased(Game.getMouseInput());
		
		myPane.setCenter(cp);
		myPane.setLeft(ch);
		
	}

	/**
	 * Clears the given canvas
	 * 
	 * @param canvas The canvas to clear (static variable!)
	 */
	public void clearCanvas(int canvas) {
		
		Canvas c = canvases[canvas];
		c.getGraphicsContext2D().clearRect(0, 0, c.getWidth(), c.getHeight());
		
	}
	
	/**
	 * Clears all canvases the window holds
	 */
	public void clearCanvases() {
		
		for (int i = 0; i < canvases.length; i++) {
			clearCanvas(i);
		}
		
	}

	public GraphicsContext getGraphics(int canvas) {
		
		return canvases[canvas].getGraphicsContext2D();
		
	}
	
}