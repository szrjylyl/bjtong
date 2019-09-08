package com.pri.lon.boyin.model;

import java.io.Serializable;

/**
 * Created by Lyon on 18/1/6.
 */

public class Video implements Serializable {

    public String des;
    public String url;

    @Override
    public String toString() {
        return "Video{" +
                "des='" + des + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
