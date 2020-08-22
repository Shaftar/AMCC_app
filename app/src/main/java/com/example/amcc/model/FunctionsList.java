package com.example.amcc.model;

public class FunctionsList {

    private int name;
    private int info;
    private int image;

    public FunctionsList(int name, int info, int image) {
        this.name = name;
        this.info = info;
        this.image = image;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
