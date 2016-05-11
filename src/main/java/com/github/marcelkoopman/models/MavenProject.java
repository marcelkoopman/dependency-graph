package com.github.marcelkoopman.models;


import java.util.Set;

/**
 * Created by marcel on 29-4-16.
 */

public class MavenProject {

    private Set<MavenDependency> dependencies;

    private String name;

    public Set<MavenDependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Set<MavenDependency> dependencies) {
        this.dependencies = dependencies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
