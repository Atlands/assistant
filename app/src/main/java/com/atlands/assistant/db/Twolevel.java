package com.atlands.assistant.db;

import org.litepal.crud.LitePalSupport;

public class Twolevel extends LitePalSupport {
    private int id;
    private String name;
    private int oid;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }
}
