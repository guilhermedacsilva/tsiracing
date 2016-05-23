package br.utfpr.gp.tsi.racing.screen.jpct;

import java.io.InputStream;

import com.threed.jpct.Loader;
import com.threed.jpct.Object3D;

import br.utfpr.gp.tsi.racing.car.Car;
import br.utfpr.gp.tsi.racing.car.CarJsFileLoader;
import br.utfpr.gp.tsi.racing.car.ICar;

public class Car3D {
	private static final String FORMAT_PATH_TEXTURE = "cars/%s.jpg";
	private static final String DEFAULT_TEXTURE_NAME = "car_default";
	private static final InputStream DEFAULT_TEXTURE_STREAM = Car.class.getResourceAsStream("res/textura.jpg");
	
	public static Object3D loadCarObject(String textureName) {
		final Object3D[] objArray = Loader.loadOBJ(
				ICar.class.getResourceAsStream("res/car.obj"), 
				null,
				1f); 
		final Object3D obj = objArray[0];
		obj.setScale(6);
		obj.rotateZ((float) Math.PI);
		obj.rotateY((float) (Math.PI/2));
		obj.rotateMesh();
		obj.translate(-50, -2, 50);
		obj.translateMesh();
		if (CarJsFileLoader.isTextureExists(textureName)) {
			Object3dUtil.applyTexture(obj, String.format(FORMAT_PATH_TEXTURE, textureName));			
		} else {
			Object3dUtil.applyTexture(obj, DEFAULT_TEXTURE_NAME, DEFAULT_TEXTURE_STREAM);
		}
		return obj;
	}
	
}
