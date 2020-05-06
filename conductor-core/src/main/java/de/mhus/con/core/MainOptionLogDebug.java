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
package de.mhus.con.core;

import java.util.LinkedList;

import de.mhus.con.api.AOption;
import de.mhus.con.api.Cli;
import de.mhus.con.api.MainOptionHandler;
import de.mhus.lib.core.MApi;
import de.mhus.lib.core.logging.Log;

@AOption(alias = {"-v", "-vv", "-vvv"})
public class MainOptionLogDebug implements MainOptionHandler {

    @Override
    public void execute(Cli cli, String cmd, LinkedList<String> queue) {
        MApi.setDirtyTrace(cmd.equals("-vv"));
        MApi.get()
                .getLogFactory()
                .setDefaultLevel(cmd.equals("-vvv") ? Log.LEVEL.TRACE : Log.LEVEL.DEBUG);
    }

    @Override
    public String getDescription(String cmd) {
        return "Enable Log DEBUG/TRACE level.";
    }

    @Override
    public String getUsage(String cmd) {
        return null;
    }
}
