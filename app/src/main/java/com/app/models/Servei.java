package com.app.models;

import org.json.JSONArray;

public class Servei {

    Integer id;
    String location;
    String name;
    String icon;
    String author;
    Integer distance;
    Integer ownerId;
    JSONArray tags;
    String tag;
    String[] photos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public Number getDistance(double distance) {
        return this.distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public JSONArray getTags() {
        return tags;
    }

    public void setTags(JSONArray tags) {
        this.tags = tags;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public Object getDistance(String distance) {
        Integer search_distance = Integer.parseInt(distance);
        return search_distance;
    }

    public Object getDistance() {
        return distance;
    }


}
