package de.mhus.bwk.core;

import java.util.LinkedList;
import java.util.List;

public class ProjectsImpl extends XCollection<Project> implements Projects {

	@SuppressWarnings("unused")
    private Bwk bwk;

    @Override
	public List<Project> select(Labels labels) {
		LinkedList<Project> ret = new LinkedList<>();
		collection.forEach((k,v) -> {
			if (v.getLabels().matches(labels))
				ret.add(v);
		});
		return ret;
	}

    public void init(Bwk bwk) {
        this.bwk = bwk;
    }

    @Override
    public List<Project> getAll() {
        return new LinkedList<Project>(collection.values());
    }

}
