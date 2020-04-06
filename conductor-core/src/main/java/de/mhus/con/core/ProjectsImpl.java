package de.mhus.con.core;

import java.util.LinkedList;
import java.util.List;

import de.mhus.con.api.Conductor;
import de.mhus.con.api.Labels;
import de.mhus.con.api.Project;
import de.mhus.con.api.Projects;

public class ProjectsImpl extends XCollection<Project> implements Projects {

	@SuppressWarnings("unused")
    private Conductor con;

    @Override
	public List<Project> select(Labels selector) {
		LinkedList<Project> ret = new LinkedList<>();
		collection.forEach((k,v) -> {
			if (v.getLabels().matches(selector))
				ret.add(v);
		});
		return ret;
	}

    public void init(Conductor con) {
        this.con = con;
    }

    @Override
    public List<Project> getAll() {
        return new LinkedList<Project>(collection.values());
    }

}
