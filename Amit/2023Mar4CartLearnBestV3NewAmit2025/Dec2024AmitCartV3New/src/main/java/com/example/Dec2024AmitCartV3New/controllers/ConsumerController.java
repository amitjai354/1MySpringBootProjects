package com.example.Dec2024AmitCartV3New.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Dec2024AmitCartV3New.models.Cart;
import com.example.Dec2024AmitCartV3New.models.CartProduct;
import com.example.Dec2024AmitCartV3New.models.Product;
import com.example.Dec2024AmitCartV3New.models.User;
import com.example.Dec2024AmitCartV3New.repo.CartProductRepo;
import com.example.Dec2024AmitCartV3New.repo.CartRepo;
import com.example.Dec2024AmitCartV3New.repo.ProductRepo;
import com.example.Dec2024AmitCartV3New.repo.UserRepo;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth/consumer")
public class ConsumerController {
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CartProductRepo cartProductRepo;
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private UserRepo userRepo;

	//status 200 // returns the consumer’s cart: logged in consumer cart only not all carts
	@GetMapping("/cart")
	public ResponseEntity<Object> getCart(){
		//return the consumers cart..
		//get consumer id from security context holder then in cart we have user id..
		//in cart Repo findByUserUserId
		//but we will get username from userdetail so .. 
		//findByUserUsername
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Cart cartFromDb = cartRepo.findByUserUsername(userDetails.getUsername()).orElse(null);
			//each consumer will have 1 cart id.. in api response also not given list[] but single cart{}
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(cartFromDb);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in get customer cart");
		}
	}
	
	//status 200, 409 if already in cart
	//takes a Product json in request body and adds it to the consumer’s cart
	@PostMapping("/cart")
	public ResponseEntity<Object> postCart(@RequestBody Product product){
		//take product and add to cart.. product has catrgoty id, user id, in input json we get category id
		//user id we will add from security context holder
		//cart has user id only but in java code you can check that it has cart product as well,, bidirectional 
		//relationship..mapped by , alos can check in the diagram that cart has relation with user and cart product
		//so we need one cart prodct as well.. cart product has product id and cart id..
		// so save product first then save cart product then save cart.. otherwise intransient Foreign key error
		//that first add in parent class then add in child class
		
		//also need to check if this cunsumer has a cart or not if not, create new cart for this consumer first
		//how will add cart product to cart.. actually in db FK cartid is in cart product.. so will update cart id
		//in cart product.. thks way cart added to cart product
		
		//Request body -
		//{"productId":3,"category":
		//{"categoryName":"Electronics","categoryId":"2"},"price":"98000.0","productName":"iPhone 12"}
		
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication();
			User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("username not found"));
			//first check if product in stoc or not
			Product productFromDb = productRepo.findById(product.getProductId()).orElse(null);
			if(productFromDb==null) {
				return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("Product out of stock");
				//customer can not save product if product not present
			}
			//take product from db other wise in transient error that category id save first
			
			//now check if this user has cart or not.. if not save cart first
			//cartProduct has column cart id and product id so will save cart product in last
			//first take product from db and save cart if not present
			
			//check cart for user so username or userid needed
			Cart cartFromDb = cartRepo.findByUserUsername(userDetails.getUsername()).orElse(null);
			if(cartFromDb == null) {
				cartFromDb = cartRepo.save(new Cart(0.0, user));
			}
			
			//we will have to update price after adding product to cart..
			//first sdave cart product with cart id and product id in it then save cart again to update price
			
			//if product already in cart.. retrn 409 so first find cart product from db by product id and user id
			//but cart product does not have user id durectly so user cart as cart has user id
			CartProduct cartProductFromDb = cartProductRepo
					.findByCartUserUserIdAndProductProductId(user.getUserId(), product.getProductId()).orElse(null);
			if(cartProductFromDb != null) {
				return ResponseEntity.status(HttpServletResponse.SC_CONFLICT).body("product already in cart");
			}
			
			//if cartProduct not already not in cart then save the cart product in cart
			cartProductFromDb.setCart(cartFromDb);
			cartProductFromDb.setProduct(productFromDb);
			cartProductFromDb.setQuantity(1);
			cartProductFromDb = cartProductRepo.save(cartProductFromDb);
			
			Double oldCartAmount = cartFromDb.getTotalAmount();
			Double newCartAmount = oldCartAmount+(cartProductFromDb.getProduct().getPrice()
					*cartProductFromDb.getQuantity());
			//can directly take price as quantity is 1 so not needed to do this much here but needed in update api
			
			//can update price in cart above as well then save in cart product
			cartFromDb.setTotalAmount(newCartAmount);
			cartFromDb = cartRepo.save(cartFromDb);
			
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(cartProductFromDb);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in saving cart product");
		}
	}
	
	//status 200 takes a CartProduct json in request body and updates the quantity of the product in cart. 
	//means update quantity of cartProduct in cart
	//if product not in cart, save product in cart with supplied quantity
	//if quantity is 0 then delete product from cart
	@PutMapping("/cart")
	public ResponseEntity<Object> putCart(@RequestBody CartProduct cartProduct){
		//takes cart product json in input and update quantity in cart.. then price will also be be updated
		//if product not in cart then add to cart
		//if quantuty 0 then delete product from cart and reduce price from cart, delete query written in below api
		
		//cart product json has already product in input json..
		//we will update quantity in cart product and price we will update in cart prudct after adding cart product
		//to cart, here also if no cart create cart first
		
		//Request body -
		//{"product":{"productId":3,"category":
		//{"categoryName":"Electronics","categoryId":"2"},"price":"98000.0","productName":"iPhone 12"},"quantity":3}
		
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication();
			User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(()-> new UsernameNotFoundException("username not found"));
			
			//first check if product in stock or not
			Product productFromDb = productRepo.findById(cartProduct.getProduct().getProductId()).orElse(null);
			if(productFromDb==null) {
				return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("product out of stock");
			}
			
			//also check if this user has cart or not.. if not create new one
			Cart cartFromDb = cartRepo.findByUserUsername(user.getUsername()).orElse(null);
			if(cartFromDb==null) {
				cartFromDb = cartRepo.save(new Cart(0.0, user));
			}
			
			double oldcartAmount = cartFromDb.getTotalAmount();
			double newCartAmount = 0.0;
			
			
			//if cart product in db , then it must have user details as well so do not look at request as
			//we are fetching from db, from request we will take product id only, userid we will take from
			//logged in user not cart product in request
			CartProduct cartProductFromDb = cartProductRepo.findByCartUserUserIdAndProductProductId(user.getUserId(), 
							cartProduct.getProduct().getProductId()).orElse(null);
			
			//if cart product in cart and supplied quantity = 0 then delete from cart
			if(cartProductFromDb!=null &&  cartProduct.getQuantity()==0) {
				//delete product from cart, if product not in cart then also no error
				//afer deleting reduce amount and update cart again
				
				//cartProduct from db has old quantoty of product in cart
				//cart product in request body has quantity as 0
				newCartAmount = oldcartAmount - (cartProductFromDb.getProduct().getPrice() * cartProductFromDb.getQuantity());
				cartFromDb.setTotalAmount(newCartAmount);
				cartFromDb = cartRepo.save(cartFromDb);
				
				cartProductRepo.deleteByCartUserUserIdAndProductProductId(user.getUserId(), cartProduct.getProduct().getProductId());
				return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).body("deleted successfully");
			}
			
			
			if(cartProductFromDb==null) {
				//if product not in cart save product in cart
				//product customer will not save so no issue..
				//but can check once if product is in stock or not
				//after saving cart product need to update amount
				
				//cartProductFromDb.setProduct(productFromDb);
				//cartProductFromDb.setCart(cartFromDb);
				//cartProductFromDb.setQuantity(cartProduct.getQuantity());
				//cartProductFromDb = cartProductRepo.save(cartProductFromDb);
				
				newCartAmount = oldcartAmount + (cartProduct.getProduct().getPrice()*cartProduct.getQuantity());
				//cartFromDb.setTotalAmount(newCartAmount);
			}
			//else update product in cart with supplied quantity
			else{
				//if do not write else here, then in case cartProductFromDb is null then after if condition below code will run
				//it will give null pointer exception when do cartProductFromDb.getQuantity as it is null
				newCartAmount = oldcartAmount + (cartProduct.getProduct().getPrice()*cartProduct.getQuantity())
					-(cartProductFromDb.getQuantity()*cartProductFromDb.getProduct().getPrice());
			//if cart product already in cart, reduce old amount of product from cart after adding new updated price of product
			}
			
			cartFromDb.setTotalAmount(newCartAmount);
			
			cartProductFromDb.setProduct(productFromDb);
			cartProductFromDb.setCart(cartFromDb);
			cartProductFromDb.setQuantity(cartProduct.getQuantity());
			cartProductFromDb = cartProductRepo.save(cartProductFromDb);
			
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(cartProductFromDb);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in updating cart product");
		}
	}
	
	//status 200
	//we have product in request, remove it from cart
	@DeleteMapping("/cart")
	public ResponseEntity<Object> deleteCart(){
		//takes product json in input and remove product from cart
		//product has category id in input and user id we will add from security cointext holder
		//cart product has product id and cart id.. we will calculate the price of this product in cart product
		//then delete cart product from cart as cart product has cart id and then reduce the amount from cart
		//this all should be transactional in nature means either all code is executed or none is executed
		
		//when multiple entities are involved then we have to write @Transactional over delete query in repo
		
		//here we dont have to delete cart, just delete product from cart..
		//so deletById in cart repo will not work..
		
		//instead in cartProduct repo we have to write deleteBy
		//CartProduct has product id and cart id as fk.. so
		//deleteByCartUserUserIdAndProductProductId
		
		//Request body -
		//{"productId":3,"category":
		//{"categoryName":"Electronics","categoryId":"2"},"price":"98000.0","productName":"iPhone 12"}
		
		
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("username not found"));   

			//we have product in request, remove it from cart
			//we have product id and user id, find cartProduct by user id and product id if cartProduct not in db
			//then return product not present else delete
			//when delete, reduce amount from cart, delete from cartProduct as product id in cartProduct
			
			CartProduct cartProductFromDb = cartProductRepo.findByCartUserUserIdAndProductProductId(
					user.getUserId(), product.getProductId()).orElse(null);
			if(cartProductFromDb==null) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("product not in cart");
			}
			
			Cart cartFromDb = cartRepo.findByUserUsername(user.getUsername()).orElse(null);
			//this is not possible that cartproduct is in cart but there is no cart
			//so no need to check null case here, but still lets put the check, np
			if(cartFromDb==null) {
				return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("No cart present for logged in user");
			}
			
			double oldCartAmount = cartFromDb.getTotalAmount();
			double newCartAmount = oldCartAmount-(cartProductFromDb.getQuantity()*cartProductFromDb.getProduct().getPrice());
			
			cartFromDb.setTotalAmount(newCartAmount);
			cartFromDb = cartRepo.save(cartFromDb);
			
			cartProductRepo.deleteByCartUserUserIdAndProductProductId(user.getUserId(), product.getProductId());
			
			return ResponseEntity.status(HttpServletResponse.SC_OK).body("deleted successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in get customer cart");
		}
		
	}
}
