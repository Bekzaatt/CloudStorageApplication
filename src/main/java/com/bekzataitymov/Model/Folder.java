package com.bekzataitymov.Model;

import lombok.Data;

@Data
public class Folder {
    private String path;
    private String name;
    private TYPE type;

    public Folder(String path, String name, TYPE type){
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

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }
}
