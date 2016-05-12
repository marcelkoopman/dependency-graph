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
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcel on 11-5-16.
 */
public class GraphBuilder {

    private static final Logger LOG = Logger.getLogger(GraphBuilder.class);

    public JcQuery buildQuery(final MavenProject... projects) {
        final JcQuery query = new JcQuery();
        final List<IClause> clauses = new ArrayList<>();
        for (MavenProject project : projects) {
            clauses.addAll(build(project));
        }
        final IClause[] clausesArr = new IClause[clauses.size()];
        query.setClauses(clauses.toArray(clausesArr));

        final String cypher = iot.jcypher.util.Util.toCypher(query, Format.PRETTY_3);
        LOG.info(cypher);

        return query;
    }

    private List<IClause> build(final MavenProject proj) {

        final List<IClause> clauses = new ArrayList<>();

        final JcNode mavenProjectNode = new JcNode("proj_"+getValidJcNodeName(proj.getName()));

        final Node pomNode;
        if (proj.getParent() == null) {
            pomNode = MERGE.node(mavenProjectNode).label("project")
                    .property("title").value(proj.getName());
        } else {
            final JcNode mavenParentProjectNode = new JcNode(getValidJcNodeName("parent_" + proj.getName() + proj.getParent()));

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
            final JcNode depNode = new JcNode("dep_"+getValidJcNodeName(dep.getName()));

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

    private String getValidJcNodeName(final String name) {
        final String valid;
        if (name == null) {
            valid = "no_name";
        } else {
            System.err.println(name);
            valid = CharMatcher.is('-').or(CharMatcher.is('.')).or(CharMatcher.is(' ')).or(CharMatcher.is('$')).or(CharMatcher.is('{')).or(CharMatcher.is('}')).replaceFrom(name, "_");
        }
        return valid;
    }

}
