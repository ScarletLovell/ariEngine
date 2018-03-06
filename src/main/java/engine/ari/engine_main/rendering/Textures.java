package engine.ari.engine_main.rendering;

import engine.ari.engine_main.Engine;
import engine.ari.engine_main.modding.javascript.js_engine;
import engine.ari.engine_main.Console;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import javax.imageio.ImageIO;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import org.lwjgl.stb.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class Textures {
    public static ArrayList<Integer> texture_list = new ArrayList<>();
    public static HashMap<Integer, textureClass> textures = new HashMap<>();
    public static HashMap<Integer, Integer> texture_depth = new HashMap<>();
    public static HashMap<Integer, Boolean> texture_active = new HashMap<>();
    public static HashMap<Integer, Boolean> texture_visible = new HashMap<>();
    public static HashMap<Integer, Integer> texture_position_x = new HashMap<>();
    public static HashMap<Integer, Integer> texture_position_y = new HashMap<>();
    public static HashMap<Integer, Integer> texture_size_w = new HashMap<>();
    public static HashMap<Integer, Integer> texture_size_h = new HashMap<>();
    //public static HashMap<Integer, textureClass> texture_class = new HashMap<>();

    /*private textureClass getTextureToCursor(int x, int y) {
        int h = Engine.window.getHeight();
        int w = Engine.window.getWidth();
        y = (h - y) * (h / h);
        for(Integer i : texture_list) {
            if(!texture_active.get(i))
                continue;
            Texture texture = textures.get(i);
            try {
                Integer x0 = texture_position_x.get(i);
                Integer y0 = texture_position_y.get(i);
                Integer w = texture_size_w.get(i);
                Integer h = texture_size_h.get(i);
                Integer x1 = x0 + w;
                Integer y1 = y0 + h;
                if (x >= x0 && x <= x1 && y >= y0 && y <= y1)
                    return texture_class.get(i);
            } catch(Exception e) {
                return null;
            }
        }
        return null;
    }*/

    /*public static HashMap<textureClass, Runnable> clickRelease = new HashMap<>();
    public static HashMap<textureClass, Runnable> hoverStop = new HashMap<>();
    public void touchDown () {
        textureClass c;
        if ((c = getTextureToCursor(Gdx.input.getX(), Gdx.input.getY())) != null) {
            if (!hoverStop.isEmpty()) {
                for(Map.Entry<textureClass, Runnable> entry : hoverStop.entrySet()) {
                    textureClass textureClass = entry.getKey();
                    if(textureClass.equals(c))
                        continue;
                    textureClass.isBeingHovered = false;
                    Runnable runnable = entry.getValue();
                    runnable.run();
                    hoverStop.remove(textureClass);
                }
            }
            if(!c.isBeingHovered && c.hoverOn != null) {
                if(c.hoverOff != null && !hoverStop.containsKey(c))
                    hoverStop.put(c, c.hoverOff);
                c.hoverOn.run();
                c.isBeingHovered = true;
            }
            if(c.hoverCont != null)
                c.hoverCont.run();
            if (!c.isBeingClicked && Gdx.input.isTouched() && c.clickOn != null) {
                Console.warn("hi");
                if(c.clickOff != null && !clickRelease.containsKey(c))
                    clickRelease.put(c, c.clickOff);
                c.clickOn.run();
                c.isBeingClicked = true;
            } else if(Gdx.input.isTouched() && c.clickCont != null) {
                c.clickCont.run();
                c.isBeingClicked = true;
            } else if(c.isBeingClicked && !Gdx.input.isTouched() && c.clickOff != null) {
                c.clickOff.run();
                c.isBeingClicked = false;
            }
        } else if (!hoverStop.isEmpty()) {
            for(Map.Entry<textureClass, Runnable> entry : hoverStop.entrySet()) {
                textureClass textureClass = entry.getKey();
                textureClass.isBeingHovered = false;
                Runnable runnable = entry.getValue();
                runnable.run();
                hoverStop.remove(textureClass);
            }
        }
        if(!Gdx.input.isTouched()) {
            if (!clickRelease.isEmpty()) {
                for(Map.Entry<textureClass, Runnable> entry : clickRelease.entrySet()) {
                    textureClass textureClass = entry.getKey();
                    textureClass.isBeingClicked = false;
                    Runnable runnable = entry.getValue();
                    runnable.run();
                }
                clickRelease.clear();
            }
        }
    }*/
    public class textureClass extends js_engine {
        private Textures Textures;
        public String texture_location = null;
        public Integer texture = null;
        public BufferedImage image;
        public Integer id = null;
        public Boolean visible = null;

        public Runnable clickOff = null;
        public Runnable clickOn = null;
        public Runnable clickCont = null;
        public Boolean isBeingClicked = false;

        public Runnable hoverOn = null;
        public Runnable hoverOff = null;
        public Runnable hoverCont = null;
        public Boolean isBeingHovered = false;
        public Integer makeTexture() {
            if(this.texture != null) {
                Console.error("You literally don't need to do this, ever. Please avoid using this. [textureClass.makeTexture()]");
                return null;
            }
            Integer textureID;

            // opengl is fucking confusing
            int[] pixels = new int[image.getWidth() * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

            ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
            buffer.order(ByteOrder.nativeOrder());
            for(int y = 0; y < image.getHeight(); y++)
            {
                for(int x = 0; x < image.getWidth(); x++)
                {
                    int pixel = pixels[y * image.getWidth() + x];
                    buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                    buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                    buffer.put((byte) (pixel & 0xFF));               // Blue component
                    buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
                }
            }
            buffer.flip();

            textureID = glGenTextures();
            glBindTexture(GL11.GL_TEXTURE_2D, textureID);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
            return textureID;
        }
        public textureClass onClick(Boolean released, Runnable runnable) {
            if(released != null) {
                if (!released) clickOff = runnable;
                else clickOn = runnable;
            } else clickCont = runnable;
            return this;
        }
        public textureClass onHover(Boolean hovering, Runnable runnable) {
            if(hovering != null) {
                if (!hovering) hoverOff = runnable;
                else hoverOn = runnable;
            } else hoverCont = runnable;
            return this;
        }
        public textureClass setClickType(String type) {
            //if(type.equalsIgnoreCase("toggle") || type.equalsIgnoreCase(""))
            return this;
        }
        public textureClass get() {
            return this;
        }
        public textureClass setTexture(String location) {
            /*if(!Gdx.files.internal(location).exists()) {
                Console.error("Attempted to create texture, but it's file doesn't exist:", location);
                return this;
            }
            Texture old = texture;
            texture_location = location;
            Texture newTexture = this.texture = new Texture(location);
            textures.put(id, newTexture);
            old.dispose();*/
            return this;
        }
        public Integer getTexture() {
            return texture;
        }
        public textureClass setPosition(Integer x, Integer y) {
            texture_position_x.put(id, x);
            texture_position_y.put(id, y);
            return this;
        }
        public textureClass setActive(Boolean b) {
            texture_active.put(id, b);
            return this;
        }
        public textureClass setDepth(Integer depth) {
            if(depth > 1 || depth < 0) {
                Console.error("Depth must be 0-1");
                return this;
            }
            texture_depth.put(id, depth);
            return this;
        }
        public int getDepth() {
            return texture_depth.get(id);
        }
        public textureClass setVisible(Boolean b) {
            this.visible = b;
            return this;
        }
        public textureClass setSize(Integer w, Integer h) {
            texture_size_w.put(id, w);
            texture_size_h.put(id, h);
            return this;
        }
        public Integer getId() {
            return id;
        }
        public textureClass onCreate(Runnable runnable) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runnable.run();
                    this.cancel();
                }
            }, 150);
            return this;
        }
        public textureClass clone(Object... objects) {
            if(texture_location == null) {
                Console.error("Attempted to clone a texture, but the texture is null");
                return null;
            }
            if(!(objects[0] instanceof Integer)) {
                Console.error("Attempted to clone a texture without an Integer (id)");
                return null;
            }
            if(texture_active.containsKey(this.id))
                texture_active.put(id, texture_active.get(this.id));
            if(texture_depth.containsKey(this.id))
                texture_depth.put(id, texture_depth.get(this.id));
            if(texture_size_w.containsKey(this.id))
                texture_size_w.put(id, texture_size_w.get(this.id));
            if(texture_size_h.containsKey(this.id))
                texture_size_h.put(id, texture_size_h.get(this.id));
            textureClass t;
            File file = new File((String)objects[1]);
            if(objects[1] instanceof String && file.exists())
                t = addTexture((Integer)objects[0], (String)objects[1]);
            else
                t = Textures.addTexture((Integer)objects[0], texture_location);
            if(texture_position_x.containsKey(texture))
                texture_position_x.put(id, texture_position_x.get(texture));
            if(texture_position_y.containsKey(texture))
                texture_position_y.put(id, texture_position_y.get(texture));
            return t;
        }
        public void delete() {
            texture_active.remove(id);
            texture_depth.remove(id);
            texture_position_y.remove(texture);
            texture_position_x.remove(texture);
            textures.remove(id);
            texture_list.remove(id);
            texture_size_h.remove(id);
            texture_size_w.remove(id);
            //this.texture.dispose();
            this.texture_location = null;
            this.id = null;
        }
    }

    public Integer getCount() {
        return texture_list.size();
    }
    public String getList() {
        String s = "\n";
        for(Map.Entry<Integer, textureClass> entry : textures.entrySet()) {
            textureClass textureClass = entry.getValue();
            Integer texture = textureClass.id;
            s = s + "Id " + entry.getKey() + (texture == null ? ": NULL" : texture);//: " + "W"+texture.getWidth() + ", H"+texture.getHeight() + ", FORMAT "+texture.getTextureData().getFormat()) + "\n";
        }
        return s;
    }
    /*public textureClass addTexture(Integer texture_id) {
        if(!textures.containsKey(texture_id)) {
            Console.log("Created a new null texture with the id: " + texture_id);
            textures.put(texture_id, null);
        } else {
            Console.warn("Cannot put texture_id of " + texture_id + " when it already exists");
            return null;
        }
        textureClass textureClass = new textureClass();
        textureClass.texture_location = null;
        textureClass.texture = null;
        textureClass.id = texture_id;
        texture_list.add(texture_id);
        texture_class.put(texture_id, textureClass);
        return textureClass.get();
    }*/
    public textureClass addTexture(Integer texture_id, String location) {
        BufferedImage image;
        textureClass textureClass;
        if(!textures.containsKey(texture_id)) {
            File file = new File(location);
            if(!file.exists()) {
                ClassLoader classLoader = getClass().getClassLoader();
                try {
                    file = new File(classLoader.getResource(location).getFile());
                } catch(NullPointerException e) {
                    Console.error(e.fillInStackTrace());
                    return null;
                }
                if(!file.exists()) {
                    Console.error("Attempted to create a new texture, but its file doesn't exist... ["+texture_id+"]");
                    return null;
                }
            }
            try {
                image = ImageIO.read(file);
            } catch (IOException e) {
                Console.error("error");
                Console.error(e.fillInStackTrace());
                return null;
            }
            textureClass = new textureClass();
            Console.log(
                    "Created a new texture:" +
                    "\n    id: " + texture_id +
                    "\n    loc: " + location +
                    "\n"
            );
        } else {
            Console.warn("Cannot create new texture of id "+texture_id+" when it already exists");
            return null;
        }
        textureClass.texture_location = location;
        textureClass.image = image;
        textureClass.id = texture_id;
        textureClass.setDepth(0);
        textureClass.setActive(true);
        textureClass.setPosition(0, 0);
        texture_list.add(texture_id);
        textures.put(texture_id, textureClass);
        return textureClass.get();
    }
    public textureClass get(Integer texture_id) {
        return textures.get(texture_id);
    }
    public void removeTexture(Integer texture_id) {
        if(!textures.containsKey(texture_id))
            return;
        texture_list.remove(texture_id);
        //textures.get(texture_id).dispose();
        texture_active.remove(texture_id);
    }
    public void delete(Integer texture_id) {
        removeTexture(texture_id);
    }
    /*public Texture getTexture(Integer id) {
        return textures.getOrDefault(id, null);
    }*/
    /*protected static void dispose() {
        Console.warn("Getting rid of all textures...");
        for(Integer texture_id : texture_list) {
            if(textures.get(texture_id) != null) {
                Texture texture = textures.get(texture_id);
                texture.dispose();
                textures.remove(texture_id);
            }
        }
    }*/
}
