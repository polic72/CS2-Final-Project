package com.theberge_stonis.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.theberge_stonis.entity.*;
import com.theberge_stonis.input.KeyInput;
import com.theberge_stonis.input.KeyListener;
import com.theberge_stonis.input.MouseInput;
import com.theberge_stonis.input.MouseListener;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The main game class.
 * 
 * This class handles all entities, updating, drawing, and the
 * application itself. Contains a main function to launch the
 * application.
 * 
 * @author Conner Theberge
 *
 */
public final class Game extends Application {
	
	public static void main(String args[]) {
		
		launch(args);
		
	}
	
	private static Player player;
	public static Player getPlayer() { return player; }
	
	private static Room mainRoom = new Room();
	public static void regen() { mainRoom.generate( System.currentTimeMillis() ); }
	public static Room getRoom() { return mainRoom; }
	
	public static final String RESOURCE_PATH = "File:Resources/";
	
	private static LinkedList< KeyListener > allKeyListeners = new LinkedList< KeyListener >();
	
	public static LinkedList< KeyListener > getKeyListeners() { return allKeyListeners; }
	
	private static LinkedList< MouseListener > allMouseListeners = new LinkedList< MouseListener >();
	
	public static LinkedList< MouseListener > getMouseListeners() { return allMouseListeners; }

	public static void addKeyListener( KeyListener kl ) { allKeyListeners.add( kl ); }
	
	public static void addMouseListener( MouseListener ml ) { allMouseListeners.add( ml ); }
	
	/**
	 * The instance that handles event-driven key input
	 */
	public static KeyInput keyIn = new KeyInput();
	
	public static KeyInput getKeyInput() { return keyIn; }
	
	/**
	 * The instance that handles event-driven mouse input
	 */
	public static MouseInput mouseIn = new MouseInput();
	
	public static MouseInput getMouseInput() { return mouseIn; }
	
	private static Map< Class< ? extends Element >, LinkedList< Element > > allElements = new HashMap<>();
	
	public static < T extends Element > LinkedList< T > getObjectList( Class< T > objectClass ) {
		
		LinkedList< T > objects = new LinkedList<>();
		
		if ( allElements.containsKey( objectClass ) ) {
			
			for( Element e : allElements.get( objectClass ) ) {
			
				objects.add( objectClass.cast( e ) );
			
			}
			
		}
		
		return objects;
		
	}
	
	private static < T extends Element > void addObject( Class< ? extends Element > objClass, T object ) {
		
		if ( !allElements.containsKey( objClass ) ) {
			allElements.put( objClass, new LinkedList< Element >() );
		}

		allElements.get( objClass ).add( object );
		
	}

	private static < T extends Element > void deleteObject( Class< ? extends Element > objClass, T object ) {
		
		if ( allElements.get( objClass ).contains( object ) ) {
			
			allElements.get( objClass ).remove( object );
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static < T extends Element > void activateElement( T object ) {
		
		Class< ? extends Element > curClass = object.getClass();
		
		while( curClass != Element.class ) {
			
			addObject( curClass, object );
			
			curClass = ( Class<? extends Element> )curClass.getSuperclass();
			
		}

		addObject( Element.class, object );
		
	}
	
	@SuppressWarnings("unchecked")
	public static < T extends Element > void deactivateElement( T object ) {
		
		Class< ? extends Element > curClass = object.getClass();
		
		while( curClass != Element.class ) {
			
			deleteObject( curClass, object );
			
			curClass = ( Class<? extends Element> )curClass.getSuperclass();
			
		}

		deleteObject( Element.class, object );
		
		object.delete();
		
	}
	
	private static Window mainWindow = new Window();
	public static Window getWindow() { return mainWindow; }
	
	public static void pauseLoop() { gameLoop.stop(); }
	
	public static void resumeLoop() { gameLoop.start(); }
	
	@Override
	public void start(Stage arg0) throws Exception {
		
		//Init
		Group g = new Group();
		
		Scene scene = new Scene(g);
		
		/*
		 * This object gets added to lists automatically in their constructor.
		 * However, I should change this to prevent warnings from being a problem.
		 */
		player = new Player(200,200,32,32,100);
		activateElement(player);
		
		g.getChildren().add(mainWindow.getMyPane());
		
		arg0.setScene(scene);
		
		arg0.show();
		
		mainRoom.generate();
		
		//Game Loop Run
		gameLoop.start();
		
	}
	
	//GAME LOOP
	
	private static AnimationTimer gameLoop = new AnimationTimer() {

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
			
			mainRoom.draw(Game.getWindow().getGraphics(Window.CANVAS_PLAY));
			
			for (Element e : Game.getObjectList(Element.class)) { e.draw(); }
		
		}

	};
	
	//GAME LOOP END
	
}