package com.example.uploadingfiles.fileParsing;

import java.util.ArrayList;

public class CoverageGroup {
    private String name;
    private ArrayList<Parameter> list;

    public CoverageGroup(String name, ArrayList<Parameter> list) {
        this.name = name;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Parameter> getList() {
        return list;
    }

    public void setList(ArrayList<Parameter> list) {
        this.list = list;
    }
}
