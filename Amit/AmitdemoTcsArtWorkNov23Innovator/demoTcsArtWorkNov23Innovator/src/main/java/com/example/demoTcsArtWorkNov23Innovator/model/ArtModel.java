package com.example.demoTcsArtWorkNov23Innovator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ArtModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private String artist;
    private String medium;
    private double price;
    private boolean isAvailable=true;
    private String dimensions;
    private String imageLink;
    private int ownerId;

    //constructor

    public ArtModel() {
    }

    public ArtModel(int id, String title, String description, String artist, String medium, double price, boolean isAvailable, String dimensions, String imageLink, int ownerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.artist = artist;
        this.medium = medium;
        this.price = price;
        this.isAvailable = isAvailable;
        this.dimensions = dimensions;
        this.imageLink = imageLink;
        this.ownerId = ownerId;
    }

    public ArtModel(String title, String description, String artist, String medium, double price, String dimensions, String imageLink) {
        this.title = title;
        this.description = description;
        this.artist = artist;
        this.medium = medium;
        this.price = price;
        this.dimensions = dimensions;
        this.imageLink = imageLink;
    }

    public ArtModel(double price, boolean isAvailable) {
        this.price = price;
        this.isAvailable = isAvailable;
    }

    //getter and setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    //to string

    @Override
    public String toString() {
        return "ArtModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", artist='" + artist + '\'' +
                ", medium='" + medium + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", dimensions='" + dimensions + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }
}
