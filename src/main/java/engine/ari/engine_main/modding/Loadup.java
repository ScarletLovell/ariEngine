package engine.ari.engine_main.modding;

import engine.ari.engine_main.Console;
import engine.ari.engine_main.entity.Character;
import engine.ari.engine_main.modding.javascript.javascript;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;

public class Loadup {
    public List<File> getFiles(List<File> list, String directoryName) {
        File directory = new File(directoryName);
        List<File> files = new ArrayList<>();
        if(list != null) {
            files.addAll(list);
            for(File file : list) {
                Console.warn(file.getName());
            }
        }
        File[] newList = directory.listFiles();
        if(newList != null) {
            files.addAll(Arrays.asList(newList));
            for (File file : newList) {
                if (file.isDirectory()) {
                    return getFiles(files, file.getAbsolutePath());
                }
            }
        }
        List<File> evaluates = new ArrayList<>();
        if(files.size() > 0) {
            try {
                for (File file : files) {
                    if (file.getName().endsWith(".ts") && !file.getName().endsWith(".d.ts") || file.getName().endsWith(".js")) {
                        evaluates.add(file);
                    }
                }
            } catch(ConcurrentModificationException e) {
                Console.warn(e.getMessage());
            }
        }
        return evaluates;
    }

    public Loadup(Character character) {
        Console.warn("Preparing to check for mods...");
        Integer ready = 0;
        int executed = 0;

        List<File> files = getFiles(null, "content/JavaScript/scripts/");
        if(files.size() > 0) {
            for(File file : files) {
                Console.warn("[MOD LOADER]: Preparing to evaluate \'" + file.getName() + "\'");
                String internals = null;
                try {
                    FileInputStream fileIn = new FileInputStream(file);
                    int content;
                    while((content = fileIn.read()) != -1) {
                        internals = internals + ((char) content);
                    }
                    //internals = (String) objectIn.readUTF();
                } catch(Exception e) {
                    Console.error(e.getMessage());
                    continue;
                }
                if (internals == null || internals.trim().equalsIgnoreCase("")) {
                    Console.error("[MOD LOADER]: Unable to load " + file.getName() + ", mod is null");
                    continue;
                }
                javascript javascript = new javascript(file.getName());
                javascript.character = character;
                javascript.setBindings();
                Object a = javascript.eval(internals, file.getName(), true);
                if (a != null) {
                    Console.log("[MOD LOADER]: Executed " + (executed += 1) + "/" + files.size() + " mods");
                    Console.log(file.getName() + " returned with: " + a);
                } else {
                    Console.warn("[MOD LOADER]: Failed to execute " + file.getName() + ", " + a);
                }
            }
        }


        // ---
        // THIS ENTIRE PART WAS CUT THE FROM PROJECT BECAUSE I DON'T FEEL LIKE MAKING PYTHON AND LUA SUPPORT FULLY
        // IF YOU WANT TO CONTINUE ON THESE MOD SUPPORTS, UNDO THIS ENTIRE COMMENT...
        // or you know just redo them cause they're bad.
        // ---
        //FileHandle[] py_files = Gdx.files.local("mods/python/mod/").list((dir, name) -> name.endsWith(".py"));
        //FileHandle[] lua_files = Gdx.files.local("mods/lua/").list((dir, name) -> name.endsWith(".lua"));
		/*if(py_files.length > 0) {
			Integer amount = 0;
			ArrayList<String> modNames = new ArrayList<>();
			for (FileHandle file : py_files) {
				if(file.name().equalsIgnoreCase("__init__.py"))
					continue;
				if(!file.exists())
					continue;
				amount++;
				modNames.add(file.path());
			}
			Console.log("[MOD LOADER]: Found " + amount.toString() + " Python mods");
			Console.warn(modNames.toString());
			for(String mod : modNames) {
				if (!Gdx.files.local(mod).exists())
					continue;
				Console.warn("[MOD LOADER]: PREPARING TO EXECUTE " + Gdx.files.local(mod).name());
				String newFile = Gdx.files.local(mod).readString();
				if(newFile == null) {
					Console.error("[MOD LOADER]: Could not find main function\n      Make sure you used: function main()");
					continue;
				}
				python python = new python();
				Object a = python.eval(newFile, mod, true);
				if (a != null) {
					Console.log("[MOD LOADER]: Executed " + (executed+=1) + "/" + amount.toString() + " mods");
					Console.log(Gdx.files.local(mod).name() + " returned with: " + a);
				}/* else {
					//console.log("[MOD LOADER]: Failed to execute " + Gdx.files.local(mod).name());
				}// end comment belongs here
			}
		}
		if(lua_files.length > 0) {
			Integer amount = 0;
			ArrayList<String> modNames = new ArrayList<>();
			for (FileHandle file : lua_files) {
				if(!file.exists())
					continue;
				amount++;
				modNames.add(file.path());
			}
			Console.log("[MOD LOADER]: Found " + amount.toString() + " Lua mods");
			Console.warn(modNames.toString());
			for(String mod : modNames) {
				if (!Gdx.files.local(mod).exists())
					continue;
				Console.warn("[MOD LOADER]: PREPARING TO EXECUTE " + Gdx.files.local(mod).name());
				String newFile = Gdx.files.local(mod).readString();
				if(newFile == null) {
					Console.error("[MOD LOADER]: Could not find main function\n      Make sure you used: function main()");
					continue;
				}
				lua lua = new lua();
				Object a = lua.eval(newFile, mod, true);
				if (a != null) {
					Console.log("[MOD LOADER]: Executed " + (executed+=1) + "/" + amount.toString() + " mods");
					Console.log(Gdx.files.local(mod).name() + " returned with: " + a);
				}/* else {
					//console.log("[MOD LOADER]: Failed to execute " + Gdx.files.local(mod).name());
				}
			}
		}*/
        if(executed < 1) {
            Console.log("[MOD LOADER]: Found 0 mods. Twas' a sad day.");
        }
    }
}
