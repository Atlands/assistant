package com.atlands.assistant.db;

import org.litepal.crud.LitePalSupport;

public class Onelevel extends LitePalSupport {
    private int id;
    private String name;
    private int cid;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
