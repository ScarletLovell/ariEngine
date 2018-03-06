package engine.ari.engine_main.rendering;

import engine.ari.engine_main.Console;
import engine.ari.engine_main.modding.javascript.js_engine;

import java.util.ArrayList;
import java.util.HashMap;

public class Models {
    /*public static ArrayList<ModelInstance> modelInstances = new ArrayList<>();
    public static HashMap<ModelInstance, Environment> model_environment = new HashMap<>();
    public ModelCreation createBox() {
        ModelCreation creation = new ModelCreation();
        creation.modelBuilder = new ModelBuilder();
        return creation;
    }
    public class ModelCreation {
        public ModelBuilder modelBuilder = null;
        private Float width = null;
        private Float height = null;
        private Float depth = null;
        private Material material = null;
        private Environment env = null;
        public ModelCreation setWidth(Float f) {
            Console.warn(f);
            width = f;
            return this;
        }
        public ModelCreation setHeight(Float f) {
            height = f;
            return this;
        }
        public ModelCreation setDepth(Float f) {
            depth = f;
            return this;
        }
        public ModelCreation setMaterial(Material m) {
            material = m;
            return this;
        }
        public ModelCreation setEnvironment(Environment e) {
            env = e;
            return this;
        }
        public ModelInstance build() {
            if(width == null) {
                Console.error("Missing width field on model creation");
                return null;
            }
            if(height == null) {
                Console.error("Missing height field on model creation");
                return null;
            }
            if(depth == null) {
                Console.error("Missing depth field on model creation");
                return null;
            }
            if(material == null) {
                Console.error("Missing material field on model creation");
                return null;
            }
            try {
                Console.warn(width, height, depth, material);
                Model model = modelBuilder.createBox(width, height, depth, material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                ModelInstance instance = new ModelInstance(model);
                instance.transform.translate(0, 0, 0);
                if (env != null)
                    model_environment.put(instance, env);
                modelInstances.add(instance);
                return instance;
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public class model {

    }
    public static ModelInstance characterInstance = null;
    public static ModelInstance addCharacterModel(Model model) {
        characterInstance = new ModelInstance(model);
        return characterInstance;
    }*/
}
