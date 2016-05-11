package com.github.marcelkoopman.models;


import java.util.Set;

/**
 * Created by marcel on 29-4-16.
 */

public class MavenProject {

    private Set<MavenDependency> dependencies;

    private String name, parent, version, description;

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

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
