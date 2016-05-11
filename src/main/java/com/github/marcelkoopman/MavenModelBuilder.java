package com.github.marcelkoopman;

import org.apache.maven.model.Model;
import org.apache.maven.model.building.DefaultModelBuilderFactory;
import org.apache.maven.model.building.DefaultModelBuildingRequest;
import org.apache.maven.model.building.ModelBuilder;
import org.apache.maven.model.building.ModelBuildingRequest;

import java.io.File;

/**
 * Created by marcel on 29-4-16.
 */
public class MavenModelBuilder {

    public Model readPom(final File pomfile) {
        final ModelBuilder builder = new DefaultModelBuilderFactory().newInstance();
        try {
            return builder.build(makeModelBuildRequest(pomfile)).getEffectiveModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ModelBuildingRequest makeModelBuildRequest(File artifactFile) {
        DefaultModelBuildingRequest mbr = new DefaultModelBuildingRequest();
        mbr.setPomFile(artifactFile);
        mbr.setModelResolver(new DefaultModelResolver());

        return mbr;
    }
}
