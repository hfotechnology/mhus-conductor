package de.mhus.cur.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
		
		LinkedList<String> queue = new LinkedList<>();
		if (args != null)
			for (String arg : args)
				queue.add(arg);
		
		new MainCli().execute(queue);
		
	}
	
	public MainCli() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		for (Class<?> clazz : getClasses( MString.beforeLastIndex(getClass().getPackageName(), '.') ) ) {
			MainOption optionDef = clazz.getAnnotation(MainOption.class);
			if (optionDef != null) {
				Object inst = clazz.getConstructor().newInstance();
				for (String alias : optionDef.alias()) {
					MainOptionHandler old = optionHandlers.put(alias, (MainOptionHandler) inst);
					if (old != null)
						log().w("Overwrite main option",alias);
				}
			}
		}
	}

	/**
	 * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
	 *
	 * @param packageName The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */	
	@SuppressWarnings({ "rawtypes" })
	private static Class<?>[] getClasses(String packageName)
	        throws ClassNotFoundException, IOException {
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    assert classLoader != null;
	    String path = packageName.replace('.', '/');
	    Enumeration<URL> resources = classLoader.getResources(path);
	    List<File> dirs = new ArrayList<File>();
	    while (resources.hasMoreElements()) {
	        URL resource = resources.nextElement();
	        dirs.add(new File(resource.getFile()));
	    }
	    ArrayList<Class> classes = new ArrayList<Class>();
	    for (File directory : dirs) {
	        classes.addAll(findClasses(directory, packageName));
	    }
	    return classes.toArray(new Class[classes.size()]);
	}
	
	 /**
	  * Recursive method used to find all classes in a given directory and subdirs.
	  *
	  * @param directory   The base directory
	  * @param packageName The package name for classes found inside the base directory
	  * @return The classes
	  * @throws ClassNotFoundException
	  */
	 @SuppressWarnings("rawtypes")
	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
	     List<Class> classes = new ArrayList<Class>();
	     if (!directory.exists()) {
	         return classes;
	     }
	     File[] files = directory.listFiles();
	     for (File file : files) {
	         if (file.isDirectory()) {
	             assert !file.getName().contains(".");
	             classes.addAll(findClasses(file, packageName + "." + file.getName()));
	         } else if (file.getName().endsWith(".class")) {
	             classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
	         }
	     }
	     return classes;
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
		MainOptionHandler handler = optionHandlers.get(next.substring(1));
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
