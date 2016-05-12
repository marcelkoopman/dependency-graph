package com.github.marcelkoopman;

import iot.jcypher.query.JcQueryResult;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by marcel on 29-4-16.
 */
public class NeoGraphFacadeTest {

    @Test
    public void testOneGraph() throws IOException, XmlPullParserException {
        final NeoGraphFacade facade = new NeoGraphFacade();
        final JcQueryResult result = facade.createGraph(new File("pom.xml"));
        assertEquals(result.getJsonResult().toString(), 0, result.getDBErrors().size());
    }

    @Test
    public void testTwoGraphs() throws IOException, XmlPullParserException {
        final NeoGraphFacade facade = new NeoGraphFacade();
        final JcQueryResult result = facade.createGraphs(Arrays.asList(new File("pom.xml"), new File("wgp.xml"), new File("wgpadmin.xml"), new File("giservices.xml")));
        assertEquals(result.getJsonResult().toString(), 0, result.getDBErrors().size());
    }
}
