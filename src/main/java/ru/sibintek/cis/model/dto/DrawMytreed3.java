package ru.sibintek.cis.model.dto;

import java.util.List;

public class DrawMytreed3 {
    private String name;
    private int size;
    private String url;
    private List<DrawMytreed3> children;

    public List<DrawMytreed3> getChildren() {
        return children;
    }

    public void setChildren(List<DrawMytreed3> children) {
        this.children = children;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
