package de.mhus.con.core;

import java.util.Map;

import de.mhus.con.api.Conductor;
import de.mhus.con.api.Context;
import de.mhus.con.api.Executor;
import de.mhus.con.api.Plugin;
import de.mhus.con.api.Project;
import de.mhus.con.api.Step;
import de.mhus.lib.core.IReadProperties;
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.MProperties;
import de.mhus.lib.core.MSystem;
import de.mhus.lib.core.parser.StringCompiler;
import de.mhus.lib.errors.MException;
import de.mhus.lib.errors.MRuntimeException;

public class ContextImpl extends MLog implements Context {

    private MProperties properties;
	private Plugin plugin;
	private Project project;
	private Conductor con;
	private Step step;
	private Executor executor;

    public ContextImpl(Conductor con) {
        this.con = con;
        properties = new MProperties();
        putReadProperties("", con.getProperties());
    }

    public ContextImpl(Conductor con, IReadProperties additional) {
    	this.con = con;
        properties = new MProperties();
        putReadProperties("", con.getProperties());
        putReadProperties("", additional);
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
	        String ret = StringCompiler.compile(in).execute(properties);
	        log().t("make", in, ret);
	        return ret;
		} catch (MException e) {
		    log().t(in,e);
			throw new MRuntimeException(in, e);
		}
	}

	public void init(Executor executor, Project project, Plugin plugin, Step step) {
		this.project = project == null ? null : new ContextProject(this, project);
		this.plugin = new ContextPlugin(this, plugin);
		this.step = new ContextStep(this, step);
		this.executor = executor;

		if (step != null)
		    putReadProperties("step.", step.getProperties());
		if (project != null)
		    putReadProperties("project.", project.getProperties());
		if (con != null && con.getProjects() != null)
    		for (Project p : con.getProjects())
                putReadProperties("projects." + p.getName() + ".", p.getProperties());
		
		log().t("init", project, plugin, step,properties);
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

    @Override
    public Executor getExecutor() {
        return executor;
    }

}
