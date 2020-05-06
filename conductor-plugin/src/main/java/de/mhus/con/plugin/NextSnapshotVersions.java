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
        // String changed = ((LabelsImpl)project.getLabels()).getOrNull("version.changed");
        String version = ((MProperties) project.getProperties()).getString("version", null);
        if (version != null) {
            String[] parts = version.split("\\.");
            if (parts.length >= 2) {
                parts[1] = String.valueOf(MCast.toint(parts[1], 0) + 1);
                if (parts.length >= 3) parts[2] = "0";
            } else if (parts.length >= 1) parts[0] = String.valueOf(MCast.toint(parts[0], 0) + 1);
            version = MString.join(parts, '.') + "-SNAPSHOT";
            log().i("Changed project", project.getName(), version);

            ((MProperties) ((ContextProject) project).getInstance().getProperties())
                    .setString("version", version);
        }
        return true;
    }
}
