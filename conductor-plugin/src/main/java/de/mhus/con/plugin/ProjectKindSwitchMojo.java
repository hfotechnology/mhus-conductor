package de.mhus.con.plugin;

import java.io.Closeable;
import java.io.File;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.Step;
import de.mhus.con.core.ContextStep;
import de.mhus.con.core.ExecutorDefault;
import de.mhus.lib.core.MLog;

@AMojo(name = "projectKindSwitch",target = "kind")
public class ProjectKindSwitchMojo extends MLog implements ExecutePlugin {

    public enum TYPES {UNKNOWN,MAVEN,GRADEL,IVY,ANT,SBT,NPM,MAKE}
    
    @Override
    public boolean execute(Context context) throws Exception {

        File dir = context.getProject().getRootDir();
        TYPES type = TYPES.UNKNOWN;
        
        if (new File(dir, "pom.xml").exists()) {
            type = TYPES.MAVEN;
        } else
        if (new File(dir, "build.gradel").exists()) {
            type = TYPES.GRADEL;
        } else
        if (new File(dir, "build.sbt").exists()) {
            type = TYPES.SBT;
        } else
        if (new File(dir, "ivy.xml").exists()) {
            type = TYPES.IVY;
        } else
        if (new File(dir,"build.xml").exists()) {
            type = TYPES.ANT;
        } else
        if (new File(dir,"Makefile").exists()) {
            type = TYPES.MAKE;
        } else
        if (new File(dir, "package.json").exists()) {
            type = TYPES.NPM;
        }
        
        for (Step typeCaze : context.getStep().getSubSteps()) {
            if (typeCaze.getTarget().equalsIgnoreCase(type.name())) {
                log().d("Found management type",type);
                try ( Closeable x = ((ExecutorDefault)context.getExecutor()).enterSubSteps(context.getStep()) ) {
                    for (Step caze : typeCaze.getSubSteps()) {
                        ((ExecutorDefault)context.getExecutor()).executeInternal( ((ContextStep)caze).getInstance(), context.getProject() );
                    }
                }
                return true;
            }
        }

        
        return false;
    }
    
}
