package com.bekzataitymov.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Resource {
    private String path;
    private String name;
    private byte size;
    private TYPE type;

    public Resource(String path, String name, byte size, TYPE type) {
        this.path = path;
        this.name = name;
        this.size = size;
        this.type = type;
    }

    public Resource(String path, String name, TYPE type){
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

    public byte getSize() {
        return size;
    }

    public void setSize(byte size) {
        this.size = size;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }
}
