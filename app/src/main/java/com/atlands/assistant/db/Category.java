package com.atlands.assistant.db;

import org.litepal.crud.LitePalSupport;

public class Category extends LitePalSupport {
    private int id;
    private String name;
    private int tid;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }
}
