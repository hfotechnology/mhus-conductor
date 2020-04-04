package de.mhus.cur.api;

import java.util.List;

public interface Projects extends ICollection<Project> {

	List<Project> select(Labels selector);

    List<Project> getAll();

}
