package engine.ari.engine_main.rendering;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Widget {
    public static List<WidgetInterface> interfaces = new ArrayList<>();
    public class WidgetInterface {
        private Skin skin = new Skin();
        public void createLabel(Object label) {

        }
        public void createTextButton(String message) {

        }
        public void createCheckBox() {
            //CheckBox checkBox = new CheckBox();
        }
    }
    public void create(Integer id) {
        VerticalGroup vg = new VerticalGroup().space(3).pad(5).fill();
    }
}
