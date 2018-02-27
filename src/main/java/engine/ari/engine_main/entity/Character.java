package engine.ari.engine_main.entity;

import com.badlogic.gdx.math.Matrix4;
import engine.ari.engine_main.Console;
import engine.ari.engine_main.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;

public class Character extends Entity {
    public static PerspectiveCamera camera;
    public static CameraInputController camController;
    private static Integer health = 100;
    private static Integer oldHealth = health;
    private Engine Engine = new Engine();
    @Override
    public Model create(String modelFile) {
        if(Gdx.files.internal(modelFile).exists() || Gdx.files.external(modelFile).exists()) {
            if (camera == null) {
                camera = new PerspectiveCamera(70, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                camController = new CameraInputController(camera);
                camera.position.set(new Vector3(10, 10, 10));
                camera.fieldOfView = 75;
                camera.near = 1f;
                camera.far = 300f;
                camera.update();
            }
        }
        return super.create(modelFile);
    }
    public void create() { // nullable model
        if (camera == null) {
            camera = new PerspectiveCamera(70, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            camController = new CameraInputController(camera);
            camera.position.set(new Vector3(10, 10, 10));
            camera.fieldOfView = 75;
            camera.near = 1f;
            camera.far = 300f;
            camera.update();
        }
    }
    public Model replace(String modelFile) {
        return create(modelFile);
    }

    public void setFov(int fov) {
        camera.fieldOfView = fov;
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
        if(characterModel != null) {
            if(!health.equals(oldHealth) && damageRunnable != null) {
                damageRunnable.run();
                oldHealth = health;
            }
            //camera.position.set(getPosition());
            camera.lookAt(getPosition());
            camera.position.set(new Vector3(10, 10, 10));
            camera.update();
        }
    }
}
