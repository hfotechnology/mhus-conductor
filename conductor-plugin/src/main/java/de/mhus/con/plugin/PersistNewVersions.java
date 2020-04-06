package de.mhus.con.plugin;

import java.io.File;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.Project;
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.MProperties;

@AMojo(name="persistNewVersions")
public class PersistNewVersions extends MLog implements ExecutePlugin {

    @Override
    public void execute(Context context) throws Exception {
        String historyFilePath = context.getStep().getProperties().getString("historyFile", "history.properties");
        File historyFile = ConUtil.getFile(context.getConductor().getRoot(), historyFilePath);
        MProperties history = new MProperties();
        if (historyFile.exists() && historyFile.isFile())
            history = MProperties.load(historyFile);
        for (Project project : context.getConductor().getProjects()) {
            String name = project.getName();
            String version = project.getProperties().getString("version", null);
            if (version != null)
                history.setString(name, version);
        }

        log().t("persist",history,historyFile);
        history.save(historyFile);
        
    }

}
