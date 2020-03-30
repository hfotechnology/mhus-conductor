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

// Sets the version of the parent pom and parameters or dependencies to new values. Is able to count up/down the version strings

// needs a config for the version hop. Writes a temp file for SetVersionsMojo

@Mojo(
        name = "modify-versions",
        defaultPhase = LifecyclePhase.PROCESS_CLASSES, 
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME, 
        inheritByDefault = false
    )
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
            throw new MojoExecutionException("",e);
        }
    }

}
