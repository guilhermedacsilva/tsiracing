package br.utfpr.gp.tsi.racing.screen.jpct;

import br.utfpr.gp.tsi.racing.track.Track;

import com.threed.jpct.Mesh;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;

public class TrackBuilder {
	private static final String TEXTURE_TRACK_NAME = "sand";
	private static final String TEXTURE_WATER_NAME = "water";

	public static void build(World world, int[][] matrix) {
		Object3D plane = Primitives.getPlane(100, 1);
		plane.rotateX((float)Math.PI/2);
		Texture texture = new Texture(Track.class.getResourceAsStream("res/sand.jpg"));
		TextureManager.getInstance().addTexture(TEXTURE_TRACK_NAME, texture);
		plane.setTexture(TEXTURE_TRACK_NAME);
		
		Mesh planeMesh = plane.getMesh();
		planeMesh.setVertexController(new TrackVertexController(matrix), false);
		planeMesh.applyVertexController();
		planeMesh.removeVertexController();
		
		world.addObject(plane);
		
		Object3D water = Primitives.getPlane(1, 200);
		water.rotateX((float)Math.PI/2);
		texture = new Texture(Track.class.getResourceAsStream("res/water.jpg"));
		TextureManager.getInstance().addTexture(TEXTURE_WATER_NAME, texture);
		water.setTexture(TEXTURE_WATER_NAME);
		water.translate(0, -0.75f, 0);
		world.addObject(water);
	}

}
