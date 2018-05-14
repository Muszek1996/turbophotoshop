package com.tps.turbophotoshop.web;

import java.io.Serializable;

public class ImageDimensions implements Serializable {
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    ImageDimensions(int width, int height){
        this.height = height;
        this.width = width;
    }

    int width,height;
}
