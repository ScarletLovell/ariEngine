package engine.ari;

import engine.ari.engine_main.Engine;
import engine.ari.engine_main.Input;
import engine.ari.engine_main.Networking;
import engine.ari.engine_main.external.External_Console;
import engine.ari.engine_main.modding.Loadup;
import engine.ari.engine_main.Console;
import engine.ari.engine_main.entity.Character;
import engine.ari.engine_main.rendering.Environment;
import engine.ari.engine_main.rendering.Screen_Text;
import engine.ari.engine_main.rendering.Textures;
import engine.ari.engine_main.rendering.render;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
//import Main;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

public class main extends ApplicationAdapter {
	private Engine engine = new Engine();
	private Networking networking = new Networking();
	private Input input = new Input();
	private Engine.config config = new Engine.config();
	private engine.ari.engine_main.rendering.render render = new render();
	private Character character = new Character();

	private Integer tick_prepare = 0;

	Boolean openIsCompleted = false;

	// Unfortunately LibGDX handles a lot of things very weirdly so I've decided to write a ton of things manually.
	// In this engine (as per this version), the engine will handle a lot of things itself, but --
	// will be using a lot of LibGDX as-well, such as input and rendering.
	@Override
	public void create() {
		config.build();

		String title;
		if(config.contains("Title")) {
			title = (String) config.get("Title");
		} else {
			title = "ariEngine [no title chosen]";
		}



		Console.warn("Creating JavaScript engine via Rhino");
		FileHandle file = Gdx.files.local("./console.txt");
		if(file.exists())
			file.delete();

		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			for (NetworkInterface ni : Collections.list(interfaces)) {
				for (InetAddress address : Collections.list(ni.getInetAddresses())) {
					if (address instanceof Inet4Address)
						networking.addresses.put(address.getHostAddress(), address.isAnyLocalAddress());
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		render.character = character;

		Environment.makeNewDefaultEnvironment();

		engine.setTitle(title);
		Console.log(title + " is starting");
		engine.setWindow(1280, 720);
		engine.VSync(false);
		Gdx.graphics.setResizable(false);

		render.create();
		Gdx.input.setInputProcessor(input);
		new Loadup(character);


		render.setBackgroundColor(0.2f, 0.2f, 0.2f, 1f);

		openIsCompleted = true;
		//new Main();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render () {
		if(!openIsCompleted)
			return;
		if(!engine.getPaused()) {
			if((tick_prepare+=1) > 15) {
				tick_prepare = 0;
				//tick+=1;
			}

			character.render();
			render.render();
			input.input();
		}
	}

	@Override
	public void pause() {
		//if(!engine.getWasAlreadyPaused())
		engine.pause(true, false);
		Console.log("pause");
	}

	@Override
	public void resume() {
		engine.pause(false, false);
		Console.log("resume");
	}
	
	@Override
	public void dispose () {
		engine.pause(false, true);
		Console.warn("Preparing to quit...");
		render.dispose();
	}
}
