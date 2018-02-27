package engine.ari.engine_main.rendering;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import engine.ari.engine_main.entity.Character;
import engine.ari.engine_main.Console;
import engine.ari.engine_main.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class render extends Engine {
    public static Boolean paused = false;
    public static Boolean was_already_paused = false;

    //public static void setTimescale(long timescale) {
//        Gdx.graphics.getFramesPerSecond();
    //}

    private static float[] bg_color = { 0f, 0f, 0f, 0f };
    private static SpriteBatch spriteBatch;
    private static ModelBatch modelBatch;

    public void create() {
        spriteBatch = new SpriteBatch();
        modelBatch = new ModelBatch();
        instances = new ArrayList<>();
        assets = new AssetManager();
    }

    public static void setBackgroundColor(float red, float green, float blue, float alpha) {
        Array.setFloat(bg_color, 0, red);
        Array.setFloat(bg_color, 1, green);
        Array.setFloat(bg_color, 2, blue);
        Array.setFloat(bg_color, 3, alpha);
    }

    /*private Integer getPlacementTypeX(Integer placementType, Integer x) {
        if(placementType == null)
            return x;
        switch(placementType) {
            case 1: // CENTERED
                x += Gdx.graphics.getHeight() / 2;break;
            case 2: // TOP LEFT
                x += Gdx.graphics.getHeight();break;
            case 3: // TOP RIGHT
                x+=Gdx.graphics.getHeight();break;
        }
        return x;
    }
    private Integer getPlacementTypeY(Integer placementType, Integer y) {
        if(placementType == null)
            return y;
        switch(placementType) {
            case 1: // CENTERED
                y += Gdx.graphics.getWidth() / 2;break;
            case 3: // TOP RIGHT
                y+=Gdx.graphics.getWidth();break;
            case 5: // BOTTOM RIGHT
                y+=Gdx.graphics.getWidth();break;
        }
        return y;
    }*/

    private static ArrayList<Integer> modelNumbers = new ArrayList<>();
    private static HashMap<Integer, String> modelStrings = new HashMap<>();
    private static AssetManager assets;
    private static ArrayList<ModelInstance> instances;

    public static void placeModelInBatch(Integer id, String file) {
        assets.load(file, Model.class);
        modelNumbers.add(id);
        modelStrings.put(id, file);
    }

    private void doneLoading() {
        int id = modelNumbers.get(0);
        String m = modelStrings.get(id);
        Model model = assets.get(m, Model.class);
        ModelInstance instance = new ModelInstance(model);
        instance.transform.setToTranslation(new Random().nextInt(25), new Random().nextInt(25), new Random().nextInt(25));
        instances.add(instance);
        modelStrings.remove(id);
        modelNumbers.remove(id);
        Console.warn("Created new model of: " + m);
        if(modelNumbers.size() > 0)
            doneLoading();
    }

    public Character character;
    //private Sprite spotlight = new Sprite(new Texture("assets/sprites/white.png"));
    Textures textures = new Textures();
    public void render() {
        textures.touchDown();
        if (modelNumbers.size() > 0 && assets.update()) {
            Console.log(modelNumbers);
            doneLoading();
        }

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        //camera.camController.update();
        if(Character.camera != null) {
            modelBatch.begin(Character.camera.camera);
            modelBatch.render(instances, Environment.get());
            modelBatch.render(Models.modelInstances, Environment.get());
            if (Models.characterInstance != null)
                modelBatch.render(Models.characterInstance, Environment.get());
            modelBatch.end();
        }

        spriteBatch.begin();
        //spotlight.draw(spriteBatch, 0.5f);
        if(Screen_Text.screen_texts.size() > 0)
            draw_screen_text();
        if(Textures.textures.size() > 0)
            draw_textures();
        spriteBatch.end();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(Array.getFloat(bg_color, 0), Array.getFloat(bg_color, 1), Array.getFloat(bg_color, 2), Array.getFloat(bg_color, 3));
    }

    public void draw_screen_text() {
        for(Integer text_id : Screen_Text.screen_text_list) {
            String f = "assets/fonts/" + Screen_Text.screen_text_font.get(text_id);
            f = (f + (f.toLowerCase().contains("-") ? "" : "-Regular") + (f.toLowerCase().contains(".ttf") ? "" : ".ttf"));
            if(Screen_Text.screen_text_bold.containsKey(text_id) && Screen_Text.screen_text_bold.get(text_id))
                f = f.replace("-Regular", "-Bold");
            if(!Gdx.files.internal(f).exists())
                continue;
            BitmapFont bitmapFont = new BitmapFont();
            bitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);
            bitmapFont.getRegion().getTexture().getTextureData().prepare();
            bitmapFont.setColor(Screen_Text.screen_text_color.get(text_id));
            bitmapFont.getData().setScale(Screen_Text.screen_scale.get(text_id));
            bitmapFont.getData().flipped = true;
            bitmapFont.getData().fontFile = Gdx.files.internal(f);
            Label.LabelStyle style = new Label.LabelStyle(bitmapFont, Screen_Text.screen_text_color.get(text_id));
            bitmapFont.dispose();
            style.font.draw(spriteBatch, Screen_Text.screen_texts.get(text_id), Screen_Text.screen_text_x.get(text_id), Screen_Text.screen_text_y.get(text_id));
            style.font.dispose();
        }
    }

    public void draw_textures() {
        ArrayList<Integer> texture_background = new ArrayList<>();
        ArrayList<Integer> texture_foreground = new ArrayList<>();
        for (Integer texture_id : Textures.texture_depth.keySet()) {
            Integer depth = Textures.texture_depth.get(texture_id);
            if(depth == null || depth == 0)
                texture_background.add(texture_id);
            else if(depth == 1)
                texture_foreground.add(texture_id);
        }
        for(Integer id : texture_background) {
            if(!Textures.texture_active.get(id) || Textures.texture_class.get(id).texture == null || Textures.textures.get(id) == null)
                continue;
            Texture texture = Textures.textures.get(id);
            try {
                if (Textures.texture_size_w.containsKey(id) && Textures.texture_size_h.containsKey(id))
                    spriteBatch.draw(texture, Textures.texture_position_x.get(id), Textures.texture_position_y.get(id), Textures.texture_size_w.get(id), Textures.texture_size_h.get(id));
                else
                    spriteBatch.draw(texture, Textures.texture_position_x.get(id), Textures.texture_position_y.get(id));
            } catch(NullPointerException e) {

            }
        }
        for(Integer id : texture_foreground) {
            Texture texture = Textures.textures.get(id);
            if(!Textures.texture_active.get(id))
                continue;
            if(Textures.texture_size_w.containsKey(id) && Textures.texture_size_h.containsKey(id))
                spriteBatch.draw(texture, Textures.texture_position_x.get(id), Textures.texture_position_y.get(id), Textures.texture_size_w.get(id), Textures.texture_size_h.get(id));
            else
                spriteBatch.draw(texture, Textures.texture_position_x.get(id), Textures.texture_position_y.get(id));
        }
    }

    public void dispose() {
        //camera.dispose();
        modelBatch.dispose();
        Textures.dispose();
        instances.clear();
        spriteBatch.dispose();
    }
}
