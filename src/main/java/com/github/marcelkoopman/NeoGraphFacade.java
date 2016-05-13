package com.github.marcelkoopman;

import com.github.marcelkoopman.models.MavenProject;
import iot.jcypher.query.JcQuery;
import iot.jcypher.query.JcQueryResult;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marcel on 11-5-16.
 */
public class NeoGraphFacade {

    private final NeoClient client = new NeoClient();
    private final MavenProjectBuilder builder = new MavenProjectBuilder();
    private final GraphBuilder graphBuilder = new GraphBuilder();

    public void createGraphs(final List<File> pomFiles) throws IOException, XmlPullParserException {

        final MavenProject[] models = new MavenProject[pomFiles.size()];
        for (int i = 0; i < pomFiles.size(); ++i) {
            final MavenProject model = builder.build(pomFiles.get(i));
            models[i] = model;
        }
        final Map<MavenProject, JcQuery> result = graphBuilder.buildQueryList(models);
        result.forEach((mavenProject, jcQuery) ->
                createNewGraph(jcQuery)
        );
    }

    public JcQueryResult createGraph(final File pomFile) throws IOException, XmlPullParserException {

        final MavenProject model = builder.build(pomFile);
        final JcQuery query = graphBuilder.buildOneQuery(model);

        return createNewGraph(query);
    }

    private JcQueryResult createNewGraph(JcQuery query) {
        try {
            client.connect();
           // client.clearDatabase();

            final JcQueryResult result = client.executeQuery(query);
            return result;

        } finally {
            client.disconnect();
        }
    }

}
