package engine.ari.engine_main.entity;

import engine.ari.engine_main.Console;

public class Entity {
    /*
    public static ModelLoader modelLoader = new ObjLoader();
    public class EntityObject{
        public ModelInstance modelInstance;
        public AnimationController animation;
        public EntityObject(ModelInstance target) {
            modelInstance = target;
            animation = new AnimationController(target);
        }

        public Vector3 getPosition() {
            return new Vector3(modelInstance.transform.val);
        }
        public Vector3 setPosition(int x, int y, int z) {
            return new Vector3(modelInstance.transform.set(new float[]{x, y, z}).val);
        }
    }
    public class CameraObject {
        public PerspectiveCamera camera;
        public void lookAt(Vector3 vec) {
            camera.lookAt(vec);
            camera.update();
        }
        public void lookAt(int x, int y, int z) {
            camera.lookAt(x, y, z);
            camera.update();
        }
        public float setFoV(float fov) {
            camera.fieldOfView = fov;
            camera.update();
            return camera.fieldOfView;
        }
        public float getFoV() {
            return camera.fieldOfView;
        }
        public Vector3 getPosition() {
            return camera.position;
        }
        public Vector3 setPosition(int x, int y, int z) {
            Vector3 vec = camera.position.set(new float[]{x, y, z});
            camera.update();
            return vec;
        }
    }
    public EntityObject replaceExisting(String modelFile, EntityObject obj) {
        Model model;
        ModelInstance instance;
        FileHandle file = Gdx.files.internal(modelFile);
        if(file.exists()) {
            model = modelLoader.loadModel(Gdx.files.internal(modelFile));
            instance = new ModelInstance(model);
        } else {
            file = Gdx.files.external(modelFile);
            if(file.exists()) {
                model = modelLoader.loadModel(file);
                instance = new ModelInstance(model);
            } else {
                Console.error("File " + modelFile + " does not exist...");
                return obj;
            }
        }
        obj.modelInstance = instance;
        return obj;
    }
    public EntityObject create(String modelFile) {
        Model model;
        ModelInstance instance;
        FileHandle file = Gdx.files.internal(modelFile);
        if(file.exists()) {
            model = modelLoader.loadModel(Gdx.files.internal(modelFile));
            instance = new ModelInstance(model);
        } else {
            file = Gdx.files.external(modelFile);
            if(file.exists()) {
                model = modelLoader.loadModel(file);
                instance = new ModelInstance(model);
            } else {
                Console.error("File " + modelFile + " does not exist...");
                return null;
            }
        }
        return new EntityObject(instance);
    }

    /*public BoundingBox getBoundingBox() {
    //    return characterInstance.calculateBoundingBox(new BoundingBox());
    }*/
}
