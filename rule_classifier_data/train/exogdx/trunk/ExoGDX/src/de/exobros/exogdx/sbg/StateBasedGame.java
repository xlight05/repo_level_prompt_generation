package de.exobros.exogdx.sbg;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

public abstract class StateBasedGame extends Game {

	private AssetManager assetManager = new AssetManager();

	private HashMap<String, GameState> stateList = new HashMap<String, GameState>();

	protected LoadingScreen loadingScreen;

	private String firstState;
	private String currentState;

	@Override
	public void create() {
		this.initStateList();
		this.loadResources(assetManager);
		this.loadingScreen = new LoadingScreen(this, this.firstState);
		this.setScreen(loadingScreen);
	}

	public void addState(String id, GameState state) {
		this.stateList.put(id, state);
		if (firstState == null) {
			firstState = id;
		}
	}

	public void enterState(String id) {
		if (stateList.containsKey(id)) {
			if (currentState != null) {
				this.stateList.get(currentState).onLeave();
			}
			this.stateList.get(id).onEnter();
			this.setScreen(stateList.get(id));
			this.currentState = id;

		} else {
			try {
				throw new Exception("State \"" + id + "\" not found!");
			} catch (Exception e) {
				e.printStackTrace();
				Gdx.app.exit();
			}
		}

	}

	public void initStates() {
		for (Entry<String, GameState> e : stateList.entrySet()) {
			e.getValue().onCreate(this);
		}

	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	protected HashMap<String, GameState> getScreenList() {
		return stateList;
	}

	public abstract void initStateList();

	public abstract void loadResources(AssetManager am);

}
