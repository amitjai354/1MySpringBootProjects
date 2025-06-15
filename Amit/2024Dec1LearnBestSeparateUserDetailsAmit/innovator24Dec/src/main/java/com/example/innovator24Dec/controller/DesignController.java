package com.example.innovator24Dec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.innovator24Dec.entity.Design;
import com.example.innovator24Dec.repository.DesignRepository;
import com.example.innovator24Dec.service.DesignService;

//i missed @RestController so all apis failing as api not found error
@RequestMapping("/design")
@RestController
public class DesignController {

	@Autowired
	private DesignService designService;
	
	@Autowired
	private DesignRepository designRepository;
	
	@PostMapping("/add")
	public ResponseEntity<Object> postDevice(@RequestBody Design design){
		//i was using RequestParam instead of RequestBody here so error was occuring
		//Resolved [org.springframework.web.bind.MissingServletRequestParameterException: 
		//Required request parameter 'design' for method parameter type Design is not present]
		return designService.postDesign(design);
	}
	
	@GetMapping("/list")
	public ResponseEntity<Object> getDesign(){
		//java.lang.IllegalArgumentException: 'uri' should start with a path or be a complete HTTP URI: 
		//design/list
		//this error comes when instead of writting /design/list we wrote design/list
		return designService.getDesign();
	}
	
	@GetMapping("/get/{designId}")
	public ResponseEntity<Object> getDesignDataWithId(@PathVariable int designId){
		return designService.getDesignDataWithId(designId);
	}
	
	@GetMapping("/filter")
	public ResponseEntity<Object> getFilter(@RequestParam int price){
		//here we need to use @RequestParam if nothing given in api url but taking input something
		return designService.getPrice(price);
	}
	
	@DeleteMapping("/delete/{designId}")
	public ResponseEntity<Object> deleteDesign(@PathVariable int designId){
		return designService.deleteDesign(designId);
	}
	
	@PutMapping("/update/{designId}")
	public ResponseEntity<Object> updateDesign(@PathVariable int designId, @RequestBody Design design){
		return designService.updateDesign(designId, design);
	}
}
