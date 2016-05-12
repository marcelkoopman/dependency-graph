package com.github.marcelkoopman;

import iot.jcypher.graph.Graph;
import iot.jcypher.query.JcQueryResult;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by marcel on 29-4-16.
 */
public class NeoGraphFacadeTest {

    private static final String RESOURCE_PATH = "src\\test\\resources\\";

    @Ignore
    @Test
    public void testOneGraph() throws IOException, XmlPullParserException {
        final NeoGraphFacade facade = new NeoGraphFacade();
        final JcQueryResult result = facade.createGraph(new File("pom.xml"));
        assertEquals(result.getJsonResult().toString(), 0, result.getDBErrors().size());
    }

    @Test
    public void testFiles() throws IOException {
        final Collection<File> files = FileUtils.listFiles(new File("E:\\Dev\\git"), FileFilterUtils.nameFileFilter("pom.xml"), new IOFileFilter() {
            @Override
            public boolean accept(final File file) {
                return true;
            }

            @Override
            public boolean accept(final File file, final String s) {
                return true;
            }
        });
        System.err.println(files);
        assertEquals(10, files.size());
    }

    @Ignore
    @Test
    public void testTwoGraphs() throws IOException, XmlPullParserException {
        final NeoGraphFacade facade = new NeoGraphFacade();

        final List<File> files = Arrays.asList(new File(RESOURCE_PATH + "csv.xml"), new File(RESOURCE_PATH + "wgp.xml"), new File(RESOURCE_PATH + "wgpadmin.xml"));
        final JcQueryResult result = facade.createGraphs(files);
        assertEquals(result.getJsonResult().toString(), 0, result.getDBErrors().size());
        final Graph graph = result.getGraph();
    }
}
