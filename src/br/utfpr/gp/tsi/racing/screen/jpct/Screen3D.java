package br.utfpr.gp.tsi.racing.screen.jpct;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.utfpr.gp.tsi.racing.car.Car;
import br.utfpr.gp.tsi.racing.car.CarLoader;
import br.utfpr.gp.tsi.racing.car.ICar;
import br.utfpr.gp.tsi.racing.screen.IScreen;
import br.utfpr.gp.tsi.racing.track.Track;

import com.threed.jpct.FrameBuffer;
import com.threed.jpct.IRenderer;
import com.threed.jpct.Loader;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.util.Light;

public class Screen3D implements IScreen {
	private World world;
	private FrameBuffer buffer;
	private Map<ICar, Object3D> car3DMap;
	private Track track;
	private List<? extends ICar> carList;

	public Screen3D() {
		buffer = new FrameBuffer(800, 600, FrameBuffer.SAMPLINGMODE_NORMAL);
		buffer.disableRenderer(IRenderer.RENDERER_SOFTWARE);
		buffer.enableRenderer(IRenderer.RENDERER_OPENGL);
		world = new World();
		world.getCamera().setPosition(0, -90, -60);
		world.getCamera().lookAt(new SimpleVector(0,0,-5));
		world.getCamera().setFOVLimits(0, Float.MAX_VALUE);
		Light light = new Light(world);
		light.setPosition(new SimpleVector(0, -10, 50));
		light.setAttenuation(-1);
	}

	@Override
	public void setTrack(Track t) {
		track = t;
		TrackBuilder.build(world, track.getMatrix());
		world.buildAllObjects();
	}

	@Override
	public void setCarList(List<? extends ICar> carList) {		
		this.carList = carList;
		car3DMap = new HashMap<ICar, Object3D>();
		Object3D car3D;
		for (ICar car : carList) {
			car3D = loadCarObject(car.getName());
			car3DMap.put(car, car3D);
			world.addObject(car3D);
		}
		world.buildAllObjects();
	}
	
	private Object3D loadCarObject(String texture) {
		final Object3D obj = Loader.loadOBJ(
				ICar.class.getResourceAsStream("res/car.obj"), 
				null,
				1f)[0];
		obj.setScale(6);
		obj.rotateZ((float) Math.PI);
		obj.rotateY((float) (Math.PI/2));
		obj.rotateMesh();
		obj.translate(-50, -2, 50);
		obj.translateMesh();
		if (CarLoader.isTextureExists(texture)) {
			Object3dUtil.applyTexture(obj, "cars/"+texture+".jpg");			
		} else {
			Object3dUtil.applyTexture(obj, "car_default", Car.class.getResourceAsStream("res/textura.jpg"));
		}
		return obj;
	}
	
	@Override
	public void update() {
		updateObjects();
		drawnBuffer();
	}
	
	private void updateObjects() {
		Object3D car3D = null;
		for (ICar car : carList) {
			car3D = car3DMap.get(car);
			car3D.clearTranslation();
			car3D.translate(car.getLocation().x/10, 0, -car.getLocation().y/10);
			car3D.clearRotation();
			car3D.rotateY((float)car.getAngleRadians());
		}
	}
	
	private void drawnBuffer() {
		buffer.clear(Color.CYAN);
		world.renderScene(buffer);
		world.draw(buffer);
		buffer.update();
		buffer.displayGLOnly();
	}
	
}

