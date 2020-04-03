package de.mhus.cur.core;

import java.io.File;

import de.mhus.lib.errors.MException;

public class ProjectsValidator implements Validator {

    @Override
    public void validate(Conductor cur) throws MException {
        for (Project p : cur.getProjects()) {
            // check for root dir
            File rootDir = p.getRootDir();
            if (!rootDir.exists() || !rootDir.isDirectory())
                throw new MException("project root dir not exists",p,rootDir);
            // check for pom
            File f = new File(rootDir,"pom.xml");
            if (!f.exists() || !f.isFile())
                throw new MException("project pom file not exists",p,f);

        }
    }

}
