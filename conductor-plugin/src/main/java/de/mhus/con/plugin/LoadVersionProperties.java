package de.mhus.con.plugin;

import java.io.File;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.Project;
import de.mhus.lib.core.IReadProperties;
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.MProperties;
import de.mhus.lib.core.util.Pom;

@AMojo(name = "loadVersionProperties",target = "con.loadVersionProperties")
public class LoadVersionProperties extends MLog implements ExecutePlugin {

    @Override
    public boolean execute(Context context) throws Exception {

        String versionsFilePath =
                context.getStep().getProperties().getString("versionsFile", "versions.properties");
        File versionsFile = ConUtil.getFile(context.getConductor().getRoot(), versionsFilePath);
        log().t("versions file", versionsFile);
        File pomFile = ConUtil.getFile(context.getConductor().getRoot(), "pom.xml");
        Pom pom = new Pom(pomFile);
        IReadProperties pomProperties = pom.getLocalProperties();
        
        
        if (versionsFile.exists() && versionsFile.isFile()) {
            MProperties versions = MProperties.load(versionsFile);

            for (Project project : context.getConductor().getProjects()) {
                String name = project.getName();
                String version = versions.getString(name, null);
                if (version == null || version.equals("0.0.0")) {
                    // load from pom
                    if (context.getProperties().getBoolean("loadFromPom", false)) {
                        String versionProperty = context.getProject().getProperties().getString("property." + name, null);
                        if (versionProperty != null)
                            version = pomProperties.getString(versionProperty, null);
                    }
                }
                if (version == null) {
                    log().d("version not found for",name);
                } else {
                    log().i("version found",name,version);
                    ((MProperties) project.getProperties()).setString("version", version);
                }
            }
            return true;

        } else
            return false;
    }

}
