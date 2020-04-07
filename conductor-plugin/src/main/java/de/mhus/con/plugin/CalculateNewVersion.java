package de.mhus.con.plugin;

import java.io.File;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.Project;
import de.mhus.con.core.LabelsImpl;
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.MProperties;

@AMojo(name = "calculateNewVersion")
public class CalculateNewVersion extends MLog implements ExecutePlugin {

    @Override
    public void execute(Context context) throws Exception {
        String versionsFilePath = context.getStep().getProperties().getString("versionsFile", "versions.properties");
        File versionsFile = ConUtil.getFile(
                context
                .getConductor()
                .getRoot()
                , versionsFilePath);
        if (versionsFile.exists() && versionsFile.isFile()) {
            MProperties versions = MProperties.load(versionsFile);
            
            // load history
            String historyFilePath = context.getStep().getProperties().getString("historyFile", "history.properties");
            File historyFile = ConUtil.getFile(context.getConductor().getRoot(), historyFilePath);
            MProperties history = new MProperties();
            if (historyFile.exists() && historyFile.isFile())
                history = MProperties.load(historyFile);
            
            log().t("versions",versions, history);
            
            // find versions for projects
            for (Project project : context.getConductor().getProjects()) {
                boolean changed = true;
                String name = project.getName();
                String version = versions.getString(name, null);
                if (version == null) {
                    version = history.getString(name, null);
                    changed = false;
                } else
                if (version.equals(history.getString(name, null))) {
                    changed = false;
                }
                if (changed) {
                    log().i("Changed project",name,version);
                }
                ((MProperties)project.getProperties()).setString("version", version);
                ((LabelsImpl)project.getLabels()).put("version.changed", String.valueOf(changed));
                
            }
            
        } else {
            log().i("versions file not found",versionsFile);
        }
    }

}
