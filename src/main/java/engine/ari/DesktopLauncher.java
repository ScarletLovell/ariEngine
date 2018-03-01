package engine.ari;
// Fun fact: Ari means Ant in Japanese

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import engine.ari.engine_main.Console;

import java.util.Locale;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 600;
		config.fullscreen = false;
		config.resizable = false;
		config.vSyncEnabled = false;
		String os = System.getProperties().getProperty("os.name").toLowerCase(Locale.ENGLISH);
		if(os.contains("win") || os.contains("nux")) {
			config.addIcon("icon.png", Files.FileType.Local);
		} else if(os.contains("mac")) { // how dare you
			config.addIcon("icon-mac.png", Files.FileType.Local);
		} else {
			Console.error("Not sure what OS you're running on!! Canceling!");
			return;
		}
		new LwjglApplication(new main(), config);
	}
}
