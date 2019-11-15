package com.example.catpokedex;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Cat {
    public String name;
    private String description;
    private Weight weight;
    private String temperament;
    private String origin;
    private String life_span;
    private String wikipedia_url;
    private String dog_friendly;
    private String id;
    private String url;



    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getWeight() {
        return weight.getMetric();
    }

    public String getTemperament() {
        return temperament;
    }

    public String getOrigin() {
        return origin;
    }

    public String getLife_span() {
        return life_span;
    }

    public String getWikipedia_url() {
        return wikipedia_url;
    }

    public String getDog_friendly() {
        return dog_friendly;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}


class Weight {
    String metric;
    String imperial;

    public String getImperial() {
        return imperial;
    }

    public String getMetric() {
        return metric;
    }
}
