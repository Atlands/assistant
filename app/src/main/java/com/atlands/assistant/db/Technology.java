package com.atlands.assistant.db;

import org.litepal.crud.LitePalSupport;

public class Technology extends LitePalSupport {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
