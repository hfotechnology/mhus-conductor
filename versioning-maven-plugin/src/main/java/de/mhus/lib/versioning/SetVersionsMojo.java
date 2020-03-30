package de.mhus.lib.versioning;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

// Sets the version of the parent pom and parameters or dependencies to new values. Is able to count up/down the version strings

// needs a temporarely config file with new version strings.

@Mojo(
        name = "set-versions",
        defaultPhase = LifecyclePhase.PROCESS_CLASSES, 
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME, 
        inheritByDefault = false
    )
public class SetVersionsMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}")
    protected MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        
        
        
        
    }

}
