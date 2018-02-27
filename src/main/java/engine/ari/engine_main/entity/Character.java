package engine.ari.engine_main.entity;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import engine.ari.engine_main.Console;
import engine.ari.engine_main.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;

public class Character extends Entity {
    public static Entity.EntityObject character;
    public static Entity.CameraObject camera;
    public static CameraInputController camController;
    private static Integer health = 100;
    private static Integer oldHealth = health;
    private Engine Engine = new Engine();

    private Entity entity = new Entity();

    public Character setModel(String modelFile) {
        if(character != null) {
            Console.warn("Replacing already existing character model...");
            entity.replaceExisting(modelFile, character);
            return this;
        }
        character = new Entity().create(modelFile);
        return this;
    }

    public CameraObject createCamera() {
        if (camera != null) {
            Console.warn("Creating a new Camera and overwriting the old...");
            camera.camera = null;
            camera = null;
        }
        camera = new Entity.CameraObject();
        camera.camera = new PerspectiveCamera(70, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camController = new CameraInputController(camera.camera);
        camera.setPosition(10, 10, 10);
        camera.camera.near = 1f;
        camera.camera.far = 300f;
        camera.camera.update();
        return camera;
    }

    public void damage(Integer amount) {
        if(Engine.getPaused())
            return;
        health-=amount;
        healthChange();
    }
    public void heal(Integer amount) {
        if(Engine.getPaused())
            return;
        health=oldHealth+=amount;
        healthChange();
    }
    public void setHealth(Integer newHealth) {
        if(Engine.getPaused())
            return;
        health = oldHealth = newHealth;
        healthChange();
    }
    public Integer getHealth() {
        return health;
    }

    private static Runnable damageRunnable = null;
    public void onDamage(Runnable runnable) {
        if(Engine.getPaused())
            return;
        if(damageRunnable != null)
            Console.warn("Overwriting previous runnable of onDamage...");
        damageRunnable = runnable;
    }
    private static Runnable healthChangeRunnable = null;
    public void onHealthChanged(Runnable runnable) {
        if(Engine.getPaused())
            return;
        if(healthChangeRunnable != null)
            Console.warn("Overwriting previous runnable of onHealthChanged...");
        healthChangeRunnable = runnable;
    }
    private void healthChange() {
        if(healthChangeRunnable != null)
            healthChangeRunnable.run();
    }
    public void render() {
        if(Engine.getPaused())
            return;
        if(character == null)
            return;
        if(!health.equals(oldHealth) && damageRunnable != null) {
            damageRunnable.run();
            oldHealth = health;
        }
    }
}
