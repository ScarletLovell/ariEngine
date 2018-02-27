package engine.ari.engine_main.map;

import engine.ari.engine_main.modding.javascript.js_engine;
import engine.ari.engine_main.rendering.Textures;

public class Map {
    public Editor Editor = new Editor();
    public void Load(String mapFile) {

    }

    public class Editor extends js_engine
    {
        private Textures Textures;
        /*Textures.textureClass t = Textures.addTexture(5001, "assets/sprites/toolbar.png")
                .setVisible(false)
                .setSize(windowWidth, 5)
                .setPosition(0, 5);*/
        private Boolean open = false;
        public Editor Open() {
            if(this.open) {
                return null;
            }
            this.open = true;
            return this;
        }
        public Editor Close() {
            if(!this.open) {

                return null;
            }
            this.open = false;
            return this;
        }
    }
}
