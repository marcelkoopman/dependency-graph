package com.github.marcelkoopman;

import com.github.marcelkoopman.models.MavenDependency;
import com.github.marcelkoopman.models.MavenProject;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by marcel on 3-5-16.
 */
public class GraphBuilder {

    private final NeoClient neoClient = new NeoClient();


    public MavenProject build(final File pomFile) throws IOException, XmlPullParserException {
        final MavenModelBuilder builder = new MavenModelBuilder();


        final Model model = builder.readPom(pomFile);
        final MavenProject proj = getMavenProject(model);

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
        proj.setDependencies(dependencies);
        return proj;
    }

    private MavenProject getMavenProject(Model model ) {
        final MavenProject proj = new MavenProject();
        proj.setName(model.getName());
        return proj;
    }


}
