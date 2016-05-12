package com.github.marcelkoopman;

import iot.jcypher.query.JcQueryResult;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by marcel on 29-4-16.
 */
public class NeoGraphFacadeTest {

    private static final String RESOURCE_PATH = "src\\test\\resources\\";

    @Test
    public void testOneGraph() throws IOException, XmlPullParserException {
        final NeoGraphFacade facade = new NeoGraphFacade();
        final JcQueryResult result = facade.createGraph(new File("pom.xml"));
        assertEquals(result.getJsonResult().toString(), 0, result.getDBErrors().size());
    }

    @Test
    public void testTwoGraphs() throws IOException, XmlPullParserException {
        final NeoGraphFacade facade = new NeoGraphFacade();
        final List<File> files = Arrays.asList(new File(RESOURCE_PATH + "csv.xml"), new File(RESOURCE_PATH + "wgp.xml"), new File(RESOURCE_PATH + "wgpadmin.xml"));
        final JcQueryResult result = facade.createGraphs(files);
        assertEquals(result.getJsonResult().toString(), 0, result.getDBErrors().size());
    }
}
