package com.github.marcelkoopman;

import com.github.marcelkoopman.models.MavenDependency;
import com.github.marcelkoopman.models.MavenProject;
import com.google.common.base.CharMatcher;
import iot.jcypher.query.JcQuery;
import iot.jcypher.query.api.IClause;
import iot.jcypher.query.api.pattern.Node;
import iot.jcypher.query.factories.clause.MERGE;
import iot.jcypher.query.values.JcNode;
import iot.jcypher.query.writer.Format;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.CharSet;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marcel on 11-5-16.
 */
public class GraphBuilder {

    private static final Logger LOG = Logger.getLogger(GraphBuilder.class);

    public JcQuery buildOneQuery(final MavenProject... projects) {
        final JcQuery query = new JcQuery();
        final List<IClause> clauses = new ArrayList<>();
        for (MavenProject project : projects) {
            clauses.addAll(build(project));
        }
        final IClause[] clausesArr = new IClause[clauses.size()];
        query.setClauses(clauses.toArray(clausesArr));

        final String cypher = iot.jcypher.util.Util.toCypher(query, Format.PRETTY_3);
        try {
            final File file = File.createTempFile("cypher", "txt");
            FileUtils.writeStringToFile(file, cypher, "UTF-8");
            LOG.info(""+file.getAbsolutePath());
        } catch (Exception e) {
            LOG.error(e);
        }


        return query;
    }

    public Map<MavenProject, JcQuery> buildQueryList(final MavenProject... projects) {
        final Map<MavenProject, JcQuery> queries = new HashMap<>();
        for (MavenProject project : projects) {
            final JcQuery query = buildOneQuery(project);
            queries.put(project, query);
        }
        return queries;
    }

    private List<IClause> build(final MavenProject proj) {

        final List<IClause> clauses = new ArrayList<>();

        final JcNode mavenProjectNode = new JcNode("m"+getRandomString());

        final Node pomNode;
        if (proj.getParent() == null) {
            pomNode = MERGE.node(mavenProjectNode).label("project")
                    .property("title").value(proj.getName());
        } else {
            final JcNode mavenParentProjectNode = new JcNode("p"+getRandomString());

            clauses.add(
                    MERGE.node(mavenParentProjectNode).label("parent")
                            .property("title").value(proj.getParent())
            );
            pomNode = MERGE.node(mavenProjectNode).label("project")
                    .property("title").value(proj.getName())
                    .relation().out().type("HAS_PARENT").node(mavenParentProjectNode);
        }

        clauses.add(
                pomNode
        );

        for (MavenDependency dep : proj.getDependencies()) {
            final JcNode depNode = new JcNode("d"+getRandomString());

            clauses.add(
                    MERGE.node(depNode).label("dependency")
                            .property("title").value(dep.getName())
                            .property("groupid").value(dep.getGroupId())
                            .property("artifactid").value(dep.getArtifactId())
                            .property("version").value(dep.getVersion())
                            .relation().in().type("HAS_DEPENDENCY").node(mavenProjectNode)
            );
        }

        return clauses;
    }

    private String getRandomString() {
        return RandomStringUtils.randomAlphabetic(4);
    }
}
