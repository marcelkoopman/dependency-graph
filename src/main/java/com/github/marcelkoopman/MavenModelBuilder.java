package com.github.marcelkoopman;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by marcel on 29-4-16.
 */
public class MavenModelBuilder {

    public Model readPom(final File pomfile) throws IOException, XmlPullParserException {
        final MavenXpp3Reader reader = new MavenXpp3Reader();
        final Model model = reader.read(new FileReader(pomfile));
        return model;
    }
}
