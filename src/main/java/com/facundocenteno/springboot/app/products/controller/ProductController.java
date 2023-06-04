package com.facundocenteno.springboot.app.products.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;
//import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.facundocenteno.springboot.app.products.models.entity.Product;
import com.facundocenteno.springboot.app.products.models.service.IProductService;

@RestController
public class ProductController {

	@Autowired
	private Environment env;
	
	@Value("${server.port}")
	private Integer port;
	
	@Autowired
	private IProductService productService;
	
	@GetMapping("/list")
	public List<Product> getAll(){
		return productService.findAll().stream().map(product -> {
			product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			//product.setPort(port);
			return product;
		}).collect(Collectors.toList());
		
	}
	
	@GetMapping("/get/{id}")
	
	

	public Product getById(@PathVariable Long id) throws InterruptedException {
		
		if(id.equals(10L)) {
			throw new IllegalStateException("producto no encontrado");
		}
		
		if (id.equals(7L)) {
			TimeUnit.SECONDS.sleep(5L);
		}
		
		Product product = productService.findById(id);
		product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		//product.setPort(port);

		
		return product;
	}
}
