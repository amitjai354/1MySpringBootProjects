package com.example.AmitCartV3NewTcs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.AmitCartV3NewTcs.model.Cart;
import com.example.AmitCartV3NewTcs.model.CartProduct;
import com.example.AmitCartV3NewTcs.model.Product;
import com.example.AmitCartV3NewTcs.model.UserModel;
import com.example.AmitCartV3NewTcs.repository.CartProductRepo;
import com.example.AmitCartV3NewTcs.repository.CartRepo;
import com.example.AmitCartV3NewTcs.repository.ProductRepo;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ConsumerService {

	
	@Autowired
	CartRepo cartRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	CartProductRepo cartProductRepo;

	//403 Forbidden.. update and delete api if seller is not the one who added the product..
	//in Art project, requirement is that delete api should be accesed by Owner and only creator of art can delete api otherwise
	//403 Forbidden.. so getArtById then check if user id in artFromDb has is same as user id from security context..
	//if yes then deleteById otherwise return 403 Forbidden

	//here it can be like delete api should be accesed by seller and onlyu seller who added the product should be able to delete
	//Product has seller.. so find producyBy id then check if userId in prodcutFromDb == userId from securitycontext
	//if yes then delete by Id and if not return 403 forbidden..
	//but we are doing deleteBySellerUserIdAndProductId..no actually in seller api we are simply doing deleteById

	//here in requirement nothing says.. it only says that if customer tries to access seller api.. give 403
	//it does not say that if seller did not add the product then return 403.. but it should be like this only..

	//this wa requirement in art that while updating or deleting if not found then return not found

	////This is written for get api.. findByCartUserUserIdAndProductProductId
	//same way if required, can give forbidden for consumer api when updating or deleting from cart
	//but currently given deleteByUserUserIdAndProductProductId.. so use this..
	//actually in json passing product here but product is added by seller in db not by consumer..
	//consumer only adds in cart.. so here with product id can not serach that belongs to which consumer

	public ResponseEntity<Object> getConsumerCart(){
		//it will automatically give 403 if Seller tries to access this as in security filter chain have givrn 
		//request matcher so from securirty Filter chain returns 403 we need not to set anything in 
		//authentication entry point..
		//authenbtication entry point is for when no token passed
		//but 403 will be returned only if we write anyrequest.permitAll
		//if we make it authenticatred then in all cases only authentication entry point
		//with anyrequest permitall, we are getting proper 401 when no token passed due to authentication entry point
		//and getting 403 if any sellertries to access consumer api
		
		//amit is both seller and consumer so api is working for amit but not for glaxo user seller
		try {
			UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			//List<Cart> cartList = cartRepo.findAll();
			//problem with findAll is that we will get cart details of all consumers(jack and bob and amit)
			//but it should be like that which user logged in only that user should see the his cart not others cart
			//we need to make any such method where we can pass user id as well
			
			Cart cart = cartRepo.findByUserUserId(userModel.getUserId()).orElse(null);
			//this is working perfectrly.. now getting details of only logged in consumer not all consumers
			//same way there can findByCartIdAndUserUserId(int cartId, int userId)
			
			//if(cartList.isEmpty()) {
			if(cart==null) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("cart is empty");
			}
			return ResponseEntity.ok(cart);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
		}
	}
	
	public ResponseEntity<Object> postProductToCart(Product product) {
		//this is consumer api and consumer will not add product to website, so just find by id, we will not find by
		//userid because seller is adding product but comnsumer is here fetching product from db
		//if product not present in db, product not in stock.. 
		//also check if this logged in user has cart or not, if not then create new cart for user
		//add product to cart product if not present, if already present in cart product, return 409

		//so basically we have product we will add it to cartOrudct then add/save cart product to cart, update price also in cart
		//cart has list of cart products, as ManyToOne from cart to cartProduct Many means list,, so can not call save on cart
		//with cart product, instead we can save cartProduct with each cartproduct we can pass cart to create FK relationship
		//and after savingg cartProduct we can save cart again to update price

		//productFromDb = findById, if null return out of stck
		//cartFromDb=cartRepo.findByUserUserId/UserName/email(), if null create new cart for user
		//cartFromDb=cartRepo.save(new Cart(0.0, userModel.getUserId/username/email)
		//cartPrdocutFromDb=cartProductRepo.findByUserUserIdAndProductProductId(userModel.getUserId, product.getProductId)
		//if not null return product already present in cart
		//if null, save prodcut to cart product and cart product to  cart..
		// cartProductFromDb= cartProductRepo.save(new cartProduct(1, productFromDb, cartFromDb)
		//Now update proice.. amount = cartFromdb.getTotalAmount+(product.getPrice*cartProduct.getQuantity)
		//cartFromDb.setTotalAmount(amount)
		//cartFromDb=cartRepo.save(cartFromDb)

		//saving cartProduct with cart will be complicated as cart has list of cartProducts so we need to pass list
		//but cartProduct has single cartId so while saving cartProduct , pass single cartId, now FK relation created in db
		//in cartModel we have @OneToMany(mappedBy=cart) above Set<CartProduct> cartProducts, so FK will not be created in db in cart
		//so we can not pass cartProduct in cart..
		//in CartProductModel, we have @ManyToOne(), @JoinCoulmn(name="cart_id", referenceColumnName="cartId:), CartModel cart;
		//so FK cartId will be created here in CartProduct in db, so we will save cartId in cartProduct

		try{
			Product productFromDb = productRepo.findById(product.getProductId()).orElse(null);
			if(productFromDb==null) {
				return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("Product Not In Stock");
			}
			
			UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			//Now find cart by logged in user id, do not findAll it will fetch other user cart also
			Cart cartFromDb = cartRepo.findByUserUserId(userModel.getUserId()).orElse(null);
			if(cartFromDb==null) {
				//if logged in user does not have cart then create new one
				cartFromDb= cartRepo.save(new Cart(0.0, userModel));
			}
			
			//Now add product to cart Product then add cart Product to cart
			//But cart product does not have User in its model class, it has product and cart
			//can not serach cartProduct from db based on product so search by cartId
			//and Cart we have to search for logged in user id , can not take cart product from some other users cart
			CartProduct cartProductFromDb = cartProductRepo.findByCartUserUserIdAndProductProductId(userModel.getUserId(), product.getProductId());
			if(cartProductFromDb!=null) {
				return ResponseEntity.status(HttpServletResponse.SC_CONFLICT).body("Product already present in cart");
			}
			//cartProductFromDb = cartProductRepo.saveAndFlush(new CartProduct(1, productFromDb, cartFromDb));
			cartProductFromDb = cartProductRepo.save(new CartProduct(1, productFromDb, cartFromDb));

//			cartProductFromDb.setCart(cartFromDb);
//			cartProductFromDb.setQuantity(1);
//			cartProductFromDb.setProduct(productFromDb);
//			cartProductFromDb = cartProductRepo.save(cartProductFromDb);
			//now cart product is added to cart as cartProduct has Cart FK
			Double amount = cartFromDb.getTotalAmount()+product.getPrice()*cartProductFromDb.getQuantity();
			//we have to update price in cart as well
			cartFromDb.setTotalAmount(amount);
			cartFromDb=cartRepo.save(cartFromDb);
			//return ResponseEntity.ok(cartProductFromDb);
			//return ResponseEntity.ok(cartFromDb);
			//this is returning cartProduct as null but in db, relation created
			//after this if get/cart then getting cartPeoduct
			//either can do saveAndFlush. this immediately saves and commit
			//but do fetch = fetch type eager on cartProduct in cart, i have only written mapped by
			//this way it will eagerly push cart prodecut otherwise when i do select query then only gives result lazy
			//with save and flush, and Fetch Eager but still giving same null for cart Product

			//if we are not setting attributes, jpa does not set attributtes while saving, need to call find from db

			//we can return findByCartUserId
			cartFromDb=cartRepo.findByUserUserId(userModel.getUserId()).orElse(null);
			return ResponseEntity.ok(cartFromDb);
			//still cartProduct is null, but if after this call findCart thenh giving cart product
			//this is SB issue as per stacksover flow
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
		}
	}
	
	
	
	public ResponseEntity<Object> updateConsumerCart(CartProduct cartProduct){
		//My biggest mistake here was that i was trying to write common code instead of writting codes in
		//if and else blocks separately..
		//but first write codes in if and else block then if possible make coomon code
		//redability also decreses if write common code if write separately then easily read code

		//3 cases here, do each separately, otherwise it will get very complicated
		//if quantity=0, delete and reduce amount from cart... if cartProductFromDb==null, simpley save new cartProduct as
		//in above post api and if cartProductFromDb is not null then get older amount From db substract this from totral amount
		//and get new updated amount for cartProduct and add this to cartAmount

		//so basically we have product we will add it to cartOrudct then add/save cart product to cart, update price also in cart
		//cart has list of cart products, as ManyToOne from cart to cartProduct Many means list,, so can not call save on cart
		//with cart product, instead we can save cartProduct with each cartproduct we can pass cart to create FK relationship
		//and after savingg cartProduct we can save cart again to update price

		//saving cartProduct with cart will be complicated as cart has list of cartProducts so we need to pass list
		//but cartProduct has single cartId so while saving cartProduct , pass single cartId, now FK relation created in db
		//in cartModel we have @OneToMany(mappedBy=cart) above Set<CartProduct> cartProducts, so FK will not be created in db in cart
		//so we can not pass cartProduct in cart..
		//in CartProductModel, we have @ManyToOne(), @JoinCoulmn(name="cart_id", referenceColumnName="cartId:), CartModel cart;
		//so FK cartId will be created here in CartProduct in db, so we will save cartId in cartProduct

		//update if cartProduct is not null..
		//productFromDb = findById, if null return out of stck
		//cartFromDb=cartRepo.findByUserUserId/UserName/email(), if null create new cart for user
		//cartFromDb=cartRepo.save(new Cart(0.0, userModel.getUserId/username/email)
		//cartPrdocutFromDb=cartProductRepo.findByUserUserIdAndProductProductId(userModel.getUserId, product.getProductId)
		//if not null return product already present in cart in save api but here not..
		//now cartProductFromDb has older qunatity in cart and cartProduct that was passed in json has new quantity.. so get
		// old amount=cartProductFromDb.getProduct.getPrice*cartProductFromDb.getQuantity
		//new amount= cartProduct.getProduct.getPrice*cartProduct.getQuantity
		//total amount=cartFromDb.getTotalAmount+newAmount-oldAmount
		//cartFromDb.setTotalAmount(totalAmount)
		//cartFromDb.save(cartFromDb)

		//if cartProductFromDb null.. simply save new cartProduct, save prodcut to cart product and cart product to  cart..
		//cartProductFromDb=cartProduct passedFromjson
		// cartProductFromDb= cartProductRepo.save(cartProductFromDb)
		//Now update proice.. amount = cartFromdb.getTotalAmount+(cartProductFromDb.getproduct.getPrice*cartProduct.getQuantity)
		//cartFromDb.setTotalAmount(amount)
		//cartFromDb=cartRepo.save(cartFromDb)

		//if quantity = 0, delete cartProduct and reduce amount from cart then cart with updated amount
		//cartProductFromDb=cartProductRepo.findByUserUserIdAndProductProductId(userModel.getUserId, cartProduct.getProduct.getProductId)
		//if not null then oly delete otherwise return cartProdct not ib cart
		//cartFromDb=cartRepo.findByUserUserId(userModel.getUserId), if null return cart not present for user
		//if not null then delete.. but frist calucalte amount to reduce
		//amount=cartFromDb.getTotalAMount-(cartProduct.getProduct.getPrice*cartProduct.getQuantity)
		//cartFromDb.setTotalAmount(amlunt) then cartFromDb=cartRepo.save(cartFromDb)
		// Now delete.. cartProductRepo.deleteByUserUserIdAndProductProductId(userId, productId)
		try {
			UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(cartProduct.getQuantity()==0) {
				CartProduct cartProductFromDb = cartProductRepo.findByCartUserUserIdAndProductProductId(userModel.getUserId(),cartProduct.getProduct().getProductId());
				if(cartProductFromDb!=null) {
					Cart cartFromDb= cartRepo.findByUserUserId(userModel.getUserId()).orElse(null);
					if(cartFromDb!=null) {
						Double amount = cartFromDb.getTotalAmount() - (cartProductFromDb.getQuantity()*cartProductFromDb.getProduct().getPrice());
						cartFromDb.setTotalAmount(amount);
						cartFromDb = cartRepo.save(cartFromDb);
					}
					else {
						return ResponseEntity.badRequest().body("No cart present for logged in User");
					}
					
					//we have to delete cart product from cart so cartProduct Repo.delete by id
					//but thios way it will delete cart product from any other persons cart as well
					//so delete by cartid and user id as well only for logged in user
					//whenever some other Entity or more than one entity involved in deleting it should be @Transactional
					//as data may currupt in deleting
					//@Transactional
	                //cartProductRepo.deleteByCartUserUserIdAndProductProductId(   myUser.getUserId(), cartProduct.getProduct().getProductId());
					cartProductRepo.deleteByCartUserUserIdAndProductProductId(userModel.getUserId(), cartProduct.getProduct().getProductId());
					return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).body("deleted successfully");
					//this delete was not working and even no error if i add cadcadeAll in Cart above cartProduct
					//this cascade behaviors is arbitrary in Bidirectional mainly.. persist in one side
					//not in sync in both side
					//Delete Not Working with JpaRepository stack over flow
					//Most probably such behaviour occurs when you have bidirectional relationship and you're not
					//synchronizing both sides WHILE having both parent and child persisted (attached to the current session).
					//The delete here didn't work because we didn't synchronized the other part of relationship which
					// is PERSISTED IN CURRENT SESSION. If Parent wasn't associated with current session our test would pass
				}
//				cartProductRepo.deleteByCartUserUserIdAndProductProductId(userModel.getUserId(), cartProduct.getProduct().getProductId());
//				return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).body("deleted successfully");
				else {
					return ResponseEntity.badRequest().body("No cart Product with given id, present in cart for logged in User");
				}
			}
			else {
				Product productFromDb= productRepo.findById(cartProduct.getProduct().getProductId()).orElse(null);
				if(productFromDb==null) {
					return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Product out of stock");
				}
				
				Cart cartFromDb= cartRepo.findByUserUserId(userModel.getUserId()).orElse(null);
				if(cartFromDb==null) {
					//if cart is not present for this user, create one cart first
					cartFromDb = cartRepo.save(new Cart(0.0, userModel));
				}
				
				//cart has set of CartProducts so can not get quantity from cart. instead we will find 
				//cartProduct by cart id and user id and get quantity from here
				//cartProductFrom db has older quantity and cartProduct has new quantity
				CartProduct cartProductFromDb = cartProductRepo.findByCartUserUserIdAndProductProductId(userModel.getUserId(),cartProduct.getProduct().getProductId());
				if(cartProductFromDb==null) {
					cartProductFromDb = cartProduct;
					Double amount = cartFromDb.getTotalAmount() + (cartProduct.getQuantity()*cartProduct.getProduct().getPrice());
					cartFromDb.setTotalAmount(amount);
					cartFromDb=cartRepo.save(cartFromDb);
					cartProductFromDb.setCart(cartFromDb);//add FK relationship as in passed cartProduct json, cart details not present
					cartProductFromDb = cartProductRepo.save(cartProductFromDb);
					return ResponseEntity.ok(cartFromDb);
				}
				else {
					Double amount = cartFromDb.getTotalAmount() - (cartProductFromDb.getQuantity()*cartProductFromDb.getProduct().getPrice()) + (cartProduct.getQuantity()*cartProduct.getProduct().getPrice());
					cartFromDb.setTotalAmount(amount);
					cartFromDb=cartRepo.save(cartFromDb);		
					cartProductFromDb.setCart(cartFromDb);//add FK relationship as in passed cartProduct json, cart details not present
					cartProductFromDb.setQuantity(cartProduct.getQuantity());//this is very must
					cartProductFromDb = cartProductRepo.save(cartProductFromDb);
					//return ResponseEntity.ok(cartFromDb);
					//return ResponseEntity.ok(cartProductFromDb);
					//if we are not setting attributes, jpa does not set attributtes while saving, need to call find from db

					//we can return findByCartUserId
					cartFromDb=cartRepo.findByUserUserId(userModel.getUserId()).orElse(null);
					return ResponseEntity.ok(cartFromDb);
					//still cartProduct is null, but if after this call findCart thenh giving cart product
					//this is SB issue as per stacksover flow
				}
//				cartProductFromDb = cartProductRepo.save(cartProductFromDb);
//				return ResponseEntity.ok(cartProductFromDb);
				//return ResponseEntity.ok(cartFromDb); returning cart produsct as well even after eager fetch
				

				
				//Sringboot returns null for relational(FK) entity after saving
				//in ManyToOne missed cascadeType.Persist
				//Persist, Merge, Remove, Refresh, Detach
				//Remove means if removing here, remove in other class also 
				//eg if removing product, remove seller user from db also, reverse is good that if remove user, 
				//then remove products added by user seller
				//we should not write cascadeAll on ManyToOne side instead, write on OneToMany side
				//as Many products related to one user, if delete any product then delete user also then means
				//delete all products, reverse is correct if remove user, remove all products
				
				//cascadePersist means when petrsisting an entity, persistthe entitoies held in its fields
				//But with cascadePersist as well not returning result immediatley.. when get separately then gives result of cart products

				//CartProduct cartProductFromDb = cartProductRepo.findByCartCartIdAndCartUserUserId(cartFromDb.getCartId(), userModel.getUserId());
				//CartProduct findByCartCartIdAndCartUserUserId(int cartId, int userId);
				//if getting by cartid and cart user id, will return any product from cart, will not consider product id
				//but in cart product there is product id also..
				//currently it is returning me product id = 2 also as this is crocin
				//but in cart product jsomn we are passing product id as 3 thyat is iphone


				//Related entities are null after calling saveAll in Spring JPA
				//As you stated in the comments you did not set those values before saving.
				//
				//JPA does not load them for you. JPA pretty much doesn't load anything upon
				// saving except the id if it is generated by the database.
				//
				//A more common case of the same problem/limitation/misconceptions are
				// bidirectional relationships: JPA pretty much ignores the not owning side
				// and the developer has to make sure that both sides are in sync at all times.
				//
				//You would have to refresh the entity yourself. Note that just loading it
				// in the same transaction would have no effect because it would come
				// from the 1st level cache and would be exactly the same instance.
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
	
	
	
	
	public ResponseEntity<Object> deleteProductFromConsumerCart(Product product){
		UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			//	`CartProduct cartProductFromDb = cartProductRepo.findByCartUserUserIdAndProductProductId(userModel.getUserId(), product.getProductId());
			//This is written for get api.. findByCartUserUserIdAndProductProductId
			CartProduct cartProductFromDb = cartProductRepo.findByCartUserUserIdAndProductProductId(userModel.getUserId(), product.getProductId());

			if(cartProductFromDb==null) {
				//return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("Product Not Present in cart");
				return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("Product Not Present in cart");
			}
			
			Cart cartFromDb = cartRepo.findByUserUserId(userModel.getUserId()).orElse(null);
			if(cartFromDb==null) {
				return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("No cart present for logged in user");
			}
			Double amount = cartFromDb.getTotalAmount()-( product.getPrice()*cartProductFromDb.getQuantity());
			cartFromDb.setTotalAmount(amount);
			cartRepo.save(cartFromDb);
			cartProductRepo.deleteByCartUserUserIdAndProductProductId(userModel.getUserId(), product.getProductId());
			return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).body("successfully deleted");
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}

}
