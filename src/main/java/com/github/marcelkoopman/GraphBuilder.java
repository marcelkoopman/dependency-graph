package com.github.marcelkoopman;

import com.github.marcelkoopman.models.MavenDependency;
import com.github.marcelkoopman.models.MavenProject;
import com.google.common.base.CharMatcher;
import iot.jcypher.query.JcQuery;
import iot.jcypher.query.api.IClause;
import iot.jcypher.query.factories.clause.CREATE;
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

    public JcQuery build(final MavenProject proj) {
        final JcQuery query = new JcQuery();

        final List<IClause> clauses = new ArrayList<>();

        final JcNode n = new JcNode(getValidJcNodeName(proj.getName()));
        clauses.add(
                CREATE.node(n).label("project")
                        .property("title").value(proj.getName())
        );

        for (MavenDependency dep : proj.getDependencies()) {
            final JcNode depNode = new JcNode(getValidJcNodeName(dep.getName()));

            clauses.add(
                    CREATE.node(depNode).label("dependency")
                            .property("title").value(dep.getName()).relation().in().type("DEPENDENCY").node(n)
            );

            final JcNode depVersionNode = new JcNode(getValidJcNodeName("version_" + dep.getVersion()));
            clauses.add(
                    CREATE.node(depVersionNode).label("version")
                            .property("title").value(dep.getVersion()).relation().in().type("VERSION").node(depNode)
            );
        }


        final IClause[] clausesArr = new IClause[clauses.size()];
        query.setClauses(clauses.toArray(clausesArr));

        final String cypher = iot.jcypher.util.Util.toCypher(query, Format.PRETTY_3);
        LOG.info(cypher);

        return query;
    }

    private String getValidJcNodeName(final String name) {
        final String valid;
        if (name == null) {
            valid = "no_name";
        } else {
            valid = CharMatcher.is('-').or(CharMatcher.is('.')).or(CharMatcher.is(' ')).replaceFrom(name, "_");
        }
        return valid;
    }

}
