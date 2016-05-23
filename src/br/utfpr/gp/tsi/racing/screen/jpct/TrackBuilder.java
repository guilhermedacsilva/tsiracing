package br.utfpr.gp.tsi.racing.screen.jpct;

import br.utfpr.gp.tsi.racing.track.Track;

import com.threed.jpct.Mesh;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;

public class TrackBuilder {

	public static void build(World world, int[][] matrix) {
		Object3D plane = Primitives.getPlane(100, 1);
		plane.rotateX((float)Math.PI/2);
		Texture texture = new Texture(Track.class.getResourceAsStream("res/sand.jpg"));
		TextureManager.getInstance().addTexture("sand", texture);
		plane.setTexture("sand");
		
		Mesh planeMesh = plane.getMesh();
		planeMesh.setVertexController(new TrackVertexController(matrix), false);
		planeMesh.applyVertexController();
		planeMesh.removeVertexController();
		
		world.addObject(plane);
		
		Object3D water = Primitives.getPlane(1, 200);
		water.rotateX((float)Math.PI/2);
		texture = new Texture(Track.class.getResourceAsStream("res/water.jpg"));
		TextureManager.getInstance().addTexture("water", texture);
		water.setTexture("water");
		water.translate(0, -0.75f, 0);
		world.addObject(water);
	}

}
