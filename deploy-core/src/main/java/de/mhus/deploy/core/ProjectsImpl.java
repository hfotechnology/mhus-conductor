package de.mhus.deploy.core;

import java.util.LinkedList;
import java.util.List;

public class ProjectsImpl extends XCollection<Project> implements Projects {

	@Override
	public List<Project> select(Labels labels) {
		LinkedList<Project> ret = new LinkedList<>();
		collection.forEach((k,v) -> {
			if (v.getLabels().matches(labels))
				ret.add(v);
		});
		return null;
	}

}
