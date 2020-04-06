package de.mhus.con.core;

import java.util.Map;

import de.mhus.con.api.Conductor;
import de.mhus.con.api.Context;
import de.mhus.con.api.Plugin;
import de.mhus.con.api.Project;
import de.mhus.con.api.Step;
import de.mhus.lib.core.IReadProperties;
import de.mhus.lib.core.MProperties;
import de.mhus.lib.core.MSystem;
import de.mhus.lib.core.parser.StringCompiler;
import de.mhus.lib.errors.MException;
import de.mhus.lib.errors.MRuntimeException;

public class ContextImpl implements Context {

    private MProperties properties;
	private Plugin plugin;
	private Project project;
	private Conductor con;
	private Step step;

    public ContextImpl(Conductor con) {
        properties = new MProperties(con.getProperties());
    }

    public ContextImpl(Conductor con, IReadProperties projectProp) {
    	this.con = con;
        properties = new MProperties(con.getProperties());
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

	@Override
	public Conductor getConductor() {
		return con;
	}

	@Override
	public Plugin getPlugin() {
		return plugin;
	}

	@Override
	public Project getProject() {
		return project;
	}

	@Override
	public Step getStep() {
		return step;
	}

	@Override
	public IReadProperties getProperties() {
		return properties;
	}

    @Override
	public String toString() {
		return MSystem.toString(this, plugin, step, project);
	}

}
