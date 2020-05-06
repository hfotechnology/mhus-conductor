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
import de.mhus.lib.core.MLog;

@AMojo(name = "con.includeLifecycle")
public class IncludeLifecycle extends MLog implements ExecutePlugin {

    @Override
    public boolean execute(Context context) throws Exception {
        for (String arg : context.getStep().getArguments()) {
            System.out.println(">>> Include Lifecycle " + arg);
            context.getExecutor().execute(context.getConductor(), arg);
            System.out.println("<<< End Lifecycle " + arg);
        }
        return true;
    }
}
