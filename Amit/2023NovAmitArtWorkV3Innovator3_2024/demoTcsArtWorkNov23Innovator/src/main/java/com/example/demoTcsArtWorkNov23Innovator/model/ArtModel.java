package com.example.demoTcsArtWorkNov23Innovator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
//@Table(indexes = @ForeignKey(value = ConstraintMode.CONSTRAINT(foreignKeyDefinition = "ownerId")))
public class ArtModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String title;
    private String description;
    private String artist;
    private String medium;
    private double price;
    private boolean isAvailable = true;
    private String dimensions;
    private String imageLink;

    //@Column()//no any field for fk
//    @OneToOne
    //@JoinColumn(name = "ownerId", referencedColumnName = "id")//no error but no FK in db if write this alone
    //@JoinColumn(referencedColumnName = "id")
    //@JoinColumn(foreignKey = @ForeignKey(foreignKeyDefinition = "id"))
    //@JoinColumn(foreignKey = @ForeignKey(UserModel))
    //@JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name = "FK_ART_USER"))//Fk not creating
    //@Column()//FK not creating


//    @ManyToOne
//    @JoinColumn(name = "Owner_Id_Fk", referencedColumnName = "Id")//be default name is user_Model_id
//    //@JsonIgnoreProperties({"email", "username", "isAvailable"})
//    @JsonIgnore
//    private UserModel userModel;
    //in constructor no need to add userModel as we are saving userModel with userRepo
    //just setter getter we need
    //now fk owner_id_fk is added in the Art Model and output we will return in ownerId
    //we need to add ownerId attribute as well because, if return usermodel.. then Art response is:
    //UserModel{
    //    "ID":1
    //}
    ///but we need ; ownerId:1

    private int ownerId;


    //private UserModel Ownner;//can not write this 2we hav egetter setter for owner id
    //unable to start tomcat if write join column at int,
    //also if do not write then in db FK constraint not showing
    //this int owner id they have given himself
    //here this is: int owner id not as private UserModel ownerId;
    //so can not use @One to one or join column, it should be some class to use these
    //But how it is foreign key then..
    //in java code when saving art model, I am getting user by owner id from db
    //in product.java :
    //@ManyToOne()
    //    @JoinColumn(name = "seller_id", referencedColumnName = "userId", updatable = false)
    //    @JsonIgnore
    //    private User seller;
    //    //here up[datable is false so no need to pass seller object while trying to crerte Product object
    //
    //    //Unidirectional Relationship, using cascade, any changes made to Product willl propagate to Category
    //    //cascade we must use as if this category does not exist, but if exist then need to do some if check herer
    //    //@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})//cascade = CascadeType.MERGE)
    //    @ManyToOne()
    //    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    //    private Category category;

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

//    public UserModel getUserModel() {
//        return userModel;
//    }
//
//    public void setUserModel(UserModel userModel) {
//        this.userModel = userModel;
//    }

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
