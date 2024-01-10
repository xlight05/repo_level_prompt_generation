package de.exobros.exogdx.sbg;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author Exobros An Entry in a MenuState
 */
public class MenuEntry {

	/**
	 * The name/Label of the Button
	 */
	public String name;
	/**
	 * The Texture of the Button
	 */
	public TextureRegion buttonTexture;
	/**
	 * The Action which is called upon clicking the corresponding Button
	 */
	public Runnable action;
	/**
	 * The Font that os used to display the Button's label
	 */
	public BitmapFont font;

	/**
	 * Creates a MenuEntry with the specific name, Texture, callback and Font
	 * 
	 * @param name
	 *            The name/Label of the Button
	 * @param buttonTexture
	 *            Texture of the Button
	 * @param action
	 *            The Action which is called upon clicking the corresponding
	 *            Button
	 * @param font
	 *            The Font that os used to display the Button's label
	 */
	public MenuEntry(String name, TextureRegion buttonTexture, Runnable action, BitmapFont font) {
		this.name = name;
		this.buttonTexture = buttonTexture;
		this.action = action;
		this.font = font;
	}

	/**
	 * Creates a MenuEntry with the Default Texture and Font (Black Texture,
	 * white default BitmapFont)
	 * 
	 * @param name
	 *            The name/Label of the Button
	 * @param action
	 *            The Action which is called upon clicking the corresponding
	 *            Button
	 */
	public MenuEntry(String name, Runnable action) {
		this.name = name;
		this.action = action;

		Pixmap pm = new Pixmap(2, 2, Format.RGBA8888);
		pm.setColor(Color.BLACK);
		pm.fillRectangle(0, 0, 2, 2);
		buttonTexture = new TextureRegion(new Texture(pm));

		font = new BitmapFont();
	}

}
