package de.mhus.deploy.core;

import java.util.List;

public interface Projects extends ICollection<Project> {

	List<Project> select(Labels labels);

}
