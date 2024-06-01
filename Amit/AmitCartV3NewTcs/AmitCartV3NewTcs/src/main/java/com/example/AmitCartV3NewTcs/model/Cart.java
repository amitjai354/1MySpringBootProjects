package com.example.AmitCartV3NewTcs.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cartId;
	private Double totalAmount;
	
	@OneToMany(mappedBy = "cart")
	//@JoinColumn(name="cp_id", referencedColumnName="cpId")
	//writting mapped so not write @JoinColumn as we do not need FK here in cart, here FK will be list
	//as for one cart id there can be multiple cart products so cart products id will be list here
	//also OneToMany means Cart product will be list
	private Set<CartProduct> cartProduct;
	//Biderectional when getting cart will get cart products details as well
	
	@OneToOne
	@JoinColumn(name="user_id", referencedColumnName="userId", updatable=false)
	private UserModel user;
}
