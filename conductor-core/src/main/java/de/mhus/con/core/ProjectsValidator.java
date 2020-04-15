package de.mhus.con.core;

import java.io.File;

import de.mhus.con.api.AValidator;
import de.mhus.con.api.Conductor;
import de.mhus.con.api.Context;
import de.mhus.con.api.Project;
import de.mhus.con.api.Validator;
import de.mhus.lib.errors.MException;

@AValidator(name="project")
public class ProjectsValidator implements Validator {

    @Override
    public void validate(Conductor con) throws MException {
        for (Project p : con.getProjects()) {
            // check for root dir
            validate(p);
        }
    }

    @Override
    public void validate(Context context) throws MException {
        validate(context.getProject());
    }

    private void validate(Project p) throws MException {
        File rootDir = p.getRootDir();
        if (!rootDir.exists() || !rootDir.isDirectory())
            throw new MException("project root dir not exists",p,rootDir);
    }

}
