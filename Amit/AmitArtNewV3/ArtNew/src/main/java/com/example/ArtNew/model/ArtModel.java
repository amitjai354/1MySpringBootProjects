package com.example.ArtNew.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class ArtModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(unique = true)
    private String title;
    private String description;
    private String artist;
    private String medium;
    private double price;
    private boolean isAvailable = true;
    private String dimension;
    private String imageLink;

    private int ownerId;

    @ManyToOne
    @JoinColumn(name = "owner_id_fk", referencedColumnName = "id")
    @JsonIgnore
    private UserModel userModel;


    public ArtModel() {
    }

    public ArtModel(String title, String description, String artist, String medium, double price, boolean isAvailable, String dimension, String imageLink, int ownerId) {
        this.title = title;
        this.description = description;
        this.artist = artist;
        this.medium = medium;
        this.price = price;
        this.isAvailable = isAvailable;
        this.dimension = dimension;
        this.imageLink = imageLink;
        this.ownerId = ownerId;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

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

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
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

    @Override
    public String toString() {
        return "ArtModel{" +
                "ownerId=" + ownerId +
                ", imageLink='" + imageLink + '\'' +
                ", dimension='" + dimension + '\'' +
                ", isAvailable=" + isAvailable +
                ", price=" + price +
                ", medium='" + medium + '\'' +
                ", artist='" + artist + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", id=" + id +
                '}';
    }
}
