/**
 * Copyright 2018 Mike Hummel
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*==
 * 
 * 
 */
package de.mhus.con.plugin;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.Project;
import de.mhus.con.core.ContextProject;
import de.mhus.lib.core.MCast;
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.MProperties;
import de.mhus.lib.core.MString;

@AMojo(name = "nextSnapshotVersions")
public class NextSnapshotVersions extends MLog implements ExecutePlugin {

    @Override
    public boolean execute(Context context) throws Exception {
        Project project = context.getProject();
        String staticVersion = context.getProperties().getString("static", null);
        String version = null;
        if (staticVersion != null) {
            version = staticVersion;
        } else {
            String next = context.getRecursiveProperty("nextVersionStep", "minor").toLowerCase().trim();
            String suffix = context.getRecursiveProperty("netVersionSuffix", "SNAPSHOT");
            String v = ((MProperties) project.getProperties()).getString("version", null);
            String vs = null;
            if (v != null) {
                if (MString.isIndex(v, '-')) {
                    vs = MString.afterIndex(v, '-');
                    v = MString.beforeIndex(v, '-');
                }
                String[] parts = v.split("\\.");
                if ("major".equals(next)) {
                    parts[0] = String.valueOf(MCast.toint(parts[0], 0) + 1);
                    for (int i = 1; i < parts.length; i++)
                        parts[i] = "0";
                } else
                if ("minor".equals(next)) {
                    if (parts.length >= 2) {
                        parts[1] = String.valueOf(MCast.toint(parts[1], 0) + 1);
                        for (int i = 2; i < parts.length; i++)
                            parts[i] = "0";
                    }
                } else
                if ("hotfix".equals(next)) {
                    if (parts.length >= 3) {
                        parts[2] = String.valueOf(MCast.toint(parts[2], 0) + 1);
                        for (int i = 3; i < parts.length; i++)
                            parts[i] = "0";
                    }
                } else {
                    v = null; // trigger not to set new version
                }
                if (v != null) {
                    version = MString.join(parts, '.');
                    if (MString.isSet(suffix)) {
                        if (suffix.equals("#timestamp"))
                            suffix = String.valueOf(System.currentTimeMillis());
                        else
                        if (suffix.equals("#buildnr")) {
                            int nr = MCast.toint(vs, 0) + 1;
                            suffix = String.valueOf(nr);
                        }
                        version = version + "-" + suffix;
                    }
                }
            }
        }
        
        if (version != null) {
            log().i("Next project version", project.getName(), version);
            ((MProperties) ((ContextProject) project).getInstance().getProperties())
                .setString("version", version);
        }
        return true;
    }
}
