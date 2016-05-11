package com.github.marcelkoopman.models;


/**
 * Created by marcel on 29-4-16.
 */

public class MavenDependency implements Comparable<MavenDependency>{

    private String name;
    private String groupId;
    private String artifactId;
    private String version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

      public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public int compareTo(MavenDependency o) {
        final int equal;
        if (o == null)
        {
            equal = -1;
        } else
        {
            equal = this.getName().compareTo(o.getName());
        }
        return equal;
    }
}
