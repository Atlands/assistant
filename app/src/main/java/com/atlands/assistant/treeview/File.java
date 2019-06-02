package com.atlands.assistant.treeview;

import com.atlands.assistant.R;

import tellh.com.recyclertreeview_lib.LayoutItemType;


public class File implements LayoutItemType {
    public String fileName;

    public File(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_file;
    }
}
