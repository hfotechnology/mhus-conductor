package de.mhus.con.core;

import java.io.File;
import java.util.Map.Entry;

import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Labels;
import de.mhus.con.api.Project;
import de.mhus.lib.core.IReadProperties;
import de.mhus.lib.core.MProperties;

public class ContextProject implements Project {

	private ContextImpl context;
	private Project inst;
	private MProperties properties;
	private File rootDir;
	private ContextLabels labels;

	public ContextProject(ContextImpl context, Project inst) {
		this.context = context;
		this.inst = inst;
	}

	@Override
	public Labels getLabels() {
		if (labels == null) {
			labels = new ContextLabels(context, inst.getLabels());
		}
		return labels;
	}

	@Override
	public String getName() {
		return inst.getName();
	}

	@Override
	public String getPath() {
		return context.make(inst.getPath());
	}

	@Override
	public File getRootDir() {
		
		if (rootDir == null)
	        rootDir = ConUtil.getFile(context.getConductor().getRoot(), getPath());
		return rootDir;
	}

	@Override
	public IReadProperties getProperties() {
		if (properties == null) {
			properties = new MProperties();
			for (Entry<String, Object> entry : inst.getProperties().entrySet()) {
				Object v = entry.getValue();
				if (v instanceof String)
					v = context.make((String)v);
				properties.put(entry.getKey(), v);
			}
		}
		return properties;
	}

    @Override
	public String toString() {
        if (inst == null) return "?";
		return inst.toString();
	}

    @Override
    public STATUS getStatus() {
        return inst.getStatus();
    }

    public Project getInstance() {
        return inst;
    }
    
}
