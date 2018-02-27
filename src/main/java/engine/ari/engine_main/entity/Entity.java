package engine.ari.engine_main.entity;

import engine.ari.engine_main.Console;
import engine.ari.engine_main.rendering.Models;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector3;

public class Entity {
    public ModelLoader modelLoader = new ObjLoader();
    protected Model characterModel = null;
    protected ModelInstance characterInstance = null;
    public Model create(String modelFile) {
        if(Gdx.files.internal(modelFile).exists()) {
            characterModel = modelLoader.loadModel(Gdx.files.internal(modelFile));
            characterInstance = Models.addCharacterModel(characterModel);
        } else {
            Console.error("File "+modelFile+" does not exist...");
            return null;
        }
        return characterModel;
    }
    public Vector3 getPosition() {
        return new Vector3(characterInstance.transform.val[0], characterInstance.transform.val[1], characterInstance.transform.val[2]);
    }
    public void setPosition(int x, int y, int z) {
        characterInstance.transform.set(new float[]{x,y,z});
    }

    /*public BoundingBox getBoundingBox() {
    //    return characterInstance.calculateBoundingBox(new BoundingBox());
    }*/
}
