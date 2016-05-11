package com.github.marcelkoopman;


import com.github.marcelkoopman.models.MavenDependency;
import com.github.marcelkoopman.models.MavenProject;
import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import iot.jcypher.database.DBAccessFactory;
import iot.jcypher.database.DBProperties;
import iot.jcypher.database.DBType;
import iot.jcypher.database.IDBAccess;
import iot.jcypher.query.JcQuery;
import iot.jcypher.query.JcQueryResult;
import iot.jcypher.query.api.IClause;
import iot.jcypher.query.factories.clause.CREATE;
import iot.jcypher.query.values.JcNode;
import org.codehaus.plexus.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by marcel on 29-4-16.
 */
public class NeoClient {


    private IDBAccess remote;

    public IDBAccess connect() {
        final Properties props = new Properties();
        props.setProperty(DBProperties.SERVER_ROOT_URI, "http://localhost:7474");
        props.setProperty(DBProperties.DATABASE_DIR, "neo4j.db");


        final String userId = "neo4j";
        final String passWord = "admin";

        this.remote =
                DBAccessFactory.createDBAccess(DBType.REMOTE, props, userId, passWord);


        return remote;
    }

    public JcQueryResult build(final MavenProject proj) {
        final JcQuery query = new JcQuery();

        final List<IClause> clauses = new ArrayList<>();

        final JcNode n = new JcNode(getValidCypherName(proj.getName()));
        clauses.add(
                CREATE.node(n).label("project")
                        .property("title").value(getValidCypherName(proj.getName()))
        );

        for (MavenDependency dep : proj.getDependencies()) {
            final JcNode depNode = new JcNode(getValidCypherName(dep.getName()));

            clauses.add(
                    CREATE.node(depNode).label("dependency")
                            .property("title").value(getValidCypherName(dep.getName())).relation().in().type("HAS_DEPENDENCY").node(n)
            );
        }


        final IClause[] clausesArr = new IClause[clauses.size()];
        query.setClauses(clauses.toArray(clausesArr));
        final JcQueryResult result = remote.execute(query);
        return result;
    }

    public void clearDatabase() {
        remote.clearDatabase();
    }

    public void disconnect() {
        remote.close();
    }

    private String getValidCypherName(final String name) {
        final String valid = CharMatcher.is('-').or(CharMatcher.is('.')).replaceFrom(name, "_");
        return valid;
    }
}