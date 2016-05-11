package com.github.marcelkoopman;

import com.github.marcelkoopman.models.MavenDependency;
import com.github.marcelkoopman.models.MavenProject;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by marcel on 3-5-16.
 */
public class MavenProjectBuilder {

    private final MavenModelBuilder modelBuilder = new MavenModelBuilder();

    public MavenProject build(final File pomFile) throws IOException, XmlPullParserException {

        final Model model = modelBuilder.readPom(pomFile);

        final List<Dependency> deps = model.getDependencies();
        final Set<MavenDependency> dependencies = new TreeSet();
        for (Dependency dep : deps) {
            final MavenDependency mavenDep = new MavenDependency();
            mavenDep.setArtifactId(dep.getArtifactId());
            mavenDep.setGroupId(dep.getGroupId());
            mavenDep.setVersion(dep.getVersion());
            mavenDep.setName(dep.getArtifactId());
            dependencies.add(mavenDep);
        }

        final MavenProject project = new MavenProject();
        final String name;
        if (model.getName() == null) {
            name = "no_name";
        } else {
            name = model.getName();
        }
        project.setName(name);
        project.setVersion(model.getVersion());
        project.setDescription(model.getDescription());
        project.setDependencies(dependencies);
        final Parent parent = model.getParent();
        if (parent != null) {
            project.setParent(parent.getArtifactId());
        }
        return project;
    }

}
