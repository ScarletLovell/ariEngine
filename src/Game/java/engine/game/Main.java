package engine.game;

import engine.ari.engine_main.modding.javascript.js_engine;
import engine.ari.engine_main.entity.Character;
import engine.ari.engine_main.rendering.Screen_Text;
import engine.ari.engine_main.rendering.Textures;
import engine.ari.engine_main.rendering.Models;
//import engine.ari.engine_main.rendering.Textures.textureClass;
import engine.ari.engine_main.rendering.Screen_Text.screenTextClass;
import engine.ari.engine_main.modding.javascript.javascript.js_timer;
import engine.ari.engine_main.Console;

public class Main extends js_engine {
    /*
    private Character character = new Character();
    private Models models = new Models();
    private Textures textures = new Textures();
    private Screen_Text screen_text = new Screen_Text();

    public Main() {
        if(null == null)
            return;
        Float[] f = {0f, 0f, 0f};
        //models.create(f, 500f, 0.5f, 30f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), null);
        character.create("assets/models/untitled.obj");
        //character.setFov(95);
        createHealth();
        Console.log("Game is up");
        render();
    }

    public void createHealth() {
        textures.addTexture(1, "assets/sprites/black.png")
                .setActive(true)
                .setPosition(Window.getWidth()-127, Window.getHeight()-28)
                .setSize(104, 20)
                .setDepth(0);
        textureClass healthBar = textures.addTexture(0)
                .setTexture("assets/sprites/green.png")
                .setActive(true)
                .setPosition(Window.getWidth()-125, Window.getHeight()-25)
                .setSize(100, 15)
                .setDepth(1);
        screenTextClass healthText = screen_text.addScreenText(0, "DroidSerif")
                .setColor(Color.WHITE)
                .setScale(20)
                .setText("Test")
                .setBold(true)
                .setPosition(Window.getWidth()-125-50, Window.getHeight()-10);

        /*character.onHealthChanged(() -> {
            if(character.getHealth() > 100) {
                healthBar.setSize(100, 15);
                return;
            } else if(character.getHealth() < 0) {
                healthBar.setSize(0, 15);
                return;
            }
            healthText.setText(character.getHealth() + "%");
            healthBar.setSize(character.getHealth(), 15);
        });
    }

    public void render() {
        js_timer timer = new js_timer();
        timer.start(25, 0, () -> {
            character.damage(1);
        });
    }*/
}
