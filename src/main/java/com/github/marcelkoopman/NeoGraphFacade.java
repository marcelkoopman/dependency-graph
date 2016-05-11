package com.github.marcelkoopman;

import com.github.marcelkoopman.models.MavenProject;
import iot.jcypher.query.JcQuery;
import iot.jcypher.query.JcQueryResult;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.IOException;

/**
 * Created by marcel on 11-5-16.
 */
public class NeoGraphFacade {

    private final NeoClient client = new NeoClient();
    private final MavenProjectBuilder builder = new MavenProjectBuilder();
    private final GraphBuilder graphBuilder = new GraphBuilder();

    public JcQueryResult createSinglePomGraph(final File pomFile) throws IOException, XmlPullParserException {

        final MavenProject model = builder.build(pomFile);
        final JcQuery query = graphBuilder.build(model);

        try {
            client.connect();
            client.clearDatabase();

            final JcQueryResult result = client.executeQuery(query);
            return result;

        } finally {
            client.disconnect();
        }
    }

}
