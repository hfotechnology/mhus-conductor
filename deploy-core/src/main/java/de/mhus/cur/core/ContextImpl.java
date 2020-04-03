package de.mhus.cur.core;

import de.mhus.lib.core.IReadProperties;
import de.mhus.lib.core.MProperties;
import de.mhus.lib.core.parser.CompiledString;
import de.mhus.lib.core.parser.StringCompiler;
import de.mhus.lib.errors.MException;
import de.mhus.lib.errors.MRuntimeException;

public class ContextImpl implements Context {

    private StringCompiler compiler = new StringCompiler();
    private MProperties parameters;
	private Plugin plugin;
	private Project project;
	private Conductor cur;
	private Step step;

    public ContextImpl(Conductor cur) {
        parameters = new MProperties(cur.getProperties());
    }

    public ContextImpl(Conductor cur, IReadProperties additional) {
    	this.cur = cur;
        parameters = new MProperties(cur.getProperties());
        parameters.putReadProperties(additional);
    }
    
    @Override
    public String make(String in) {
    	if (in == null) return null;
    	try {
	        CompiledString cs = compiler.compileString(in);
	        String ret = cs.execute(parameters);
	        return ret;
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
		return parameters;
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

}
