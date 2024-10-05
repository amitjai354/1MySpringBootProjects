package com.example.AmitSbTcsJwtV3Project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String productName;
    private Double price;

    @ManyToOne()
    @JoinColumn(name = "seller_id", referencedColumnName = "userId", updatable = false)
    @JsonIgnore
    private User seller;
    //here up[datable is false so no need to pass seller object while trying to crerte Product object

    //Unidirectional Relationship, using cascade, any changes made to Product willl propagate to Category
    //cascade we must use as if this category does not exist, but if exist then need to do some if check herer
    //@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})//cascade = CascadeType.MERGE)
    @ManyToOne()
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private Category category;
    //one category has many products so should not create FK Product id in category as thgis will be list of products
    //entity which is one sg category should br FK in many eg Product
    //categoryId will be a column in Product table.. in bidirectional
    //But in Category table we will have List<Product> this productList will not be a column in Category table..
    //give mapped by where you do not wa2nt to create fk and give join column where you want to give fk


    public Product() {
    }

    public Product(Integer productId, String productName, Double price, User seller, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.seller = seller;
        this.category = category;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
