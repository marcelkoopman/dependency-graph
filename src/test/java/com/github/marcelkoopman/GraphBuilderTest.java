package com.github.marcelkoopman;

import com.github.marcelkoopman.models.MavenProject;
import iot.jcypher.database.IDBAccess;
import iot.jcypher.query.JcQueryResult;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by marcel on 29-4-16.
 */
public class GraphBuilderTest {

    @Test
    public void testBuild() {
        NeoClient client = new NeoClient();
        GraphBuilder builder = new GraphBuilder();
        MavenProject model = builder.build(new File("pom.xml"));

        try {
            client.connect();
            client.clearDatabase();
            JcQueryResult result = client.build(model);
            assertEquals("Errors gevonden: "+result.getJsonResult().getJsonArray("errors"), 0, result.getJsonResult().getJsonArray("errors").size());
        } finally {
            client.disconnect();
        }
    }
}
