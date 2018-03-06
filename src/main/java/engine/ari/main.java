package engine.ari;

import engine.ari.engine_main.Engine;
import engine.ari.engine_main.Input;
import engine.ari.engine_main.Networking;
import engine.ari.engine_main.modding.Loadup;
import engine.ari.engine_main.Console;
import engine.ari.engine_main.entity.Character;
import engine.ari.engine_main.rendering.Textures;
import engine.ari.engine_main.rendering.render;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static java.lang.Thread.sleep;
import org.lwjgl.opengl.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
//import Main;

import java.lang.reflect.Type;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

public class main /*extends ApplicationAdapter*/ {
	private Engine engine = new Engine();
	private Networking networking = new Networking();
	private Input input = new Input();
	private Engine.config config = new Engine.config();
	private engine.ari.engine_main.rendering.render render = new render();
	private Character character = new Character();

	private Integer tick_prepare = 0;

	Boolean openIsCompleted = false;

    public static long window;

	public final void create() {
		Console.warn("Creating LWJGL...");
        new Console();
        Properties p = System.getProperties();

		java.io.Console console = System.console();

		String os = p.getProperty("os.name").toLowerCase(Locale.ENGLISH);
		Console.log("OS: " + os);
		Console.log("BOOT WITH:", p.getProperty("java.runtime.name"));
		Console.log("JVM:",  p.getProperty("java.vm.name"), p.getProperty("java.vm.info"));
		Console.log("JDK:", p.getProperty("java.version"));//, p.getProperty("java.library.path"));
		Console.log("ARCH:", p.getProperty("os.arch"));
		Console.log("CPU:", p.getProperty("sun.cpu.isalist"));

		String icon;
		if(os.contains("win") || os.contains("nux")) {
			icon = "icon.png";
		} else if(os.contains("mac")) { // how dare you
			icon = "icon-mac.png";
		} else {
			Console.error("Not sure what OS you're running on!! Canceling!");
            glfwTerminate();
			throw new RuntimeException();
		}
		GLFWErrorCallback.createPrint(Console.error).set();
		if(!glfwInit()) {
			Console.error("LWJGL could not initiate for some reason? Ending session...");
			glfwTerminate();
			throw new NullPointerException();
		}

        config.build();
        String title = (String) config.get("Title");
        if(title == null)
            title = "ariEngine [no title set]";

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
        window = glfwCreateWindow(300, 300, title, NULL, NULL);
        if(window == NULL) {
            Console.error("LWJGL could not create window");
            glfwTerminate();
            throw new NullPointerException();
        }
        glfwMakeContextCurrent(window);

        GLFWKeyCallback keyStuff = glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {

        });
        GLFWWindowCloseCallback close = new GLFWWindowCloseCallback() {
			@Override
			public void invoke(long window) {
                glfwSetWindowShouldClose(window, true);
			}
		};
        glfwSetWindowCloseCallback(window, close);
		GLFWFramebufferSizeCallbackI resize = new GLFWFramebufferSizeCallback() {
			@Override
			public void invoke(long window, int x, int y) {
				Console.log("Resized window to", x + "x" + y);
			}
		};
        glfwSetFramebufferSizeCallback(window, resize);
		/*BufferedImage image = null;
		try {
			image = ImageIO.read(new File(icon));
		} catch (IOException e) {
			Console.warn("Tried to set icon to " + icon + ", but it doesn't exist");
		}*/

        glfwMakeContextCurrent(window);
        glfwSwapInterval(0);

        glfwShowWindow(window);
        //glfwSetWindowTitle(1, title);
        Console.log(title + " has fully started without errors! Horray!");
        /*if(image != null) {
            glfwSetWindowIcon(0, image);
            // for now, fuck this lol, too lazy to convert to real buffer
        }*/


        new Loadup(character);
	}

	public void resize(int width, int height) {

	}

	public static int fps = 0;
	private static int lastFPS = 0;
	public static void updateFPS() {
        if (glfwGetTime() - lastFPS > 1000) {
            fps = 0; // reset the FPS counter
            lastFPS += 1000; //add one second
        }
        fps++;
    }
	public void render () {

        int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShader, "content/shaders/shader.vert");
        GL20.glCompileShader(vertexShader);
        int status = GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS);
        if (status != GL_TRUE) {
            Console.error("Vertex shader failed to compile!!");
            throw new RuntimeException(GL20.glGetShaderInfoLog(vertexShader));
        }

        int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShader, "content/shaders/shader.frag");
        GL20.glCompileShader(fragmentShader);
        status = GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS);
        if (status != GL_TRUE) {
            Console.error("Fragment shader failed to compile!!");
            throw new RuntimeException(GL20.glGetShaderInfoLog(fragmentShader));
        }

        int shaderProgram = GL20.glCreateProgram();
        GL20.glAttachShader(shaderProgram, vertexShader);
        GL20.glAttachShader(shaderProgram, fragmentShader);
        GL30.glBindFragDataLocation(shaderProgram, 0, "fragColor");
        GL20.glLinkProgram(shaderProgram);

        long sleepTime = 1000L / 60L;
        GL.createCapabilities();
        glClearColor(0.2f, 0.2f, 0.3f, 0.0f);
        while (!glfwWindowShouldClose(window)) {
            updateFPS();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glPushMatrix();
            glfwSwapBuffers(window);
            glTranslatef(100, 100, 0);

            // Lets grab all the textures and put them into an array
            Map<Integer/*id*/, Integer/*depth*/> textures = new HashMap<>();
            for (Integer texture_id : Textures.textures.keySet()) {
                Textures.textureClass textureClass = Textures.textures.get(texture_id);
                int depth = textureClass.getDepth();
                textures.put(textureClass.id, depth);
            }
            List<Map.Entry<Integer, Integer>> list = new LinkedList<>(textures.entrySet());
            list.sort(Comparator.comparing(o -> (o.getValue())));
            ArrayList<Integer> result = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : list) {
                result.add(entry.getKey());
            }

            // Now that we have that full array, lets start drawing them.
            glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            for(int id : result) {
                if(!Textures.textures.containsKey(id))
                    continue;
                Boolean active = Textures.texture_active.get(id);
                if(active == null || !active)
                    continue;
                Textures.textureClass texture = Textures.textures.get(id);
                Integer tex;
                if(texture.texture == null) {
                    tex = texture.texture = texture.makeTexture();
                    Console.log("DEBUG: made a texture?");
                } else {
                    tex = texture.texture;
                }
                glBindTexture(GL_TEXTURE_2D, tex);
            }
            glBegin(GL11.GL_QUADS);
            glTexCoord2f(0, 0);
            glVertex2i(0, 0);

            glTexCoord2f(1, 0);
            glVertex2i(512, 0);

            glTexCoord2f(1, 1);;
            glVertex2i(512, 512);

            glTexCoord2f(0, 1);
            glVertex2i(0, 512);
            glEnd();
            // All textures should now be drawn onto the screen


            glPopMatrix();

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
            try {
                sleep(sleepTime);
            } catch (InterruptedException e) {
                Console.error(e.fillInStackTrace()  );
            }
        }
	}

	public void pause() {
		//if(!engine.getWasAlreadyPaused())
		//engine.pause(true, false);
		Console.log("pause");
	}

	public void resume() {
		//engine.pause(false, false);
		Console.log("resume");
	}
	
	public void dispose () {
		//engine.pause(false, true);
		Console.warn("Preparing to quit...");
		//render.dispose();
	}
}
