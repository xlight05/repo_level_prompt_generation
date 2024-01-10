package de.exobros.exogdx.sbg;

import com.badlogic.gdx.Screen;

public interface GameState extends Screen {
		
	public void onCreate(StateBasedGame sbg);
	
	public void onEnter();

	public void onLeave();
	
}
