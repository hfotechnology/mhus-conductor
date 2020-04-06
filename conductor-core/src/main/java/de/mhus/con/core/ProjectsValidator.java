package de.mhus.con.core;

import java.io.File;

import de.mhus.con.api.AValidator;
import de.mhus.con.api.Conductor;
import de.mhus.con.api.Project;
import de.mhus.con.api.Validator;
import de.mhus.lib.errors.MException;

@AValidator(name="project")
public class ProjectsValidator implements Validator {

    @Override
    public void validate(Conductor con) throws MException {
        for (Project p : con.getProjects()) {
            // check for root dir
            File rootDir = p.getRootDir();
            if (!rootDir.exists() || !rootDir.isDirectory())
                throw new MException("project root dir not exists",p,rootDir);
            // check for pom
//            File f = new File(rootDir,"pom.xml");
//            if (!f.exists() || !f.isFile())
//                throw new MException("project pom file not exists",p,f);

        }
    }

}
