package com.bekzataitymov.Model;

import lombok.Data;
@Data
public class Folder {
    private String path;
    private String name;
    private Type type;

    public Folder(String path, String name, Type type){
        this.path = path;
        this.name = name;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
