package com.github.marcelkoopman;

import iot.jcypher.query.JcQueryResult;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by marcel on 29-4-16.
 */
public class NeoGraphFacadeTest {

    @Test
    public void testBuild() throws IOException, XmlPullParserException {
        final NeoGraphFacade facade = new NeoGraphFacade();
        final JcQueryResult result = facade.createSinglePomGraph(new File("pom.xml"));
        assertEquals(0, result.getDBErrors().size());
    }
}
