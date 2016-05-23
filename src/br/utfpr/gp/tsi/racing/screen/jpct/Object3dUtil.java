package br.utfpr.gp.tsi.racing.screen.jpct;

import java.awt.Color;
import java.io.InputStream;

import com.threed.jpct.Object3D;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;

public class Object3dUtil {

	/**
	 * @param name use extension
	 */
	public static void applyTexture(Object3D obj, String textureName) {
		if (!TextureManager.getInstance().containsTexture(textureName)) {
			Texture texture = new Texture(textureName);
			TextureManager.getInstance().addTexture(textureName, texture);
		}
		obj.setTexture(textureName);
	}
	public static void applyTexture(Object3D obj, String textureName, InputStream textureStream) {
		if (!TextureManager.getInstance().containsTexture(textureName)) {
			Texture texture = new Texture(textureStream);
			TextureManager.getInstance().addTexture(textureName, texture);
		}
		obj.setTexture(textureName);
	}
	public static void applyTexture(Object3D obj, String textureName, Color color) {
		if (!TextureManager.getInstance().containsTexture(textureName)) {
			Texture texture = new Texture(2,2,color);
			TextureManager.getInstance().addTexture(textureName, texture);
		}
		obj.setTexture(textureName);
	}
	public static Texture loadTexture(String name) {
		return new Texture(name);
	}

}
