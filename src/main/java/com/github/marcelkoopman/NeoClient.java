package com.github.marcelkoopman;


import iot.jcypher.database.DBAccessFactory;
import iot.jcypher.database.DBProperties;
import iot.jcypher.database.DBType;
import iot.jcypher.database.IDBAccess;
import iot.jcypher.query.JcQuery;
import iot.jcypher.query.JcQueryResult;

import java.util.Properties;

/**
 * Created by marcel on 29-4-16.
 */
public class NeoClient {

    private IDBAccess remote;

    public IDBAccess connect() {
        final Properties props = new Properties();
        props.setProperty(DBProperties.SERVER_ROOT_URI, "http://localhost:7474");
        props.setProperty(DBProperties.DATABASE_DIR, "E:\\projects\\neo\\db2");

        final String userId = "neo4j";
        final String passWord = "admin";

        this.remote =
                DBAccessFactory.createDBAccess(DBType.REMOTE, props, userId, passWord);

        return remote;
    }

    public JcQueryResult executeQuery(final JcQuery query) {
        final JcQueryResult result = remote.execute(query);
        return result;
    }

    public void clearDatabase() {
        remote.clearDatabase();
    }

    public void disconnect() {
        remote.close();
    }
}
