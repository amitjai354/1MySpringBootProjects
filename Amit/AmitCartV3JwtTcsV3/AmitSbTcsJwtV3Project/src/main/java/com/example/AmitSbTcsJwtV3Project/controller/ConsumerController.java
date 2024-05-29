package com.example.AmitSbTcsJwtV3Project.controller;

import com.example.AmitSbTcsJwtV3Project.model.*;
import com.example.AmitSbTcsJwtV3Project.repository.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/auth/consumer")
public class ConsumerController {

    @Autowired
    CartRepo cartRepo;

    @Autowired
    CartProductRepo cartProductRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CategoryRepo categoryRepo;

    //{ output
    //    "cpId": 2,
    //    "product": {
    //        "productId": 3,
    //        "productName": "iPhone 12 Pro Max",
    //        "price": 98000.0,
    //        "category": {
    //            "categoryName": "Electronics"
    //        }
    //    },
    //    "quantity": 1
    //}

    @GetMapping("/cart")
    public ResponseEntity<Object> getCart(){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        Cart cartFromDb=cartRepo.findCartByUserUsername(username).orElse(null);
        if(cartFromDb==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(cartFromDb);
    }

    @PostMapping("/cart")
    public ResponseEntity<Object> postCart(@RequestBody Product product){
        try{
            //first of all we need to check if this product already in cart means if already in cart product, then do not add
            //since category id is ignored we need to look by categoryName, but we have to find product so can find by
            //product id no need of category id, we have product id in json and in cart product also
            //Actually cartProduct has both product and cart and cart has user id so we need to find by cartuserid and
            //productProductId, because we have to finally check if product exists in cart, so we need to check both
            //if product exists and this cart exists for logged in user, in cart we have only user id and amount
            //in cart product we have cart id, prooduct id, quantity
            //or can we do this instaed of writing one query, we can find product in cart product first and then find
            //find cart fror loggrd in user, if cart exists or not, if not need to create new cart then

            //check if product exist in cart product
            //check if cart exists for logged in user, find cart by user name

            String username= SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepo.findByUsername(username);

            Product productFromDb=productRepo.findById(product.getProductId()).orElse(null);
            if(productFromDb==null){
//            Category categoryFromDb = categoryRepo.findByCategoryName(product.getCategory().getCategoryName()).orElse(null);
//            if (categoryFromDb==null){
//                categoryFromDb=categoryRepo.save(product.getCategory());
//            }
//            product.setCategory(categoryFromDb);
//            productFromDb=productRepo.save(product);
                //only seller can add product to website, there will be no such case that I searched iphone and if
                //iphone not on flipkart then i will add iphone to filpkart then add it to cart to buy
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).build();
            }

            Cart cartFromDb = cartRepo.findCartByUserUsername(username).orElse(null);
            if(cartFromDb==null){
                //need to save new cart
                Cart cart = new Cart();
                cart.setUser(user);
                cart.setTotalAmount(0.0);
                cartFromDb=cartRepo.save(cart);
            }
            //now we will attach this cart to our cart product while creating cart product
            //CartProduct cartProduct = new CartProduct();
            //cartProduct.setCart(cart);

            //CartProduct cartProduct1=cartProductRepo.findByProductProductId(product.getProductId()).orElse(null);
            //thing is if cartProduct will exist, it will exist with product id and logged in user id, so find cartProduct
            // by both as user id filed will not be null
            CartProduct cartProductFromDb = cartProductRepo.findByCartUserUserIdAndProductProductId(user.getUserId(), product.getProductId()).orElse(null);
            if(cartProductFromDb==null){
                cartProductFromDb = new CartProduct();
            }
            else{
                //error product already exists in the cart 409
                return ResponseEntity.status(HttpServletResponse.SC_CONFLICT).build();
            }
            cartProductFromDb.setQuantity(1);
            cartFromDb.setTotalAmount(cartFromDb.getTotalAmount()+(productFromDb.getPrice()*cartProductFromDb.getQuantity()));
            cartProductFromDb.setCart(cartFromDb);
            cartProductFromDb.setProduct(productFromDb);
            //first need to save this product as product id does not exist so error FK constraint
            cartProductFromDb=cartProductRepo.save(cartProductFromDb);
            return ResponseEntity.ok(cartProductFromDb);
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
        }
    }

    @PutMapping("/cart")
    public ResponseEntity<Object> putCart(@RequestBody CartProduct cartProduct){
        try{
            String username=SecurityContextHolder.getContext().getAuthentication().getName();
            User myUser = userRepo.findByUsername(username);

            if(cartProduct.getQuantity()==0){
                //when deleteing product from cart, substract amount from cart as well if product was already present
                // in the cart
                CartProduct cartProduct1 = cartProductRepo.findByCartUserUserIdAndProductProductId(myUser.getUserId(), cartProduct.getProduct().getProductId()).orElse(null);
                if (cartProduct1!=null){
                    double amount1=cartProduct1.getQuantity()*cartProduct1.getProduct().getPrice();
                    Cart cart1= cartRepo.findCartByUserUsername(username).orElse(null);
                    if (cart1!=null){
                        cart1.setTotalAmount(cart1.getTotalAmount()-amount1);
                        cartRepo.save(cart1);
                    }
                }
                cartProductRepo.deleteByCartUserUserIdAndProductProductId(myUser.getUserId(), cartProduct.getProduct().getProductId());
                return ResponseEntity.ok().build();
            }
            else{
                Product productFromDb=productRepo.findById(cartProduct.getProduct().getProductId()).orElse(null);
                if (productFromDb==null){
//                    Category categoryFromDb=categoryRepo.findByCategoryName(cartProduct.getProduct().getCategory().getCategoryName()).orElse(null);
//                    if (categoryFromDb==null){
//                        categoryFromDb=categoryRepo.save(cartProduct.getProduct().getCategory());
//                    }
//                    cartProduct.getProduct().setCategory(categoryFromDb);
//                    cartProduct.getProduct().setSeller(myUser);
//                    productfromDb=productRepo.save(cartProduct.getProduct());
                    return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).build();
                }
                cartProduct.setProduct(productFromDb);

                Cart cartFromDb = cartRepo.findCartByUserUsername(username).orElse(null);
                if(cartFromDb==null){
                    cartFromDb = new Cart();
                }

                CartProduct cartProductFromDb=cartProductRepo.findByCartUserUserIdAndProductProductId(myUser.getUserId(), cartProduct.getProduct().getProductId()).orElse(null);
                if(cartProductFromDb==null){
                    //cartProductFromDb=new CartProduct();
                    cartProductFromDb=cartProduct;
                    //return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).build();
                    //we have to save product with supplied quantity
                    cartProductFromDb.setQuantity(cartProduct.getQuantity());
                    cartFromDb.setTotalAmount(cartFromDb.getTotalAmount()+(cartProductFromDb.getQuantity()*cartProductFromDb.getProduct().getPrice()));
                    cartProductFromDb.setCart(cartFromDb);
                }
                //if (cartProductFromDb!=null){//this will be always true if write if at both places
                else {
                    double oldAmountOfProductInCart = cartProductFromDb.getQuantity()*cartProductFromDb.getProduct().getPrice();
                    cartProductFromDb.setQuantity(cartProduct.getQuantity());
                    double newAmountOfProductInCart = cartProductFromDb.getQuantity()*cartProductFromDb.getProduct().getPrice();
                    cartFromDb.setTotalAmount(cartFromDb.getTotalAmount()+newAmountOfProductInCart-oldAmountOfProductInCart);
                    cartProductFromDb.setCart(cartFromDb);
                }
                //currently here cartProductFromDb has cpid and cart id both but cart product that we take in input
                //does not have these 2 ids, no cart product id
                //but quantity is not updated in cartProduct fromDb, so price is updated but quantity is not updated

                //cartProductFromDb.setQuantity(cartProduct.getQuantity());

                //if cartproductFromDb is not null then means cart is already present so price in cart is including
                //price of this cartProduct also, so now wither make quantity 3+1 = 4 then price is correct
                //or if product already present then get quantity in substract once the price of this product *
                //existing quantity in cart
                //cartFromDb.setTotalAmount(cartFromDb.getTotalAmount()+(cartProductFromDb.getQuantity()*cartProductFromDb.getProduct().getPrice()));
                //cartProductFromDb.setCart(cartFromDb);

                cartProductFromDb=cartProductRepo.save(cartProductFromDb);
                //if saving cartProduct not cartProductFrom Db there is already one product in cart if try to save
                // same cart again will give unique constraint error
                //take cartproduct from db, then you will get cart product id, now when you save , save updates also on same
                //id so no error, I am taking cart product from db but not using it..
                return ResponseEntity.ok(cartProductFromDb);

                //everything working just one issue, if some product is already added in cart and we are updating
                //quantity by 3 so it should be total 3 or 4
                //actuallly currently when adding product in cart first then price is 39.. something but quantity is 3
                //as previous price in cart after adding product 98020
                //and if directly update cartproduct without saving first then also quantity 3 but price 29.. something
                //90000 is less price as previous price is 20 only now
                //mentioned in exam, if product not in cart add with supplied quantity, if quantity 0 then delete
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
        }

    }

    @DeleteMapping("/cart")
    public ResponseEntity<Object> deleteCart(@RequestBody Product product){
        try{
            //when deleting product from cart, substract total amount
            String username=SecurityContextHolder.getContext().getAuthentication().getName();
            User myUser = userRepo.findByUsername(username);
            CartProduct cartProductFromDb=cartProductRepo.findByCartUserUserIdAndProductProductId(myUser.getUserId(), product.getProductId()).orElse(null);
            if (cartProductFromDb==null){
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).build();
            }
            else {
                double amount1=cartProductFromDb.getQuantity()* cartProductFromDb.getProduct().getPrice();
                Cart cart1=cartRepo.findCartByUserUsername(username).orElse(null);
                if(cart1!=null){
                    cart1.setTotalAmount(cart1.getTotalAmount()-amount1);
                    cartRepo.save(cart1);
                }
                cartProductRepo.deleteByCartUserUserIdAndProductProductId(myUser.getUserId(), product.getProductId());
                //No EntityManager with actual transaction available for current thread - cannot reliably
                //process 'remove' call error if not using Transactional
                return ResponseEntity.ok().build();
            }
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
        }

    }
}



//package com.example.demoTcsArtWorkNov23Innovator.service;
//
//import com.example.demoTcsArtWorkNov23Innovator.model.ArtModel;
//import com.example.demoTcsArtWorkNov23Innovator.model.UserModel;
//import com.example.demoTcsArtWorkNov23Innovator.repository.ArtRepository;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ArtService {
//
//    @Autowired
//    ArtRepository artRepository;
//
//    @Autowired
//    UserService userService;
//
//    //created 201 and bad request 400
//    public ResponseEntity<Object> postArtWork(ArtModel artModel){
//        try{
//            //String email1=SecurityContextHolder.getContext().getAuthentication().getName();//owner1
//            //UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            UserModel userModel1= (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            //Both userDetails and userModel has email in debug mode
//            //But userdetails is not giving getEmail while we have getter in UserModel which implements userdetails
//            //since UserModel implements userDetails so this is also UserDetails, here we will get getEmail()
//            //We can get all userDetails from Security context
//            //not using findByusername because it is not unique, currently this is unique so it will work also
//            //but if later omn some user comes with same username then problem will be there
//            String email=userModel1.getEmail();
//            //here also returning username not email
//            //but in load user by username in tcs coe String email passing
//            //how willl we get email, userbname is not unique here
//
//            //We got userModel from Security context but below one is preffered way to get userModel
//            //so taking email only from above usermodel
//            //Lect 17 1:22, getPrincipal returns Proxy only, he is taking UserModel object from SecurityContext
//            //by casting the getPrincipal.. but he is taking id from here then calling findById
//            //actuall user object is actual but if some book list or anything inside thart is proxy..
//            //so be on safer side after getting from security contextr, call db query, he said in Lect 17 1:40:00
//            //this is completely correct, ibn Lecture dopsing same way, typecasting first then using
//            //not taking username directly
//            UserModel userModel=userService.getUserByEmail(email);
//            artModel.setOwnerId(userModel.getId());
//            //in model class if we make it as User ownerId then need to set: artModel.setOwner(userModel);
//            // Cart cartFromDb = cartRepo.findCartByUserUsername(username).orElse(null);
//            //            if(cartFromDb==null){
//            //                //need to save new cart
//            //                Cart cart = new Cart();
//            //                cart.setUser(user);
//            //                cart.setTotalAmount(0.0);
//            //                cartFromDb=cartRepo.save(cart);
//            //            }
//            artModel.setAvailable(true);
//            artModel=artRepository.save(artModel);
//            return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(artModel);
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Bad request was made");
//        }
//    }
//
//    //200 and 400
//    public ResponseEntity<Object> getArtWork(String medium){
//        try{
//            List<ArtModel> artModelListByMedium = artRepository.findByMedium(medium);
//            if (artModelListByMedium.isEmpty()){
//                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("no data available");
//            }
//            return ResponseEntity.ok(artModelListByMedium);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
//        }
//        //need to uise hasAnyAuthority("Owner","Customer") here other wise if permit all then any one can see
//        // other customer and owner any one can see
//        //login is permit all, first login as customer t5hen see art list
//
//    }
//
//    //to update art work price and available status
//    //only the owner of that particular artwork can update
//    //200, 400, 403 forbidden
//    public ResponseEntity<Object> updateArtWork(int id, ArtModel artModel){
//        ArtModel artModelFromDb = artRepository.findById(id).orElse(null);
//        if (artModelFromDb==null){
//            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("not found");
//        }
//        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (artModelFromDb.getOwnerId()!=userModel.getId()){
//            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("you don't have permission");
//        }
//        artModelFromDb.setAvailable(artModel.isAvailable());
//        artModelFromDb.setPrice(artModel.getPrice());
//        //artModelFromDb=artRepository.save(artModel);
//        artModelFromDb=artRepository.save(artModelFromDb);
//        return ResponseEntity.ok(artModelFromDb);
//        //you can do has AnyAuthority(owner, customner) in requst Matcher and if id does not match
//        // then snd this msg you dont have permission
//        //but if doing hasAuthority(Owner) only then you will get Forbidden from there for customer
//        //thois msg will not go there
//
//    }
//
//    //to delete artwork by using id
//    //only the owner of that particular artwork can update
//    //no content 204 and bad request 400 and forbidden 403
//    public ResponseEntity<Object> deleteArtWork(int id){
//        try{
//            UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            //will get class cast exception here if token not passed in header and security configuration not
//            //defined properly or permit all, if properly written request matcher then 403 forbidden
//            String email = userModel.getEmail();
//            UserModel userModelFromDb = userService.getUserByEmail(email);
//            ArtModel artModelFromDb = artRepository.findById(id).orElse(null);
//            if (artModelFromDb==null){
//                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("not found");
//            }
//            if(artModelFromDb.getOwnerId()!=userModelFromDb.getId()){
//                return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("you don't have permission");
//            }
//            artRepository.deleteById(id);
//            return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).body("deleted successfully");
//            //204 is not error, this means successfukly deleted 2xx is successful, 4xx is error
//            //204 successfuly No content means does not return anything in response body
//            //it discards response body
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
//        }
//
//    }
//
//}