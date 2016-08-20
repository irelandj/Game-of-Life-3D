package com.jakeireland.life3d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Life extends ApplicationAdapter {
	public PerspectiveCamera cam;
    public Model[][][] models;
    public ModelInstance[][][] modelInstances;
    public ModelBatch modelBatch;
    public ModelBuilder modelBuilder;
    public Environment environment;
    public CameraInputController camController;
	int xLength;
	int yLength;
    int zLength;
	int tileSize;
	Grid grid;

	public Life(int tileSize, int xLength, int yLength, int zLength) {
		super();
		this.tileSize = tileSize;
		this.xLength = 10;
		this.yLength = 10;
        this.zLength = 10;
	}
	
	@Override
	public void create () {
	    modelBatch = new ModelBatch();
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(20f, 5f, 20f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        modelBuilder = new ModelBuilder();
        models = new Model[10][10][10];
        modelInstances = new ModelInstance[10][10][10];
        for (int z = 0; z < 10; z++) {
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    models[x][y][z] = modelBuilder.createBox(0.5f, 0.5f, 0.5f,
                            new Material(ColorAttribute.createDiffuse(Color.YELLOW)),
                            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                    modelInstances[x][y][z] = new ModelInstance(models[x][y][z]);
                    modelInstances[x][y][z].transform.translate(x, y, z);
                }
            }
        }
		grid = new Grid(xLength, yLength, zLength);
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
	}

	@Override
	public void render () {
        camController.update();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		modelBatch.begin(cam);
		grid.setCoords(grid.modulate());
		coordsToBase();
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }sssssssssssss
        for (int z = 0; z < 10; z++) {
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    modelBatch.render(modelInstances[x][y][z], environment);
                }
            }
        }
		System.out.println(Gdx.graphics.getFramesPerSecond());
		modelBatch.end();
	}
	
	@Override
	public void dispose () {
		modelBatch.dispose();
	}

	public void coordsToBase() {
	    for (int z = 0; z < 10; z++) {
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    modelInstances[x][y][z].materials.get(0).set(new ColorAttribute(ColorAttribute.Diffuse,
                            (grid.getCoords()[x][y][z].isAlive()) ? Color.GREEN : Color.YELLOW));
                }
            }
        }
	}
}