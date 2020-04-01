package de.mhus.bwk.core;

import java.util.List;

public interface Projects extends ICollection<Project> {

	List<Project> select(Labels labels);

}
