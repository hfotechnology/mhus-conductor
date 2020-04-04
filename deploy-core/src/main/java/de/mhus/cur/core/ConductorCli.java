package de.mhus.cur.core;

import java.util.LinkedList;

import de.mhus.lib.core.MProperties;
import de.mhus.lib.core.MString;

public class ConductorCli {

	public static void main(String[] args) {
		
		LinkedList<String> queue = new LinkedList<>();
		if (args != null)
			for (String arg : args)
				queue.add(arg);
		
		new ConductorCli().execute(queue);
		
	}

	protected void execute(LinkedList<String> queue) {
		
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
				executeProperty(next, queue);
				
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
		
	}

	private void executeProperty(String next, LinkedList<String> queue) {
		// TODO Auto-generated method stub
		xxx
	}

	private void executeLifecycle(String execLifecycle, MProperties execProperties) {
		ConfiguratorDefault config = new ConfiguratorDefault();
		xxx
	}

}
