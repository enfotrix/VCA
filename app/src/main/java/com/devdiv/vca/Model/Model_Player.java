package com.devdiv.vca.Model;

public class Model_Player {
    String name,catg;

    public Model_Player(String name, String catg) {
        this.name = name;
        this.catg = catg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatg() {
        return catg;
    }

    public void setCatg(String catg) {
        this.catg = catg;
    }
}
