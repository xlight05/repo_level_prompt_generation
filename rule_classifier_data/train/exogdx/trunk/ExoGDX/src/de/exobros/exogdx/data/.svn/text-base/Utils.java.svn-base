package de.exobros.exogdx.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * @author Exobros Provides useful static Methods.
 */
public class Utils {

	/**
	 * Creates a single-colored Texture with the specific size and Color
	 * 
	 * @param color
	 *            The Color you want the Texture to have
	 * @param size
	 *            The Size you want the Texture to have (height and width)
	 * @return The created Texture
	 */
	public static Texture getColoredTexture(Color color, int size) {
		Pixmap map = new Pixmap(size, size, Format.RGBA8888);
		map.setColor(color);
		map.fill();
		return new Texture(map);
	}

	/**
	 * Creates a single-colored 2x2 px Texture with the specific Color
	 * 
	 * @param color
	 *            The Color you want the Texture to have
	 * @return The Created Texture
	 */
	public static Texture getColoredTexture(Color color) {
		return getColoredTexture(color, 2);
	}
	
	/**
	 * Creates a standard TextButton with a default White BitmapFont and a Black Texture
	 * @param text The Text to be displayed in the Button
	 * @return The generated Button
	 */
	public static TextButton getBasicButton(String text){
		TextButtonStyle style = new TextButtonStyle();
		TextureRegion tex = new TextureRegion(Utils.getColoredTexture(Color.BLACK));
		style.up = new TextureRegionDrawable(tex.split(tex.getRegionWidth(), tex.getRegionHeight() / 2)[0][0]);
		style.down = new TextureRegionDrawable(tex.split(tex.getRegionWidth(), tex.getRegionHeight() / 2)[1][0]);
		style.font = new BitmapFont();
		
		return new TextButton(text, style);
	}

}
