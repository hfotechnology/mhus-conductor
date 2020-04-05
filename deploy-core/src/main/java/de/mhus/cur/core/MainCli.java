package de.mhus.cur.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import de.mhus.cur.api.Cli;
import de.mhus.cur.api.MainOption;
import de.mhus.cur.api.MainOptionHandler;
import de.mhus.deploy.api.meta.Version;
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.MProperties;
import de.mhus.lib.core.MString;
import de.mhus.lib.errors.MException;
import de.mhus.lib.errors.NotFoundException;

public class MainCli extends MLog implements Cli {

	protected Map<String, MainOptionHandler> optionHandlers = new HashMap<>();
	protected File rootDir = new File(".");
	protected ConductorImpl cur;
	protected String configFile;
	
	public static void main(String[] args) throws Exception {
		
		if (args == null || args.length == 0) {
			System.out.println("Try --help");
			return;
		}
		
		LinkedList<String> queue = new LinkedList<>();
		for (String arg : args)
			queue.add(arg);
		
		new MainCli().execute(queue);
		
	}
	
	public MainCli() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String pack = MString.beforeLastIndex(getClass().getPackageName(), '.');
		log().t("Scan Package", pack);
		for (Class<?> clazz : getClasses( pack ) ) {
			MainOption optionDef = clazz.getAnnotation(MainOption.class);
			log().t("Option",clazz,optionDef);
			if (optionDef != null) {
				Object inst = clazz.getConstructor().newInstance();
				for (String alias : optionDef.alias()) {
					MainOptionHandler old = optionHandlers.put(alias, (MainOptionHandler) inst);
					if (old != null)
						log().w("Overwrite main option",alias);
				}
			}
		}
		log().t("optionHandlers",optionHandlers);
	}

	private static Class<?>[] getClasses(String packageName) {
		Reflections reflections = new Reflections(packageName);
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(MainOption.class);
		return annotated.toArray(new Class<?>[0]);
	}
	
	protected void execute(LinkedList<String> queue) throws MException {
		
		MProperties execProperties = null;
		String execLifecycle = null;
		
		while (queue.size() > 0) {
			String next = queue.removeFirst();
			if (next.startsWith("-")) {
				if (execLifecycle != null) {
					executeLifecycle(execLifecycle, execProperties);
					execLifecycle = null;
					execProperties = null;
				}
				executeOption(next, queue);
				
			} else
			if (execLifecycle == null) {
				execLifecycle = next;
				execProperties = new MProperties();
			} else {
				if (MString.isIndex(next, '='))
					execProperties.put(MString.beforeIndex(next, '=').trim(), MString.afterIndex(next, '='));
			}
		}
		
		if (execLifecycle != null) {
			executeLifecycle(execLifecycle, execProperties);
			execLifecycle = null;
			execProperties = null;
		}
		
		resetCur();
		
	}

	private void executeOption(String next, LinkedList<String> queue) throws NotFoundException {
		if (next.equals("-")) return;
		// -P path/
		// --path path/
		MainOptionHandler handler = optionHandlers.get(next);
		if (handler == null) throw new NotFoundException("option",next);
		handler.execute(this,next, queue);
	}

	private void executeLifecycle(String execLifecycle, MProperties execProperties) throws MException {
		
		createConductor();

		((MProperties)cur.getProperties()).putReadProperties(execProperties);
        ExecutorDefault executor = new ExecutorDefault();
        
        executor.execute(cur, execLifecycle);

	}

	private void createConductor() throws MException {
		if (cur != null) return;
		log().d("Create conductor object");
		ConfiguratorDefault config = new ConfiguratorDefault();
		
        ((SchemesImpl)config.getSchemes()).put("file", new FileScheme() );
        ((SchemesImpl)config.getSchemes()).put("mvn", new MavenScheme() );
        ((ConfigTypesImpl)config.getTypes()).put("yml", new YmlConfigType());
        ((ConfigTypesImpl)config.getTypes()).put("yaml", new YmlConfigType());
        config.getValidators().add(new ProjectsValidator());

        if (configFile == null) {
        	// set default
        	File file = new File(rootDir, "conductor.yml");
        	if (file.exists() && file.isFile())
        		configFile = "file:conductor.yml";
        	else
        		configFile = "mvn:de.mhus.deploy/deploy-plugin/"+Version.VERSION+"/yml/configuration-default";
        }
        URI uri = URI.create(configFile);
        
        cur = new ConductorImpl(rootDir);
        config.configure(uri, cur, null);
		
	}

	@Override
	public Map<String, MainOptionHandler> getOptions() {
		return optionHandlers;
	}

	public void resetCur() {
		if (cur == null) return;
		cur.close();
		cur = null;
	}

}
