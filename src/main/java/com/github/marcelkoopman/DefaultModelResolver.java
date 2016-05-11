package com.github.marcelkoopman;

import org.apache.maven.model.Parent;
import org.apache.maven.model.Repository;
import org.apache.maven.model.building.ModelSource;
import org.apache.maven.model.resolution.InvalidRepositoryException;
import org.apache.maven.model.resolution.ModelResolver;
import org.apache.maven.model.resolution.UnresolvableModelException;

/**
 * Created by marcel on 29-4-16.
 */
public class DefaultModelResolver implements ModelResolver {

    @Override
    public ModelSource resolveModel(String s, String s1, String s2) throws UnresolvableModelException {
        return null;
    }

    @Override
    public ModelSource resolveModel(Parent parent) throws UnresolvableModelException {
        return null;
    }

    @Override
    public void addRepository(Repository repository) throws InvalidRepositoryException {

    }

    @Override
    public void addRepository(Repository repository, boolean b) throws InvalidRepositoryException {

    }

    @Override
    public ModelResolver newCopy() {
        return null;
    }
}
