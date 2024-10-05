package com.example.AmitSbTcsJwtV3Project.controller;

import com.example.AmitSbTcsJwtV3Project.config.JwtUtil;
import com.example.AmitSbTcsJwtV3Project.model.Category;
import com.example.AmitSbTcsJwtV3Project.model.Product;
import com.example.AmitSbTcsJwtV3Project.model.User;
import com.example.AmitSbTcsJwtV3Project.repository.CategoryRepo;
import com.example.AmitSbTcsJwtV3Project.repository.ProductRepo;
import com.example.AmitSbTcsJwtV3Project.repository.UserRepo;
import com.example.AmitSbTcsJwtV3Project.service.UserAuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SellerController {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    JwtUtil jwtUtil;

    //{output seller is ignored in json response
    //    "productId": 3,
    //    "productName": "iPhone 12 Pro Max",
    //    "price": 98000.0,
    //    "category": {
    //        "categoryName": "Electronics"
    //    }
    //}
    @PostMapping("/product")
    public ResponseEntity<Object> postProduct(@RequestBody Product product){
        //whatever input taking from json, creating object of that first then passing to db for saving
        //creating Product request class and inside this creating to() to build object
        //then save(productRequest.to());
        //Lecture 13 end Lecture 14 staRT, writing biderectional part in lec13 and create api in lect14
        //since we are not creatring any other api to save categor so we will take category in request when creating product
        //we will check some unique parameter of category to if it already exists or not, we will not use id as
        //do not which id for whixh category so use category name as unique
        //if it exits we wil;l not create new category, instaed attach category id to product
        //if exists we will take category object from db , not the one that was passed in input as this is transient state
        //find using category name


        //in product request creating builder to() for product but first creating object of category from input passeed
        //then product.setCategory() while creating object of product then passing to db
        //not passing request from api directly to db.. referencing trasient object error.. transirent means completely new
        //does not belong to any db table.. transient

        //Eager means fectch and stores in memory on system start up,, lazy means when call only then fetch

        //object refrences an unsaved transient instance even in Lecture 14 at 11:06, no error in syntax, this is db exception
        //this error comes because of foreign key constraint: having Fk as null is not an issue, but having FK that is not
        //present as Pk in parent table is an issue.. eg if we pass category as nnull it will save product no error
        //but if we pass category and that category does not exist in parent category table is an issue
        //if we are passing category id as 3, it should be present in category table
        //in lecture not passing author id so itself passing authorId as 0 because id is int, but no auythor id 0 exist in
        //author table so when tries to find author id 0.. error unsaved transient instance
        //initially both book id and author id are passed as 0 by default, book id will be auto inc internally as biggest id seen so far+1
        // as we are doing save book but author id will not be auto inc so that is why we need to save autghor first
        //and if author is saved and we are not retieving the author refernce which is already saved, we will get error :
        //referencing unsaved transient.. lect 14  15:19 : we need to save or get already existing author Lecdt14 33:28

        //on debug i found that in product for category I am getting only categoryName from json, I am getting categoryId as null
        //because this is Integer, it is not taking categoryId that I am passing, may be due to auto inc or what
        //this is due to json ignore that we have put on categoryId . ignoring in json input and output both
        //if comment json ignor on category id in categy class, then working completely fine, no need to get category from db
        //even no need to use bidirectional, based on categoryid, hibernate automatically associating oroduct to this category
        //only thing is if this does not exist then we need to save category first

        // nut when ignpore category id in input and output, we need to get category based on category name then set this
        //cateogy in product then product willl have category id as well now it will work..
        //even when ignoriing category id, this new code workinmg without bidirectional relation, we need birectional
        //in category incase if we want to see products as well when fetching category, but we need not that so no need of
        //bidirectional in category,
        //also no  need of cascade or transactional

        try{
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            //String username = jwtUtil.getUserNameFromToken()
            User user = userRepo.findByUsername(username);
            product.setSeller(user);
            //product.setSeller(new User());
            Category categoryFromDb = categoryRepo.findByCategoryName(product.getCategory().getCategoryName()).orElse(null);
            if(categoryFromDb==null){
                Category category = new Category();
                category.setCategoryName(product.getCategory().getCategoryName());
                //id we will not save as id auto incremented in category
                categoryFromDb=categoryRepo.save(category);
            }

//            Product productToInsert = new Product();
//            productToInsert.setCategory(categoryFromDb);
//            productToInsert.setProductName(product.getProductName());
//            productToInsert.setPrice(product.getPrice());
            product.setCategory(categoryFromDb);//this is also working
            //return ResponseEntity.ok(productRepo.saveAndFlush(product));
            return ResponseEntity.ok(productRepo.save(product));
            //saveandflush immediately save data
            //should we check if product already exists for given product id and seller id, if product not exists then
            //save product othjeriwse error, seller id will not be blank so while checking product id check seller id also
            //and also there are 2 sellers so each seller can save same product but for this primary key should include
            //seller id as well
            //there is one query findbyselller id and product id what is use of that
        }
        catch (Exception e){
            e.printStackTrace();
            //this is must to give othedrwise only 400 response but what is error no idera, can see in debug only
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
        }
        /*
        {
    "productId":3,
    "category":{
        "categoryName":"Electronics",
        "categoryId":2
    },
    "price":98000.0,
    "productName":"iPhone 12 Pro Max"
}
         */
    }

    @GetMapping("/product")
    public ResponseEntity<Object> getAllProduct(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User myUser=userRepo.findByUsername(username);
        List<Product> allProduct = productRepo.findBySellerUserId(myUser.getUserId());
        if(allProduct.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(allProduct);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Object> getProduct(@PathVariable("productId") int productId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User myUser = userRepo.findByUsername(username);
        //if use findById then user can see products of other users also, this is not right
        //seller should be able to see product by id only from the products which he added
        if(productRepo.findBySellerUserIdAndProductId(myUser.getUserId(),productId).isPresent()){
            return ResponseEntity.ok(productRepo.findBySellerUserIdAndProductId(myUser.getUserId(),productId).get());
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/product")
    public ResponseEntity<Object> putProduct(@RequestBody Product product){
        try{
            if (product.getProductId()==null){
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
            }
            else {
                String username=SecurityContextHolder.getContext().getAuthentication().getName();
                User myUser=userRepo.findByUsername(username);
                Product productFromDb=productRepo.findBySellerUserIdAndProductId(myUser.getUserId(), product.getProductId()).orElse(null);
                if (productFromDb==null){
                    productFromDb=new Product();
                }
                productFromDb.setProductId(product.getProductId());
                productFromDb.setProductName(product.getProductName());
                productFromDb.setPrice(product.getPrice());
                productFromDb.setSeller(myUser);

                Category categoryFromDb=categoryRepo.findByCategoryName(product.getCategory().getCategoryName()).orElse(null);
                if (categoryFromDb==null) {
                    categoryFromDb = categoryRepo.save(product.getCategory());
                }
                productFromDb.setCategory(categoryFromDb);
                productFromDb=productRepo.save(productFromDb);
                return ResponseEntity.ok(productFromDb);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
        }

    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("productId") int productId){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        User myUser = userRepo.findByUsername(username);
        Product productFromDb=productRepo.findBySellerUserIdAndProductId(myUser.getUserId(), productId).orElse(null);
        if (productFromDb==null){
            return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).build();
        }
        else {
            productRepo.deleteById(productId);
            return ResponseEntity.ok().build();
        }
    }
}
