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
package de.mhus.lib.versioning;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.mhus.lib.core.MXml;

// Sets the version of the parent pom and parameters or dependencies to new values. Is able to count
// up/down the version strings

// needs a config for the version hop. Writes a temp file for SetVersionsMojo

@Mojo(
        name = "modify-versions",
        defaultPhase = LifecyclePhase.PROCESS_CLASSES,
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME,
        inheritByDefault = false)
public class ModifyVersionsMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}")
    protected MavenProject project;

    @Parameter(defaultValue = "../versions.xml")
    protected String versions;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            Document versionsDoc = MXml.loadXml(new File(versions));
            Element versionsE = versionsDoc.getDocumentElement();

            //        } catch (MojoExecutionException e) {
            //            throw e;
            //        } catch (MojoFailureException e) {
            //            throw e;
        } catch (Exception e) {
            throw new MojoExecutionException("", e);
        }
    }
}
