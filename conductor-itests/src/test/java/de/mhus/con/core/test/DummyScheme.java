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
package de.mhus.con.core.test;

import java.io.File;
import java.io.IOException;

import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Conductor;
import de.mhus.con.api.Scheme;
import de.mhus.lib.core.MFile;
import de.mhus.lib.core.util.MUri;

public class DummyScheme implements Scheme {

    @Override
    public File load(Conductor con, MUri uri) throws IOException {
        File f = ConUtil.createTempFile(con, DummyScheme.class, ".yml");
        MFile.writeFile(f, "");
        return f;
    }
}
