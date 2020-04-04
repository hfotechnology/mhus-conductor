package de.mhus.cur.core;

import java.util.Map;

import de.mhus.deploy.api.Conductor;
import de.mhus.deploy.api.Context;
import de.mhus.deploy.api.Plugin;
import de.mhus.deploy.api.Project;
import de.mhus.deploy.api.Step;
import de.mhus.lib.core.IReadProperties;
import de.mhus.lib.core.MProperties;
import de.mhus.lib.core.parser.StringCompiler;
import de.mhus.lib.errors.MException;
import de.mhus.lib.errors.MRuntimeException;

public class ContextImpl implements Context {

    private MProperties properties;
	private Plugin plugin;
	private Project project;
	private Conductor cur;
	private Step step;

    public ContextImpl(Conductor cur) {
        properties = new MProperties(cur.getProperties());
    }

    public ContextImpl(Conductor cur, IReadProperties projectProp) {
    	this.cur = cur;
        properties = new MProperties(cur.getProperties());
        putReadProperties("project.", projectProp);
    }
    
	public void putReadProperties(String prefix, IReadProperties m) {
		if (m == null) return;
		for (Map.Entry<? extends String, ? extends Object> e : m.entrySet())
			properties.put(prefix + e.getKey(),e.getValue());
	}

    
    @Override
    public String make(String in) {
    	if (in == null) return null;
    	try {
	        return StringCompiler.compile(in).execute(properties);
		} catch (MException e) {
			throw new MRuntimeException(in, e);
		}
	}

	public void init(Project project, Plugin plugin, Step step) {
		this.project = new ContextProject(this, project);
		this.plugin = new ContextPlugin(this, plugin);
		this.step = new ContextStep(this, step);
	}

	public Conductor getConductor() {
		return cur;
	}

	public MProperties getParameters() {
		return properties;
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public Project getProject() {
		return project;
	}

	public Step getStep() {
		return step;
	}

	@Override
	public IReadProperties getProperties() {
		return properties;
	}

}
